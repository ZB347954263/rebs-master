package com.rebs.core.express.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "rebs.express")
public class ExpressProperties {
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private String appId;
    private String appKey;

    private List<Map<String, String>> vendors = new ArrayList<>();

    public List<Map<String, String>> getVendors() {
        return vendors;
    }

    public void setVendors(List<Map<String, String>> vendors) {
        this.vendors = vendors;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
