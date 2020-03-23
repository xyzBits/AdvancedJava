package com.dongfang.advanced.concurrent.thread;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class WebDownloader {
    public int[] getLeastNumbers(int[] arr, int k) {
        if (arr == null || arr.length == 0) return new int[] {0};


        int[] res = new int[k];
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        for (int num : arr) {
            if (queue.size() < k) {
                queue.offer(num);
            } else if (num >= queue.peek()) {
                queue.poll();
                queue.offer(num);
            }
        }
        int i = 0;
        while (!queue.isEmpty()) {
            res[i++] = queue.poll();
        }
        return res;
    }

    @Test
    public void test() {
        int[] arr = {3, 2, 1};
        int[] res = getLeastNumbers(arr, 2);
        System.out.println("Arrays.toString(res) = " + Arrays.toString(res));
    }

    @Test
    public void testPriorityQueue() {
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int[] arr = {0, 1, 2, 1, 3, -1};
        for (int i : arr) {
            queue.offer(i);
        }
        System.out.println("queue.peek() = " + queue.peek());

        System.out.println("queue.size() = " + queue.size());
        Object[] objects = queue.toArray();
        while (!queue.isEmpty()) {
            System.out.println("queue.poll() = " + queue.poll());
        }
    }


    public void download(String url, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，download方法出现问题");
        }
    }
}
