package com.dongfang.advanced.concurrent.share;

/**
 * Synchronized解决方案
 *      应用之互斥
 *          为了避免发生临界区的竞态我们的发生，有多种手段和目的可以达到
 *              1、阻塞式解决方法：synchronized lock
 *              2、非阻塞式：原子变量
 *          这里使用阻塞式的解决方案，synchronized来解决上述问题，俗称为对象锁，
 *          它采用互斥的方式让同一时刻至多只有一个线程能持有对象锁，其他线程想获取这个对象锁时，
 *          会被 阻塞，这样就能保证拥有锁的线程可以安全地执行临界区内的代码，不用担心上下文切换
 *
 *
 *          注意：互斥和同步都可以用synchronized完成，但是有区别
 *              互斥是保证只有一个线程执行临界区代码
 *              同步是由于线程执行的先后顺序不同，需要一个线程等待其他线程运行到某个点
 *
 *              对象任意，多个线程要使用同一个对象，临界区内代码单线程执行
 *              synchronize(对象) { 线程1 线程2（blocked) 线程1完成，释放锁，唤醒其他线程
 *                  临界区
 *              }
 *
 *
 *
 *              synchronized的类比
 * 	1、synchronized（对象）中的对象，可以想象为一个房间（room）有唯一的入口（门），一次
 * 		只能进入一人进行计算，线程t1 t2想象成2个人。
 * 	2、当线程t1执行到了synchronized(room)时就好比t1进入了这个房间，并锁住了门拿走了钥匙，
 * 		在门内执行counter++代码
 * 	3、这时如果t2也运行到了sychronized(room)时，发现门被锁住了，只能在门外等待，发生了上下文
 * 		切换，阻塞住了
 * 	4、这蹭即使t1的cpu时间片不幸用完了，被剔除了门外（不要误解了锁住了对象就能一直执行下去），
 * 		这时门还是锁住的，t1拿着钥匙，t2线程还在阻塞状态进不来，只有等下次轮到t1自己再次获得
 * 		时间片时才能开门进入
 * 	5、当t1执行完synchronized{}内的代码（couner的值写入主存），这时才会从obj房间里出来，并解开门上的锁，
 * 		唤醒t2线程并把钥匙给它，t2这时才可以进入obj房间，锁住了门拿上钥匙，执行它的counter--
 * 		唤醒锁对象上阻塞的线程
 *
 *
 * 	思考：
 * 		1、synchronized实际是用对象锁保证了临界区内代码的原子性，临界区内的代码对外是不可
 * 			分割的，不会被线程切换所打断
 * 		2、如果把synchronized(obj)放在for循环外面，意味对全部操作作为整体 ---->原子性
 * 		3、两个线程用不同的锁，结果也不可预料，要保护一个东西，用同一个锁 ----> 锁对象
 * 		4、一个加，另一个不加锁，就不会被阻塞信，代码继续执行--->都要上锁
 */
public class Synchronized解决线程安全 {
    // 两个线程对初始值为0的静态变量做一个自增，一个做自减，各做5000
    private static int counter = 0;
    private static final Object room = new Object();

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (room) {
                for (int i = 0; i < 5000; i++) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (room) {
                for (int i = 0; i < 5000; i++) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("counter = " + counter);
    }
}