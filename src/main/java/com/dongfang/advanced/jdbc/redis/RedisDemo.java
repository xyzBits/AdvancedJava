package com.dongfang.advanced.jdbc.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * jedis完成对hash类型的操作
     *      1、hash存储一个对象
     *          判断redis中是否存在该key，如果存在，直接返回对应值
     *          如果不存在，查询数据库，并将查询的结果存入redis，并返回给用户
     */
    @Test
    public void testHash() {
        String key = "users";
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("jedis.hget(key, \"name\") = " + jedis.hget(key, "name"));
            System.out.println("从redis中查询的结果--->");
            System.out.println(map.get("id") + "\t" + map.get("name") + "\t" + map.get("age") + "\t" + map.get("remark"));
        } else {
            // 赋值查询并返回
            jedis.hset(key, "id", "1");
            jedis.hset(key, "name", "dongfang");
            jedis.hset(key, "age", "22");
            jedis.hset(key, "remark", "nan ");

        }
    }



    /**
     * 直接存储java bean
     */
    @Test
    public void testHashBean() {
        // users getUserById(int id)
        int id = 1;
        String key = User.getKeyName() + id;
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("map.get(\"id\") = " + map.get("id"));
            System.out.println("map.get(\"name\") = " + map.get("name"));
            System.out.println("map.get(\"password\") = " + map.get("password"));
            System.out.println("map.get(\"age\") = " + map.get("age"));
        } else {
            User user = new User(id, "dongfang", "123456", 23);
            Map<String, String> hash = new HashMap<>();
            hash.put("id", user.getId() + "");
            hash.put("name", user.getUserName());
            hash.put("password", user.getPassword());
            hash.put("age", user.getPassword() + "");
            jedis.hmset(key, hash);
            System.out.println("MySQL 查询的结果 是" + user);
        }

    }
}
