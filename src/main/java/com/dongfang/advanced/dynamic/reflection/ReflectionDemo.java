package com.dongfang.advanced.dynamic.reflection;

import com.dongfang.advanced.annotation.TableMapping;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 动态语言     运行时改变程序的结构
 *      程序运行时，可以改变程序结构或者变量类型，典型的python ruby javascript
 *      C C++ JAVA不是动态语言，但是Java有一定的动态性，我们可以利用反射机制，字节码操作获得类似动态语言的特性
 *      Java是准动态语言
 *
 *      反制机制
 *          指的是可以在运行时加载一些编译期间完全未知的类
 *          --程序在运行状态中，可以动态加载一个只有名称的类，对于任意一个已加载的类，能够知道
 *            这个类中所有的属性和方法，对于一个对象，我们能够调用它的任意一个方法和属性
 *            Class clazz = Class.forName("java.lang.String);
 *            加载完类之后，在堆内存中，就产生了一个Class类型的对象，一个类只有一个Class对象，这个对象包含了完整的类的结构信息，
 *            我们可以通过这个对象看到类的结构，这个对象就像一面镜子，透过这个镜子看到类的纸醉金迷，形象地称为反射
 *
 *
 *
 *         反射的作用：
 *              1、动态加载类、动态获取类的信息（属性、方法、构造器）
 *              2、动态构造对象
 *              3、动态调用类和对象的任意方法、构造器
 *              4、动态调用和处理属性
 *              5、获取泛型信息
 *              6、处理注解
 *
 *        反制机制的性能问题：
 *              setAccessible
 *                  --启用和禁用访问安全检查的开关，值为true时，则指示反射的对象在使用时应该取消java语言访问检查
 *                     值为false时，则指示应该实施java语言访问检查，并不是为true就能访问，为false就不能访问
 *                  -- 禁止安全检查，可以提高反射的运行速度
 *                     可以使用cglib java assist字节码操作库
 *
 *        反射操作泛型：
 *              Java采用泛型擦除的机制来引入泛型，Java中的泛型仅仅是给编译器java以使用的，确保数据的安全性和免去强制类型转换的麻烦
 *              但是，一旦编译完成，所有的和泛型相关类型全部都擦除
 *
 *              为了通过反射操作这些类型，以迎合实际开发的需要，java新增了ParameterizedType GenericArrayType
 *              TypeVariable WildcardType几种类型来代表不能被扫到Class类中的类型但是又和原始类型齐名的类型
 *                  ParameterizedType 表示一种参数化的类型 比如Collection<String>
 *                  GenericArrayType  表示一种元素类型是参数化类型或者类型变量的数组类型
 *                  TypeVariable      是各种类型变量的公共父接口
 *                  WildcardType代表一种通配符表达式 比如？ extend Number ? super Integer
 *
 */
@SuppressWarnings("all")
public class ReflectionDemo {

    private static String path = "com.dongfang.advanced.dynamic.reflection.User";
    /**
    * 一个类被 加载后，jvm会创建一个此类的Class对象，这个对象中封装了类的整个结构信息
     * 一个类只对应一个Class对象
     *
     * 创建Class对象的方法
     *      1、全类名：Class.forName("全类名")
     *      2、类型.class String.class
     *      3、对象.getClass()   "Hello World".class
     *      4、基本类型，数组（与维数有关） void
    * */
    @Test
    public void testGetClassObject() throws ClassNotFoundException {
        // 1、全类名
        Class<?> clazz = Class.forName(path);
        System.out.println("clazz = " + clazz);
        System.out.println("Class.forName(path).equals(clazz) = " + Class.forName(path).equals(clazz));

        // 2、类型
        Class<String> stringClass = String.class;

        // 3、对象
        Class<? extends String> aClass = "Hello World".getClass();
        System.out.println("stringClass.equals(aClass) = " + stringClass.equals(aClass));
    }

    /**
     * 反应投身API，获取类的信息（类的名字、属性、方法、构造器）
     * getDeclaredXxx()获取类中声明的构造器，方法，获取指定的方法，要传方法名，参数类型.class
     * getXXX()         获取类中和父类中代开的构造器方法，获取指定方法，要传方法名，参数类型.class
     */
    @Test
    public void testGetClassInformation() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class<?> aClass = Class.forName(path);
        // 1、包与包名
        System.out.println("aClass.getPackage() = " + aClass.getPackage());
        System.out.println("aClass.getPackageName() = " + aClass.getPackageName());

        // 2、类名
        System.out.println("aClass.getName() full name = " + aClass.getName());
        System.out.println("aClass.getSimpleName() = " + aClass.getSimpleName());

        // 3、属性
        System.out.println("aClass.getDeclaredFields() = " + Arrays.toString(aClass.getDeclaredFields()));
        System.out.println("aClass.getFields() = " + Arrays.toString(aClass.getFields())); // 只能获取public的属性
        System.out.println("aClass.getDeclaredField(\"id\") = " + aClass.getDeclaredField("id"));

        // 4、构造器
        System.out.println("aClass.getDeclaredConstructors() = " + Arrays.toString(aClass.getDeclaredConstructors()));
        System.out.println("aClass.getConstructors() = " + Arrays.toString(aClass.getConstructors()));
        System.out.println("aClass.getDeclaredConstructor(int.class) = " + aClass.getDeclaredConstructor(int.class));

        // 5、方法
        System.out.println("aClass.getDeclaredMethods() = " + Arrays.toString(aClass.getDeclaredMethods())); // 类中声明的所有方法
        System.out.println("aClass.getMethods() = " + Arrays.toString(aClass.getMethods())); // 公开方法，包含父类的
        System.out.println("aClass.getDeclaredMethod(\"testPrivateMethod\", Date.class) = " + aClass.getDeclaredMethod("testPrivateMethod", Date.class));
    }

    /**
     * 使用反射的信息
     *      1、创建对象，推荐用构造器的newInstance方法
     *      2、调用方法 method.invoke(object, args)
     *      3、设置属性，setAccessible() 可修改私有属性
     */
    @Test
    public void testUseClassInformation() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class<?> aClass = Class.forName(path);

        // 1、创建对象 class.newInstance()过时方法
        User user = (User) aClass.newInstance();

        // 1、创建对象，获得构造器后，调用构造器的newInstance(args)
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(int.class, String.class, int.class);
        User dongFang = (User) declaredConstructor.newInstance(1, "Dong fang", 20);
        System.out.println("dongFang = " + dongFang);

        // 2、操作方法，method.invoke(object) 传入构造器创建的对象
        // 无参方法，获取时不用填参数类型
        Method hello = aClass.getDeclaredMethod("hello");
        hello.invoke(dongFang);

        // 3、操作属性，私有属性要设置可访问，可以set设置属性的新值
        Field nameField = aClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(dongFang, "Xi fang");
        System.out.println("dongFang = " + dongFang);
    }


    /**
     * 频繁调用反射时，setAccessible(true) 性能提交4倍
     */
    @Test
    public void testReflectionPerformance() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        long frequency = 100_000_000_000L;
        User user = new User();
        long time1 = System.currentTimeMillis();
        for (long i = 0; i < frequency; i++) {
            user.getName();
        }
        long time2 = System.currentTimeMillis();
        System.out.println("origin  = " + (time2 - time1));

        Class<?> aClass = Class.forName(path);
        User user1 = (User) aClass.newInstance();
        Method getName = aClass.getDeclaredMethod("getName");
        getName.setAccessible(false);
        time1 = System.currentTimeMillis();
        for (long i = 0; i < frequency; i++) {
            getName.invoke(user1);
        }
        time2 = System.currentTimeMillis();
        System.out.println("setAccessible false = " + (time2 - time1));

        getName.setAccessible(true);
        time1 = System.currentTimeMillis();
        for (long i = 0; i < frequency; i++) {
            getName.invoke(user1);
        }
        time2 = System.currentTimeMillis();
        System.out.println("setAccessible true  = " + (time2 - time1));
    }

    /**
     * 通过反制获取泛型
     *      getGenericParameterTypes
     *      getGenericReturnType
     */
    @Test
    public void testGeneric() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> aClass = Class.forName(path);
        Method testGeneric = aClass.getDeclaredMethod("testGeneric", Map.class, List.class);
        Type[] genericParameterTypes = testGeneric.getGenericParameterTypes();
        for (Type parameterType : genericParameterTypes) {
            System.out.println("#" + parameterType);
            if (parameterType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) parameterType).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    System.out.println("泛型类型：" + actualTypeArgument);
                }
            }
        }
        System.out.println();

        // 获取返回值的泛型
        Type genericReturnType = testGeneric.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            System.out.println("#" + genericReturnType);
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println("泛型类型：" + actualTypeArgument);
            }
        }
    }


    /**
     * 通过反制获取注解
     *      1、获取类上的注解
     *          1-1、获取所有注解
     *          1-2、获取某个注解
     *      2、获取属性，方法，构造器上的注解，
     *          2-1、先获取属性，方法，构造器
     *          2-2、获取所有注解，获取某个注解
     */
    @Test
    public void testGetAnnotation() throws ClassNotFoundException, NoSuchFieldException {
        String fullName = "com.dongfang.advanced.annotation.Student";
        Class<?> aClass = Class.forName(fullName);
        // 获取类上的所有注解
        Annotation[] annotations = aClass.getAnnotations();
        System.out.println("annotations = " + Arrays.deepToString(annotations));

        // 获取类上的指定注解
        System.out.println("aClass.getDeclaredAnnotation(TableMapping.class) = " + aClass.getDeclaredAnnotation(TableMapping.class));

        //
        Field nameField = aClass.getDeclaredField("studentName");
        System.out.println("nameField.getDeclaredAnnotations() = " + Arrays.deepToString(nameField.getDeclaredAnnotations()));
    }

    /**
     * 获取方法参数上的注解
     */
    @Test
    public void testGetMethodInformation() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> aClass = Class.forName(path);
        Method testParamAnnotation = aClass.getDeclaredMethod("testParamAnnotation", String.class, int.class);
        Annotation[][] parameterAnnotations = testParamAnnotation.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            System.out.println("Arrays.toString(parameterAnnotation) = " + Arrays.toString(parameterAnnotation));
        }
    }
}
