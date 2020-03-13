package com.dongfang.advanced.jvm.memorystructure.direct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 直接内存Direct Memory
 *     1、常见于NIO操作时，用于数据缓冲区
 *     2、分配回收成本较高
 *     3、不受jvm内存回收管理
 *
 *         读取大文件时，java api调用后，再调用system call，将文件拷贝到系统内存的缓冲区， 这里jvm无法访问，
 *         然后再将从系统内存copy到jvm的堆内存，两次copy，非常耗时
 *
 *         使用direct buffer 在操作系统内存，分配缓冲区，这个区域jvm可以直接访问，os也可以访问，
 *         是os jvm共享的区域，读大文件时，直接读取到这个缓冲区，少了一次复制操作
 *     4、分配和回收原理
 *         1、使用了Unsafe对象完成直接内存的分配回收，并且回收需要主动调用freeMemory方法
 *         2、ByteBuffer的实现类内部，使用了Cleaner（虚引用）来监测ByteBuffer对象，一旦ByteBuffer
 *             对象被垃圾回收，那么就会由ReFerenceHandler线程通过Cleaner的clean方法调用freeMemory来释放直接内存
 * java.lang.OutOfMemoryError: Direct buffer memory
 */
public class 直接内存溢出 {
    private static int _100MB = 1024 * 1024 * 100;

    public static void main(String[] args) {
        List<ByteBuffer> list = new ArrayList<>();
        int i = 0;
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_100MB);
                list.add(byteBuffer);
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("i = " + i);
        }
    }
}