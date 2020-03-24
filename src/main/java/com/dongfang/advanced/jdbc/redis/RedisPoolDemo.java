package com.dongfang.advanced.jdbc.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolDemo {
    private String host = "localhost";
    private int port = 6379;
    private Jedis jedis;

    @Before
    public void prepare() {
        // 1、连接池 redis pool基本配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5); // 最大连接数
        poolConfig.setMaxIdle(2); // 空闲时保留几个

        // 2、连接池
        JedisPool redisPool = new JedisPool(poolConfig, host, port);
        jedis = redisPool.getResource();
    }

    @After
    public void close() {
        jedis.close();
    }

    @Test
    public void testPoolConnection() {
        String ping = jedis.ping();
        System.out.println("ping = " + ping);
    }
}
