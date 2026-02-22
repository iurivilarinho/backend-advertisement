package com.br.ads.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ads")
public class AdsStorageProperties {

    private Media media = new Media();
    private Playback playback = new Playback();

    public Media getMedia() {
        return media;
    }

    public Playback getPlayback() {
        return playback;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setPlayback(Playback playback) {
        this.playback = playback;
    }

    public static class Media {
        private String basePath;

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }
    }

    public static class Playback {
        private String bundlePath;

        public String getBundlePath() {
            return bundlePath;
        }

        public void setBundlePath(String bundlePath) {
            this.bundlePath = bundlePath;
        }
    }
}