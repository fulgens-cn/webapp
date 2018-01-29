package cn.fulgens.test;

import cn.fulgens.config.ActiveMQConfig;
import cn.fulgens.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ActiveMQConfig.class})
public class TestActiveMQ {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testSend() {
        User user = new User();
        user.setUsername("google");
        user.setPassword("password");
        /*jmsTemplate.send("queue",
                session -> session.createObjectMessage(user));*/
        jmsTemplate.convertAndSend("queue", user);
    }

    // 测试同步消息接收
    @Test
    public void testReceive() {
        /*try {
            ObjectMessage objectMessage = (ObjectMessage) jmsTemplate.receive("queue");
            System.out.println(objectMessage.getObject().toString());
        } catch (JMSException e) {
            // JmsTemplate可以将JMSException检查型异常转换为spring提供的JmsException非检查型异常
            // 但JmsTemplate无法处理ObjectMessage.getObject()方法抛出的JMSException检查型异常
            // 这里使用JmsUtils.convertJmsAccessException()方法将JMSException检查型异常转换为非检查型异常
            JmsUtils.convertJmsAccessException(e);
        }*/
        User user = (User) jmsTemplate.receiveAndConvert("queue");
        System.out.println(user);
    }

}
