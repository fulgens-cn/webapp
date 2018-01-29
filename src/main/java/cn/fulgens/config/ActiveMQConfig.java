package cn.fulgens.config;

import cn.fulgens.listener.MsgListener;
import cn.fulgens.listener.UserAddMsgListener;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ActiveMQConfig {

    // 配置ActiveMQ连接工厂org.apache.activemq.spring.ActiveMQConnectionFactory
    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        // 配置受信奈的序列化实体包（activeMQ5.12.2后使用ObjectMessage时需配置）
        List<String> trustedPackages = new ArrayList<>();
        trustedPackages.add("cn.fulgens.entity");
        trustedPackages.add("java.util");
        // connectionFactory.setTrustAllPackages(true);
        connectionFactory.setTrustedPackages(trustedPackages);
        return connectionFactory;
    }

    // 声明消息目的地
    // 队列（点对点）
    @Bean
    public ActiveMQQueue queue() {
        ActiveMQQueue queue = new ActiveMQQueue("queue");
        return queue;
    }

    // 主题（发布订阅）
    @Bean
    public ActiveMQTopic topic() {
        ActiveMQTopic topic = new ActiveMQTopic("topic");
        return topic;
    }

    // 配置spring提供的jmsTemplate
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,
                                   MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        // 注入连接工厂
        jmsTemplate.setConnectionFactory(connectionFactory);
        // 设置默认消息目的地    setDefaultDestination()或setDefaultDestinationName
        jmsTemplate.setDefaultDestinationName("topic");
        // 设置消息转换器
        // jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    // 配置消息转换器，spring默认使用SimpleMessageConverter，一般无需更改
    // MessageConverter接口及其实现类位于org.springframework.jms.support.converter包下
    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    /*****************************ObjectMessage额外配置****************************/
    // 5.12.2和5.13.0开始，ActiveMQ强制用户明确列出可以使用ObjectMessages交换的包。
    // 官方给出的解释是：ObjectMessage objects depend on Java serialization of marshal/unmarshal object payload.
    // This process is generally considered unsafe as malicious payload can exploit the host system.
    // 可参见http://activemq.apache.org/objectmessage.html
    // 将org.apache.activemq.SERIALIZABLE_PACKAGES系统属性添加到$ {ACTIVEMQ_HOME}/bin/env脚本中的ACTIVEMQ_OPTS变量中
    // 如：-Dorg.apache.activemq.SERIALIZABLE_PACKAGES=com.mycompany.myapp
    // 或-Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*

    // 客户端配置
    // 1.在连接工厂中设置受信奈的序列化实体包
    // 2.配置JmsConfiguration同时注入连接工厂
    public JmsConfiguration jmsConfiguration(ConnectionFactory connectionFactory) {
        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        jmsConfiguration.setConnectionFactory(connectionFactory);
        return jmsConfiguration;
    }
    // 3.配置ActiveMQComponent同时注入JmsConfiguration
    public ActiveMQComponent activeMQComponent(JmsConfiguration jmsConfiguration) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConfiguration(jmsConfiguration);
        return activeMQComponent;
    }
    // 注：如果借助spring提供的消息转换器将对象转换为json则不用这么配置
    // 因为没有对象序列化与反序列化的过程，自然也不存在activemq官方认为的不安全因素存在
    // 但发送与接收消息时都需要转换，涉及到JmsTemplate的两个方法convertAndSend及receiveAndConvert
    // 注意：receiveAndConvert和receive方法均是同步的
    /*****************************ObjectMessage额外配置****************************/

    // 配置消息监听器
    @Bean
    public MessageListener messageListener() {
        return new UserAddMsgListener();
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory cf,
                                                             MessageListener ml,
                                                             MessageConverter mc) {
        DefaultMessageListenerContainer messageListenerContainer =
                new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(cf);
        messageListenerContainer.setDestinationName("topic");
        messageListenerContainer.setMessageListener(ml);
        return messageListenerContainer;
    }
}