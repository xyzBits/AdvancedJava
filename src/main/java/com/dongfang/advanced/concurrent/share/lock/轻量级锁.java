package com.dongfang.advanced.concurrent.share.lock;

/**
 * Monitor
 * 	1、翻译为监视器或者管程
 * 	2、每个java对象都可以关联一个Monitor对象，如果使用synchronized给对象上锁后
 * 		该对象头的Mark Word中就被设置指向Monitor对象
 * 	3、刚开始Monitor中Owner为null
 * 		当一个线程进来时，占据owner，其他线程进来后，进入entryList等待队列
 * 		只能有一个线程作为owner，owner执行完后，唤醒entryList中的线程进行竞争
 * 	synchronized(lock) {
 * 			// 有异常也能释放
 * 	     让lock对象关联monitor，monitor是由os提供的，是重量级的锁，影响性能
 *        }
 *
 *
 * 	1、轻量级锁：
 * 		使用场景：如果一个对象虽然有多线程访问，但是多线程访问的时间是错开的（也就是
 * 			没有竞争），那么可以使用轻量级锁来优化
 * 		轻量级锁对使用者是透明的，语法仍是synchronized
 *
 */
public class 轻量级锁 {
}
