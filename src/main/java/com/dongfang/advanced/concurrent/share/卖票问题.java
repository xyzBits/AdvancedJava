package com.dongfang.advanced.concurrent.share;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class 卖票问题 {
    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(1000);
        List<Integer> list = new Vector<>();
        Set<Thread> threadSet = new HashSet<>();
        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(() -> {
                int sell = window.sell(new Random().nextInt(5) + 1);
                try {
                    Thread.sleep(55);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(sell);
            }, "thread-" + i);
            threadSet.add(thread);
            thread.start();
        }

        // 让所有线程先执行完
        for (Thread thread : threadSet) {
            thread.join();
        }
        // 统计卖出的票，剩余的票
        System.out.println("window.getCount() = " + window.getCount());
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println("sum = " + sum);
    }
}


class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}