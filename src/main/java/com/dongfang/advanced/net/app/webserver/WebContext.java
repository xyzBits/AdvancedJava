package com.dongfang.advanced.net.app.webserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebContext {
    private List<Entity> entities;
    private List<Mapping> mappings;

    // (servlet-name, servlet-class)
    private Map<String, String> entityMap = new HashMap<>();
    // (url-pattern, servlet-name)
    private Map<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        // 将list转成map
        for (Entity entity : entities) {
            entityMap.put(entity.getName(), entity.getClassName());
        }

        for (Mapping mapping : mappings) {
            Set<String> urlPatterns = mapping.getUrlPatterns();
            for (String urlPattern : urlPatterns) {
                mappingMap.put(urlPattern, mapping.getName());
            }
        }
    }


    /**
     * 通过url找到了对应的class
     * @param urlPattern
     * @return
     */
    public String getServletClassName(String urlPattern) {
        return entityMap.get(mappingMap.get(urlPattern));
    }
}
