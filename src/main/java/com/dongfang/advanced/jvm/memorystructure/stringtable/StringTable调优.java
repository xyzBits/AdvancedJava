package com.dongfang.advanced.jvm.memorystructure.stringtable;

/**
 * 调整StringTable对应的hashtable的桶的个数
 * -XX:StringTableSize=2000000 -XX:PrintStringTableStatistics
 *
 *考虑是否将字符串入池
 *      字符串可能 重复，但是入池后，只会存在一份，减少对内存的占用
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class StringTable调优 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\ubuntu\\learn\\JavaWeb\\book\\框架\\资料-解密JVM\\代码\\jvm\\linux.words"), "utf-8"))) {
            String line = null;
            long start = System.nanoTime();
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                line.intern();
            }
            System.out.println("cost:" + (System.nanoTime() - start) / 1000000);
        }


    }
}
