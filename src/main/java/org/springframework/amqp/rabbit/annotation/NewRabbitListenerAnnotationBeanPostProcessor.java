package org.springframework.amqp.rabbit.annotation;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class NewRabbitListenerAnnotationBeanPostProcessor
        extends RabbitListenerAnnotationBeanPostProcessor
        implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(NewRabbitListenerAnnotationBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    @Override
    protected void processAmqpListener(RabbitListener rabbitListener, Method method, Object bean, String beanName) {
        super.processAmqpListener(rabbitListener, method, bean, beanName);
        doSomething(rabbitListener);
    }

    private void doSomething(RabbitListener rabbitListener) {
        log.info("Called from NewRabbitListenerAnnotationBeanPostProcessor");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
