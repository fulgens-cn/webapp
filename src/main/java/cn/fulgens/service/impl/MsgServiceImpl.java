package cn.fulgens.service.impl;

import cn.fulgens.entity.User;
import cn.fulgens.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class MsgServiceImpl  implements MsgService {

    private JmsOperations jmsOperations;

    @Autowired
    public MsgServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    @Override
    public void sendUserAddMsg(User user) {
        /*jmsOperations.send("topic", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(user);
            }
        });*/
        // 使用java8 lambda表达式创建消息
        jmsOperations.send("topic", session -> session.createObjectMessage(user));
    }
}
