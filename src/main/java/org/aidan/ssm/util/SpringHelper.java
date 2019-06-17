package org.aidan.ssm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Aidan
 * @创建时间：2019/6/16 4:27 PM
 * @描述: TODO
 */
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (APPLICATION_CONTEXT == null) {
            APPLICATION_CONTEXT = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(clazz);
    }
}
