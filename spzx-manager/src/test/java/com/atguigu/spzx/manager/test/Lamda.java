package com.atguigu.spzx.manager.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.test
 * @className: Lamda
 * @author: XiaoHB
 * @date: 2024/2/6 17:30
 */
public class Lamda {
    
    public static void main(String[] args) {
        MyInterface myInterface1 = (a,b) -> {
            System.out.println(a+b);
        };
    }
}
