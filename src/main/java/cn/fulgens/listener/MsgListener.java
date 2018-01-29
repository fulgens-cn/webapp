package cn.fulgens.listener;

import cn.fulgens.entity.User;

/**
 * 消息监听器（纯pojo）
 * 得益于spring提供的jms命名空间
 */
public class MsgListener {

    // 监听用户添加消息并处理
    public void handlerUserAdd(User user) {
        // ...处理逻辑
    }

}
