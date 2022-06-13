/*
package com.optum.rqns.ftm.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import static org.mockito.Mockito.mock;

@Slf4j
public abstract class GetterSetterTester<T> {

    public abstract T getTestInstance();


    @Test
    public void testInstance() {
        testGetterSetters(getTestInstance());
    }

    protected T instance;

    public void testGetterSetters(T instance) {
        this.instance = instance;
        Field[] declaredFields = FieldUtils.getAllFields(instance.getClass());

        for (Field field : declaredFields) {
            try {
                testGetAndSetField(field);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void testGetAndSetField(Field field)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (Modifier.isFinal(field.getModifiers())) {
            return;
        }

        Class<?> type = field.getType();
        String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        String setterName = "set" + name;
        String getterName = "get" + name;
        Object val;

        if (type.isEnum()) {
            val = type.getEnumConstants()[0];
        } else if (type.isArray()) {
            log.debug("creating array of type {}", type.getComponentType());
            val = Array.newInstance(type.getComponentType(), 0);
        } else if (type.isPrimitive()) {
            if (type == long.class) {
                val = 2L;
            } else if (type == boolean.class) {
                getterName = "is" + name;
                val = false;
            } else if (type == int.class) {
                val = 1;
            } else if (type == double.class) {
                val = 0.0;
            } else if (type == float.class) {
                val = 0.0f;
            } else if (type == char.class) {
                val = 'a';
            } else {
                log.debug("Unaccounted for primitive type {}", type.getName());
                val = mock(type);
            }
            // More primitives can be added as necessary
        } else {
            if (type == Integer.class) {
                val = 1;
            } else if (type == Long.class) {
                val = 2L;
            } else if (type == Double.class) {
                val = 3d;
            } else if (type == String.class) {
                val = "string";
            } else if (type == Character.class) {
                val = 'A';
            } else if (type == Float.class) {
                val = 0.0f;
            }
            else if (type == LocalDate.class) {
                val = LocalDate.now();
            }
            else {
                val = mock(type);
            }
        }

        Method setter = null;
        boolean hasSetter = true;
        Class<? extends Object> instanceClass = instance.getClass();
        try {
            setter = instanceClass.getMethod(setterName, type);
            setter.invoke(instance, val);
        } catch (NoSuchMethodException e) {
            log.debug("No setter method {} in class {}", setterName, type.getName());
            hasSetter = false;
        } catch (IllegalArgumentException e) {
            if (setter != null) {
                log.error("Setter {} on class {} failed with value {}", setter.getName(), instanceClass, val);
                throw e;
            }
        }

        try {
            Method getter = instanceClass.getMethod(getterName);
            if (hasSetter) {
                if (!type.isPrimitive()) {
                    assertSame(val, getter.invoke(instance));
                } else {
                    assertEquals(val, getter.invoke(instance));
                }
            }
        } catch (NoSuchMethodException e) {
            log.warn("No such getter method {} on class {}", getterName, instanceClass);
        }
    }

    protected <B> B createBeanWithPropertyValue(Class<B> beanClass, String propertyName, Object value) {
        MethodParameter writeMethod = BeanUtils
                .getWriteMethodParameter(BeanUtils.getPropertyDescriptor(beanClass, propertyName));
        Method method = writeMethod.getMethod();
        if (method != null) {
            try {
                B bean = BeanUtils.instantiate(beanClass);
                method.invoke(bean, value);

                return bean;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(
                        String.format("Cannot set the property %s for class %s", propertyName, beanClass.getName()));
            }
        } else {
            throw new RuntimeException(
                    String.format("%s is not a writeable property for %s", beanClass.getName(), propertyName));
        }
    }

}
*/
