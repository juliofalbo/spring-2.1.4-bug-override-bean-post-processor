package org.springframework.boot.autoconfigure.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.NewRabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;

import com.rabbitmq.client.Channel;

@Configuration
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@EnableConfigurationProperties({ RabbitProperties.class })
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import(RabbitAnnotationDrivenConfiguration.class)
public class NewRabbitAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(NewRabbitAutoConfiguration.class);

    @Primary
    @Bean(name = RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_APPLICATION)
    @DependsOn("containerFactory2")
    public RabbitListenerAnnotationBeanPostProcessor rabbitListenerAnnotationProcessor() {
        return new NewRabbitListenerAnnotationBeanPostProcessor();
    }

    @Bean("containerFactory2")
    public SimpleRabbitListenerContainerFactory customCF(CachingConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        return simpleRabbitListenerContainerFactory;
    }

}
