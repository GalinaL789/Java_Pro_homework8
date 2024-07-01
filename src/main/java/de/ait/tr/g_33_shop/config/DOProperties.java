package de.ait.tr.g_33_shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="do")
public class DOProperties {
    private String accessKey;
    private String secretKey;
    private String url;
    private String region;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;

    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public String getRegion() {
        return region;
    }
}
