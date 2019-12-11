package com.dongfang.advanced.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    public static void close(Closeable... targets) {
        for (Closeable target : targets) {
            try {
                if (target != null) {
                    target.close();
                }
            } catch (IOException e) {
                System.out.println("close target " + target + " with error " + e);
            }
        }
    }
}
