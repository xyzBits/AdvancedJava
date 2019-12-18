package com.dongfang.advanced.net.app.webserver;

/**
 *     <servlet>
 *         <servlet-name>FirstHttpServlet</servlet-name>
 *         <servlet-class>com.dongfang.javaweb.servlet.ch1_http.FirstHttpServlet</servlet-class>
 *     </servlet>
 */
public class Entity {
    private String name;
    private String className;

    public Entity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"className\":\"")
                .append(className).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
