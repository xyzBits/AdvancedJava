package com.dongfang.advanced.jvm.memorystructure;

/**
 *
 */
public class 常量池与串池 {
    /**
     * 常量池中的信息，都会被加载到运行时常量池中，这时 a b ab都是常量池中的符号
     * 还没有变为java中的字符串对象
     * ldc # 2将a符号变为a字符串对象，变成对象后，将a变为key，存入到StringTable
     * StringTable[a, b, ab]，并不是事先放到字符串池中，用到的时候才放到里面
     * StringTable将 a b ab符号变成字符串对象，放入串池中，是个hash表，不能扩容
     * 如果串池中没有这个对象，就放入串池，如果有了，就直接用，串池中的字符串对象只会存在一份
     *
     * @param args
     */
    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        /**
         *  public static void main(java.lang.String[]);
         *     descriptor: ([Ljava/lang/String;)V
         *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
         *     Code:
         *       stack=1, locals=5, args_size=1
         *          0: ldc           #2                  // String a
         *          2: astore_1
         *          3: ldc           #3                  // String b
         *          5: astore_2
         *          6: ldc           #4                  // String ab
         *          8: astore_3
         *          9: ldc           #3                  // String b
         *         11: astore        4
         *         13: return
         */
        // 又声明了一个s4，但是还是引用串池中的b
        String s4 = "b";

        String s5 = s1 + s2; // 调用StringBuilder 的append方法
        System.out.println(s5 == s3);
        String s6 = "a" + "b"; // javac在编译期间的优化，在编译期间结果就确定为 ab
        System.out.println("s6 == s3 = " + s6 == s3);
    }
}
