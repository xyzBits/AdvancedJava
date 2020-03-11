package com.dongfang.advanced.jvm.memorystructure;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StringTable堆内存溢出 {
    @Test
    public void testHeapOOM() {
        List<String> list = new ArrayList<>();
        int i = 0;
        try {
            for (int j = 0; j < 26000000; j++) {
                list.add(String.valueOf(i).intern());
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("i = " + i);
        }
    }
}
