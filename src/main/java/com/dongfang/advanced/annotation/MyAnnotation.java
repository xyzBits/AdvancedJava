package com.dongfang.advanced.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SOURCE 在源文件中有效，即源文件中保留
 * CLASS 在class文件中有效 即class保留
 * RUNTIME 运行时有效，即运行时保留
 * 为Runtime可以被反射机制读取
 */

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value() default ""; // 如果注解中只有一个参数，一般参数名定义成value，赋值时可以直接写，不用写value = ""

    String yourName() default "";

    int yourAge() default 0;

    int id() default -1; // -1表示不存在

    String[] schools() default {"MIT", "HIT"};

}
