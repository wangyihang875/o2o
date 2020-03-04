package com.bushengxin.o2o.test;


/*
运行结果：
static block of Super class is initialized
static block of Sub class is initialized in Java
non static blocks in super class is initialized
non static blocks in sub class is initialized
false

从运行结果可以看出：
1.静态变量或代码块初始化早于非静态块和域
2.超类初始化早于子类
3.没使用的类根本不会被初始化，因为他没有被使用
 */

/**
 * Java program to demonstrate class loading and initialization in Java.
 */
public class ClassInitializationTest {

    public static void main(String args[]) throws InterruptedException {

        NotUsed o = null; //this class is not used, should not be initialized
        Child t = new Child(); //initializing sub class, should trigger super class initialization
        System.out.println((Object)o == (Object)t);
    }
}

/**
 * Super class to demonstrate that Super class is loaded and initialized before Subclass.
 */
class Parent {
    static { System.out.println("static block of Super class is initialized"); }
    {System.out.println("non static blocks in super class is initialized");}
}

/**
 * Java class which is not used in this program, consequently not loaded by JVM
 */
class NotUsed {
    static { System.out.println("NotUsed Class is initialized "); }
}

/**
 * Sub class of Parent, demonstrate when exactly sub class loading and initialization occurs.
 */
class Child extends Parent {
    //静态代码块（执行在构造代码块之前，不管该类实例化多少次，静态代码块只会执行一次）
    static { System.out.println("static block of Sub class is initialized in Java "); }
    //构造代码块
    {System.out.println("non static blocks in sub class is initialized");}

    public void play()
    //普通代码块
    {System.out.println("play");}
}