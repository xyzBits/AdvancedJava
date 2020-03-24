package com.dongfang.advanced.jdbc.redis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SpringDataRedis {
    private String resource = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources\\spring-redis.xml";
    private RedisTemplate<String, String> template;

    @Before
    public void prepare() {
        ApplicationContext context = new FileSystemXmlApplicationContext(resource);
        template = context.getBean(RedisTemplate.class);
    }

    @Test
    public void testSpringRedisString() {
        System.out.println("template = " + template);
        String key = "newKey";
        ValueOperations<String, String> stringOpt = template.opsForValue();
        if (template.hasKey(key)) {
            String value = stringOpt.get(key);
            System.out.println("value from redis = " + value);
        } else {
            stringOpt.set(key, "template exercise");
        }


    }

}
