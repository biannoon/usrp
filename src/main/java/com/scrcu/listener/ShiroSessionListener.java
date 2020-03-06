package com.scrcu.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述： shiro监听配置
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/21 21:39
 */
public class ShiroSessionListener implements SessionListener {

    //统计在线人数 juc包下线程安全自增
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    private static HashMap map = new HashMap();

    /**
     * 描述： 会话创建时触发
     * @param session
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/21 21:39
     */
    @Override
    public void onStart(Session session) {
        System.out.println("用户登录创建会话session"+session.getId());
        if (null != session) {
            map.put(session.getId(), session);
        }
        //会话创建，增加在线人数（+1）
        sessionCount.incrementAndGet();
    }

    /**
     * 描述： 会话退出时触发
     * @param session
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/21 21:39
     */
    @Override
    public void onStop(Session session) {
        System.out.println("用户退出删除session"+session.getId());
        if (null != session) {
            map.remove(session.getId());
        }
        //会话退出，减少在线人数（-1）
        sessionCount.decrementAndGet();
    }

    /**
     * 描述： 会话过期时触发
     * @param session
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/21 21:39
     */
    @Override
    public void onExpiration(Session session) {
        System.out.println("用户会话过期session注销"+session.getId());
        if (null != session) {
            map.remove(session.getId());
        }
        //会话过期，减少在线人数（-1）
        sessionCount.decrementAndGet();
    }

    /**
     * 描述： 获取在线人数
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/21 21:39
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
