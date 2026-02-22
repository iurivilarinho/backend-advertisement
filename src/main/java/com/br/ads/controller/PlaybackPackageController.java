package com.br.ads.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.ads.services.PlaybackZipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/playback")
public class PlaybackPackageController {

	private final PlaybackZipService zipService;

	public PlaybackPackageController(PlaybackZipService zipService) {
		this.zipService = zipService;
	}

	@GetMapping("/package")
	@Operation(summary = "Pacote do dia (ZIP) com pasta e manifest.json", description = "Retorna um ZIP nomeado dd-MM-yyyy.zip contendo uma pasta dd-MM-yyyy/ com manifest.json e os arquivos. Suporta Range Requests.")
	@ApiResponse(responseCode = "200", description = "ZIP retornado (conteúdo completo).")
	@ApiResponse(responseCode = "206", description = "ZIP retornado (conteúdo parcial via Range).")
	@ApiResponse(responseCode = "404", description = "Não há anúncios ativos para a data informada.")
	public ResponseEntity<Resource> getPackage(@RequestParam(required = false) LocalDate date,
			HttpServletRequest request) throws IOException {

		LocalDate target = (date == null) ? LocalDate.now() : date;

		PlaybackZipService.ZipFile zip = zipService.getOrCreateZip(target);
		if (!zip.exists()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Resource resource = new UrlResource(zip.path().toUri());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // evita incompatibilidade por media type
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zip.filename() + "\"");
		headers.setCacheControl(CacheControl.noCache());

		// IMPORTANTE: não setar Content-Length/Content-Range manualmente aqui.
		// O Spring trata Range e responde 206/Content-Range automaticamente quando o
		// cliente envia Range.
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}