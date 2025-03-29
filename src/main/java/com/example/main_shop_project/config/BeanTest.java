package com.example.main_shop_project.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class BeanTest {
    private final ApplicationContext context;

    public BeanTest(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void checkBean() {
        boolean exists = context.containsBean("hiddenHttpMethodFilter");
        System.out.println("HiddenHttpMethodFilter 존재 여부: " + exists);
    }
}
