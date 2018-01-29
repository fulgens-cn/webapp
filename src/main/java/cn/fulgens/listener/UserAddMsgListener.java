package cn.fulgens.listener;

import cn.fulgens.entity.User;
import org.springframework.jms.support.JmsUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * 添加用户的监听器
 */
public class UserAddMsgListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // ...
        try {
            if (message instanceof ObjectMessage) {
                User user = (User) ((ObjectMessage) message).getObject();
                System.out.println(user);
            }
        }catch (JMSException ex) {
            JmsUtils.convertJmsAccessException(ex);
        }
    }
}
