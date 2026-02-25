package com.br.ads.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.br.ads.config.AdsStorageProperties;
import com.br.ads.enums.AdvertisementType;
import com.br.ads.models.Advertisement;
import com.br.ads.models.AdvertisementImage;
import com.fasterxml.jackson.databind.ObjectMapper;

import response.PlaybackManifest;

@Service
public class PlaybackZipService {

	private static final DateTimeFormatter ZIP_DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private final AdvertisementService advertisementService;
	private final AdsStorageProperties props;
	private final ObjectMapper objectMapper;

	public PlaybackZipService(AdvertisementService advertisementService, AdsStorageProperties props,
			ObjectMapper objectMapper) {

		this.advertisementService = advertisementService;
		this.props = props;
		this.objectMapper = objectMapper;
	}

	public record ZipFile(Path path, String filename, boolean exists) {
	}

	public ZipFile getOrCreateZip(LocalDate date) throws IOException {
		String bundlePath = requiredNonBlank(props.getPlayback().getBundlePath(), "ads.playback.bundle-path");
		Path bundleDir = Path.of(bundlePath);
		Files.createDirectories(bundleDir);

		String dayFolderName = ZIP_DATE.format(date); // 22-02-2026
		String zipFilename = dayFolderName + ".zip"; // 22-02-2026.zip
		Path zipPath = bundleDir.resolve(zipFilename);

		List<Advertisement> ads = advertisementService.listActiveForPlayback(date);
		if (ads.isEmpty()) {
			return new ZipFile(zipPath, zipFilename, false);
		}

		buildZip(zipPath, dayFolderName, date, ads);
		return new ZipFile(zipPath, zipFilename, true);
	}

	private void buildZip(Path zipPath, String dayFolderName, LocalDate date, List<Advertisement> ads)
			throws IOException {
		Path tmp = zipPath.resolveSibling(zipPath.getFileName().toString() + ".tmp");
		Files.deleteIfExists(tmp);

		String mediaBasePath = requiredNonBlank(props.getMedia().getBasePath(), "ads.media.base-path");
		Path mediaBase = Path.of(mediaBasePath);

		PlaybackManifest manifest = buildManifestForZip(date, ads, dayFolderName);

		try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(tmp)))) {

			// Sempre cria a "pasta do dia" (entry de diretório) para garantir que ao
			// extrair vira pasta
			ZipEntry folderEntry = new ZipEntry(dayFolderName + "/");
			zos.putNextEntry(folderEntry);
			zos.closeEntry();

			// manifest.json dentro da pasta do dia
			byte[] manifestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(manifest);
			ZipEntry manifestEntry = new ZipEntry(dayFolderName + "/manifest.json");
			zos.putNextEntry(manifestEntry);
			zos.write(manifestBytes);
			zos.closeEntry();

			// assets dentro de dayFolderName/media/...
			for (Advertisement ad : ads) {
				if (ad.getType() == AdvertisementType.VIDEO) {
					String videoName = safeFilenameFromUrlOrName(ad.getVideoUrl());
					Path source = mediaBase.resolve(String.valueOf(ad.getId())).resolve(videoName);

					String insideZip = dayFolderName + "/media/" + ad.getId() + "/video/" + videoName;
					addFile(zos, source, insideZip);
				}

				if (ad.getType() == AdvertisementType.IMAGE) {
					List<AdvertisementImage> images = (ad.getImages() == null) ? List.of()
							: ad.getImages().stream().toList();
					images.stream().sorted(Comparator.comparingInt(AdvertisementImage::getOrderIndex)).forEach(img -> {
						try {
							String imgName = safeFilenameFromUrlOrName(img.getImageUrl());
							Path source = mediaBase.resolve(String.valueOf(ad.getId())).resolve("images")
									.resolve(imgName);

							String insideZip = dayFolderName + "/media/" + ad.getId() + "/images/" + img.getOrderIndex()
									+ "-" + imgName;

							addFile(zos, source, insideZip);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
				}
			}
		} catch (RuntimeException e) {
			if (e.getCause() instanceof IOException io) {
				throw io;
			}
			throw e;
		}

		Files.move(tmp, zipPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	}

	private PlaybackManifest buildManifestForZip(LocalDate date, List<Advertisement> ads, String dayFolderName) {
		List<PlaybackManifest.Item> items = new ArrayList<>();

		for (Advertisement ad : ads) {
			Integer dailyDisplayCount = ad.getRecurrence().getDailyDisplayCount() != null
					? ad.getRecurrence().getDailyDisplayCount()
					: 0;
			Integer maxShows = (dailyDisplayCount > 0) ? ad.getRecurrence().getDailyDisplayCount() : null;
			List<PlaybackManifest.Asset> assets = new ArrayList<>();

			if (ad.getType() == AdvertisementType.VIDEO) {
				String videoName = safeFilenameFromUrlOrName(ad.getVideoUrl());
				String path = "media/" + ad.getId() + "/video/" + videoName; // relativo à pasta do dia
				assets.add(new PlaybackManifest.Asset(path, null, ad.getVideoDurationSeconds()));
			}

			if (ad.getType() == AdvertisementType.IMAGE) {
				List<AdvertisementImage> images = (ad.getImages() == null) ? List.of()
						: ad.getImages().stream().toList();
				images.stream().sorted(Comparator.comparingInt(AdvertisementImage::getOrderIndex)).forEach(img -> {
					String imgName = safeFilenameFromUrlOrName(img.getImageUrl());
					String path = "media/" + ad.getId() + "/images/" + img.getOrderIndex() + "-" + imgName;
					assets.add(new PlaybackManifest.Asset(path, img.getOrderIndex(), img.getDisplaySeconds()));
				});
			}

			items.add(new PlaybackManifest.Item(ad.getId(), ad.getType(), maxShows, assets));
		}

		return new PlaybackManifest(date, items);
	}

	private void addFile(ZipOutputStream zos, Path source, String zipEntryName) throws IOException {
		if (!Files.exists(source) || !Files.isRegularFile(source)) {
			throw new IOException("Arquivo não encontrado para empacotamento: " + source);
		}

		ZipEntry entry = new ZipEntry(zipEntryName);
		entry.setTime(Files.getLastModifiedTime(source).toMillis());
		zos.putNextEntry(entry);

		try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(source))) {
			byte[] buffer = new byte[8192];
			int read;
			while ((read = in.read(buffer)) >= 0) {
				zos.write(buffer, 0, read);
			}
		}

		zos.closeEntry();
	}

	private String safeFilenameFromUrlOrName(String value) {
		if (value == null) {
			return "unknown";
		}
		String v = value.trim();
		if (v.isEmpty()) {
			return "unknown";
		}

		// remove query/fragment se for URL
		int q = v.indexOf('?');
		if (q >= 0)
			v = v.substring(0, q);
		int h = v.indexOf('#');
		if (h >= 0)
			v = v.substring(0, h);

		// pega último segmento
		int slash = Math.max(v.lastIndexOf('/'), v.lastIndexOf('\\'));
		String name = (slash >= 0 && slash < v.length() - 1) ? v.substring(slash + 1) : v;

		// sanitiza
		name = name.replaceAll("[\\r\\n\\t]", "_");
		name = name.replace("..", "_");
		name = name.replace("/", "_").replace("\\", "_");

		return name.isBlank() ? "unknown" : name;
	}

	private String requiredNonBlank(String value, String propertyName) {
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Propriedade obrigatória não configurada: " + propertyName);
		}
		return value.trim();
	}
}