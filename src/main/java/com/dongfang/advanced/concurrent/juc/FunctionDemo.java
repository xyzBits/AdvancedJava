package com.dongfang.advanced.concurrent.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionDemo {
    /**
     * R apply(T t)
     */
    @Test
    public void testFunctionInterface() {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };

        function = (t) -> {
            return Integer.parseInt(t);
        };

        function = (t) -> Integer.parseInt(t);
        function = t -> Integer.parseInt(t);
        function = Integer::parseInt;

        System.out.println("function.apply(\"123\") = " + function.apply("123"));
    }

    /**
     * boolean test(T t)
     */
    @Test
    public void testPredicateInterface() {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty();
            }
        };

        predicate = (s) -> {
            return s.isEmpty();
        };

        predicate = s -> s.isEmpty();
        predicate = String::isEmpty;

        System.out.println("predicate.test(\"hello\") = " + predicate.test("hello"));

        List<String> list = Arrays.asList("hello", "world", "fuck");
        list.stream().filter(s -> s.contains("o")).forEach(System.out::println);
        String result = list.stream().reduce((t, u) -> t + " " + u).get();
        System.out.println("result = " + result);
    }


    /**
     * void accept(T t)
     */
    @Test
    public void testConsumeInterface() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s.toUpperCase() = " + s.toUpperCase());
            }
        };

        consumer = (s) -> {
            System.out.println("s.toUpperCase() = " + s.toUpperCase());
        };

        consumer = s -> System.out.println("s.toUpperCase() = " + s.toUpperCase());
        consumer = System.out::println;

        consumer.accept("da sha bi");
    }

    /**
     * T get()
     */
    @Test
    public void testSupplierInterface() {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return UUID.randomUUID().toString();
            }
        };
        supplier = () -> UUID.randomUUID().toString();
        System.out.println("supplier.get() = " + supplier.get());
    }

    @Data
    @AllArgsConstructor
    @ToString
    private static class User {
        private int id;
        private String name;
        private int age;
    }

    /**
     * 集合 mysql本质是存储东西的
     * 计算应该都交给流来操作
     */
    @Test
    public void testStream() {
        List<User> list = Arrays.asList(
                new User(1, "a", 21),
                new User(2, "b", 22),
                new User(3, "c", 23),
                new User(4, "d", 24),
                new User(6, "e", 25)
        );
        list.parallelStream().
                filter(u -> u.getId() % 2 == 0).
                filter(u -> u.getAge() > 23).
                /*map(u -> {
                    u.setName(u.getName().toUpperCase());
                    return u;
                }).*/
                peek(u -> u.setName(u.getName().toUpperCase())).
                sorted((u1, u2) -> u2.getName().compareTo(u1.getName())).
                forEach(System.out::println);

        System.out.println("Runtime.getRuntime().availableProcessors() = " + Runtime.getRuntime().availableProcessors());
    }
}