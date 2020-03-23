package com.dongfang.advanced.jdbc.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisDemo {
    private String host = "localhost";
    private int port = 6379;
    private Jedis jedis;

    @Before
    public void prepare() {
        // 获得redis的连接
        jedis = new Jedis(host, port);
    }

    @After
    public void clear() {
        jedis.close();
    }

    /**
     * java端通过Jedis操作redis服务器
     */
    @Test
    public void testConnectRedis() {

        System.out.println("jedis.ping() = " + jedis.ping());
    }

    /**
     * 测试String类型
     *      Redis中有哪些命令，Jedis 中就有哪些方法
     */
    @Test
    public void testString() {
        jedis.set("strName", "字符串的名称");
        String strName = jedis.get("strName");
        System.out.println("strName = " + strName);
    }

    /**
     * Redis的作用：减轻数据库（MySQL）的压力
     *      判断某key是否存在，存在，就在redis中查询
     *                      不存在，就查询数据库，并将查出的数据存入redis
     */
    @Test
    public void versusRedisAndMySql() {
        String key = "appName";
        if (jedis.exists(key)) {
            String result = jedis.get(key);
            System.out.println("redis 数据库中查询 result = " + result);
        } else {
            // 去数据库中查询
            String result = "wei chat";
            jedis.set(key, result);
            System.out.println("数据库中查询得到 result = " + result);
        }

    }
}
