package com.xht.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;

/**
 * 启动类
 *
 * @author xht
 **/
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        Iterator<String> beanNamesIterator = run.getBeanFactory().getBeanNamesIterator();
        while (beanNamesIterator.hasNext()) {
            String beanName = beanNamesIterator.next();
            if (beanName.startsWith("com.xht")) {
                System.out.println(beanName + "：" + run.getBean(beanName).getClass());
            }
        }
    }
}