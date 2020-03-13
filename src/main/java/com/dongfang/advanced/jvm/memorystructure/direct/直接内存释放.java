package com.dongfang.advanced.jvm.memorystructure.direct;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 直接内存不能在jconsole中看，因为是os的内存
 */
public class 直接内存释放 {
    private static int _1GB = 1024 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1GB);
        System.out.println("分配完成");
        System.in.read();
        System.out.println("开始释放");
        byteBuffer = null;
        System.gc();
        System.in.read();
    }
}