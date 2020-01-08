package com.dongfang.advanced.dynamic;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 字节码操作：
 *      Java动态性的两种常见实现方式：
 *          -- 字节码操作
 *          -- 反射
 *      运行时操作字节码可以让我们实现如下功能
 *          -- 动态生成新的类
 *          -- 动态改变某个类的结构（添加/删除/修改/ 新的属性 方法）
 *      优势：
 *          -- 比反射开销小，性能高
 *          -- javassist性能高于反射，低于ASM
 *      常见的字节码操作库：
 *          BCEL
 *              -- Byte Code Engineering Library 是Apache Software Foundation的Jakarta项目的一部分，BCEL是java
 *                 class working广泛使用的一种框架，它可以深入jvm汇编语言进行类操作，BCEL与javassist有不同的处理字节码的
 *                 方法，BCEL在jvm指令上进行操作，有丰富的jvm指定支持，而java assist是源代码级别
 *          ASM
 *              -- 是一个轻量级的java字节码操作框架，涉及到jvm底层的操作和指令
 *          CGLIB  (Code Generation Library)
 *              -- 是一个强大的，高性能的，高质量的Code生成类库，基于ASM实现
 *          Javassit
 *              -- 是一个开源的分析、编辑和创建java字节码的类库，性能较ASM差，和cglib并不多，
 *                  但是使用简单，很多框架在使用
 *
 *          API
 *              -- javassist的最外层API和java的反射中的API颇为类似，
 *                  主要由CtClass CtMethod CtField几个类组成，用以执行和jdk反射
 *                  API中java.lang.Class java.lang.reflect.Method java.lang.reflect.Field
 *                  相同的操作
 */
public class ByteCodeOperation {

    /**
     * 创建一个全新的类，生成class后反编译查看
     * package com.dongfang.advanced.dynamic;
     *
     * public class Pseudo {
     *     private String name;
     *
     *     public void setName(String var1) {
     *         this.name = var1;
     *     }
     *
     *     public String getName() {
     *         return this.name;
     *     }
     *
     *     public Pseudo(String var1) {
     *         this.name = this.name;
     *     }
     * }
     */
    @Test
    public void buildNewClassByJavassist() throws CannotCompileException, NotFoundException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.dongfang.advanced.dynamic.Pseudo");

        // 创建属性
        CtField nameField = CtField.make("private String name;", ctClass);
        ctClass.addField(nameField);

        // 创建方法
        CtMethod setName = CtMethod.make("public void setName(String name) { this.name = name;}", ctClass);
        CtMethod getName = CtMethod.make("public String getName() { return this.name;}", ctClass);
        ctClass.addMethod(setName);
        ctClass.addMethod(getName);

        // 添加构造器
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, ctClass);
        ctConstructor.setBody("{this.name = name;}");
        ctClass.addConstructor(ctConstructor);
        ctClass.writeFile("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources");
    }

    @Test
    public void testGetClassInformationByJavassist() throws NotFoundException, IOException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get("com.dongfang.advanced.dynamic.User");

        byte[] bytes = ctClass.toBytecode();
        System.out.println("Arrays.toString(bytes) = " + Arrays.toString(bytes));

        System.out.println("ctClass.getName() = " + ctClass.getName());
        System.out.println("ctClass.getSimpleName() = " + ctClass.getSimpleName());
    }

    /**
     * 在类中添加新的方法，通过反射执行
     */
    @Test
    public void testAddNewMethodByJavassist() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get("com.dongfang.advanced.dynamic.User");

        CtMethod helloMethod = CtMethod.make("public void helloWorld() {System.out.println(\"Hello World\");}", ctClass);
        ctClass.addMethod(helloMethod);

        CtMethod addMethod = new CtMethod(CtClass.intType, "add", new CtClass[]{CtClass.intType, CtClass.intType}, ctClass);
        addMethod.setModifiers(Modifier.PUBLIC);
        addMethod.setBody("{System.out.println(\"Hello World\");return $1 + $2;}");
        ctClass.addMethod(addMethod);


        // 通过反射调用新的方法
        Class aClass = ctClass.toClass();
        Object object = aClass.newInstance();
        Method add = aClass.getDeclaredMethod("add", int.class, int.class);
        Object result = add.invoke(object, 200, 300);
        System.out.println("result = " + result);
    }

    @Test
    public void testUpdateOldMethod() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get("com.dongfang.advanced.dynamic.User");

        CtMethod helloMethod = ctClass.getDeclaredMethod("hello", new CtClass[]{pool.get("java.lang.String")});
        helloMethod.insertBefore("System.out.println(\" hello start, arg is \" + $1);");
        helloMethod.insertAfter("System.out.println(\" hello end\");");

//        helloMethod.insertAt(); 在某一行前面加
        Class aClass = ctClass.toClass();
        Object object = aClass.newInstance();
        Method hello = aClass.getDeclaredMethod("hello", String.class);
        hello.invoke(object, "dong fang");
    }

    @Test
    public void testUpdateFields() throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get("com.dongfang.advanced.dynamic.User");

        // 创建属性
        CtField nameField = CtField.make("private String userName = \"dong fang\";", ctClass);
        ctClass.addField(nameField);

        CtField salaryField = new CtField(CtClass.doubleType, "salary", ctClass);
        salaryField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(salaryField, String.valueOf(1000.0));

        ctClass.addMethod(CtNewMethod.getter("getSalary", salaryField));
        ctClass.addMethod(CtNewMethod.setter("setSalary", salaryField));
        ctClass.writeFile("D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\AdvancedJava\\src\\main\\resources");
    }

}
