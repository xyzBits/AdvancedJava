package com.dongfang.advanced.net.app.webserver.core;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> urlPatterns;

    public Mapping() {
        urlPatterns = new HashSet<>();
    }

    public void addUrl(String url) {
        this.urlPatterns.add(url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(Set<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"urlPatterns\":")
                .append(urlPatterns);
        sb.append('}');
        return sb.toString();
    }
}
