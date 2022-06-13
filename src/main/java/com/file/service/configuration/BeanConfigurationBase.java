package com.file.service.configuration;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public abstract class BeanConfigurationBase {

    private final AutowireCapableBeanFactory beanFactory;

    protected BeanConfigurationBase(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    Object create(Class<?> type) {
        return this.beanFactory.createBean(type, AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, true);
    }

}
