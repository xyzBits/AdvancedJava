package com.dongfang.advanced.jvm.memorystructure;

import org.junit.Test;

/**
 * -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
 */
public class StringTable垃圾回收 {
    @Test
    public void testStringTableGC() {
        int i = 0;
        try {
            for (int j = 0; j < 10000; j++) {
                String.valueOf(j).intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("i = " + i);
        }
    }
}
