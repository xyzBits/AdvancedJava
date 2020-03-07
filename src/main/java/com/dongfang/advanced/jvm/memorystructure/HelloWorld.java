package com.dongfang.advanced.jvm.memorystructure;

/**
 * 常量池，就是一张表，虚拟机指令根据这张表找到要执行的类名、方法名、参数类型、字面量等信息
 * 运行时常量池，常量池是*.class文件中的，当该类被 加载时，它的常量池信息就会被放入运行时常量池，并把里面的符号地址变为真实地址
 * 二进制字节码（类基本信息、常量池、类方法定义（包含虚拟机指令））
 * javap -v HelloWorld.class
 * Classfile /D:/ubuntu/learn/JavaWeb/MavenProject/maven03/AdvancedJava/target/classes/com/dongfang/advanced/jvm/memorystructure/HelloWor
 * ld.class
 *   Last modified 2020年3月7日; size 617 bytes
 *   MD5 checksum 9b00b466a48ef43bd5835823e5ee0f76
 *   Compiled from "HelloWorld.java"
 * public class com.dongfang.advanced.jvm.memorystructure.HelloWorld
 *   minor version: 0
 *   major version: 54
 *   flags: (0x0021) ACC_PUBLIC, ACC_SUPER
 *   this_class: #5                          // com/dongfang/advanced/jvm/memorystructure/HelloWorld
 *   super_class: #6                         // java/lang/Object
 *   interfaces: 0, fields: 0, methods: 2, attributes: 1
 * Constant pool:
 *    #1 = Methodref          #6.#20         // java/lang/Object."<init>":()V
 *    #2 = Fieldref           #21.#22        // java/lang/System.out:Ljava/io/PrintStream;
 *    #3 = String             #23            // Hello World
 *    #4 = Methodref          #24.#25        // java/io/PrintStream.println:(Ljava/lang/String;)V
 *    #5 = Class              #26            // com/dongfang/advanced/jvm/memorystructure/HelloWorld
 *    #6 = Class              #27            // java/lang/Object
 *    #7 = Utf8               <init>
 *    #8 = Utf8               ()V
 *    #9 = Utf8               Code
 *   #10 = Utf8               LineNumberTable
 *   #11 = Utf8               LocalVariableTable
 *   #12 = Utf8               this
 *   #13 = Utf8               Lcom/dongfang/advanced/jvm/memorystructure/HelloWorld;
 *   #14 = Utf8               main
 *   #15 = Utf8               ([Ljava/lang/String;)V
 *   #16 = Utf8               args
 *   #17 = Utf8               [Ljava/lang/String;
 *   #18 = Utf8               SourceFile
 *   #19 = Utf8               HelloWorld.java
 *   #20 = NameAndType        #7:#8          // "<init>":()V
 *   #21 = Class              #28            // java/lang/System
 *   #22 = NameAndType        #29:#30        // out:Ljava/io/PrintStream;
 *   #23 = Utf8               Hello World
 *   #24 = Class              #31            // java/io/PrintStream
 *   #25 = NameAndType        #32:#33        // println:(Ljava/lang/String;)V
 *   #26 = Utf8               com/dongfang/advanced/jvm/memorystructure/HelloWorld
 *   #27 = Utf8               java/lang/Object
 *   #28 = Utf8               java/lang/System
 *   #29 = Utf8               out
 *   #30 = Utf8               Ljava/io/PrintStream;
 *   #31 = Utf8               java/io/PrintStream
 *   #32 = Utf8               println
 *   #33 = Utf8               (Ljava/lang/String;)V
 * {
 *   public com.dongfang.advanced.jvm.memorystructure.HelloWorld();
 *     descriptor: ()V
 *     flags: (0x0001) ACC_PUBLIC
 *     Code:
 *       stack=1, locals=1, args_size=1
 *          0: aload_0
 *          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *          4: return
 *       LineNumberTable:
 *         line 3: 0
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       5     0  this   Lcom/dongfang/advanced/jvm/memorystructure/HelloWorld;
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=1, args_size=1
 *          0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *          3: ldc           #3                  // String Hello World
 *          5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *          8: return
 *       LineNumberTable:
 *         line 5: 0
 *         line 6: 8
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       9     0  args   [Ljava/lang/String;
 * }
 * SourceFile: "HelloWorld.java"
 */
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
