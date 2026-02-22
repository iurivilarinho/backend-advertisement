// src/main/java/com/cantodeminas/ads/api/dto/PlaybackManifest.java
package response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.ads.enums.AdvertisementType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Manifesto de execução offline incluído no ZIP do dia.")
public class PlaybackManifest {

    @Schema(description = "Data do pacote.", example = "2026-02-22")
    private LocalDate date;

    @Schema(description = "Itens ordenados para execução.")
    private List<Item> items = new ArrayList<>();

    public PlaybackManifest() {
    }

    public PlaybackManifest(LocalDate date, List<Item> items) {
        this.date = date;
        this.items = items;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Schema(description = "Item do manifesto.")
    public static class Item {

        @Schema(description = "ID do anúncio.", example = "100")
        private Long advertisementId;

        @Schema(description = "Tipo do anúncio.", example = "IMAGE")
        private AdvertisementType type;

        @Schema(description = "Máximo de exibições por dia (quando aplicável).", example = "10", nullable = true)
        private Integer maxShowsPerDay;

        @Schema(description = "Lista de arquivos na ordem correta.")
        private List<Asset> assets = new ArrayList<>();

        public Item() {
        }

        public Item(Long advertisementId, AdvertisementType type, Integer maxShowsPerDay, List<Asset> assets) {
            this.advertisementId = advertisementId;
            this.type = type;
            this.maxShowsPerDay = maxShowsPerDay;
            this.assets = assets;
        }

        public Long getAdvertisementId() {
            return advertisementId;
        }

        public AdvertisementType getType() {
            return type;
        }

        public Integer getMaxShowsPerDay() {
            return maxShowsPerDay;
        }

        public List<Asset> getAssets() {
            return assets;
        }

        public void setAdvertisementId(Long advertisementId) {
            this.advertisementId = advertisementId;
        }

        public void setType(AdvertisementType type) {
            this.type = type;
        }

        public void setMaxShowsPerDay(Integer maxShowsPerDay) {
            this.maxShowsPerDay = maxShowsPerDay;
        }

        public void setAssets(List<Asset> assets) {
            this.assets = assets;
        }
    }

    @Schema(description = "Arquivo (asset) incluído no ZIP.")
    public static class Asset {

        @Schema(description = "Caminho relativo dentro da pasta do dia no ZIP.", example = "media/100/images/0-img1.jpg")
        private String path;

        @Schema(description = "Ordem (para imagem).", example = "0", nullable = true)
        private Integer orderIndex;

        @Schema(description = "Duração em segundos (para imagem: displaySeconds; para vídeo pode ser null).", example = "5", nullable = true)
        private Integer durationSeconds;

        public Asset() {
        }

        public Asset(String path, Integer orderIndex, Integer durationSeconds) {
            this.path = path;
            this.orderIndex = orderIndex;
            this.durationSeconds = durationSeconds;
        }

        public String getPath() {
            return path;
        }

        public Integer getOrderIndex() {
            return orderIndex;
        }

        public Integer getDurationSeconds() {
            return durationSeconds;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setOrderIndex(Integer orderIndex) {
            this.orderIndex = orderIndex;
        }

        public void setDurationSeconds(Integer durationSeconds) {
            this.durationSeconds = durationSeconds;
        }
    }
}