package com.jt.dubbo_jt_sso.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.jt.dubbo_jt_common.service.UserService;
import com.jt.dubbo_jt_sso.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Override
    public List<Map<String, Object>> queryUser() {
        return userDao.queryUser();
    }

    @Override
    public void addUser(Map<String, Object> params) {
        userDao.addUser(params);
    }

    //////////////////////////////////   sso  ////////////////////////////////////////////////

    /**
     * 登录接口
     * @param params  {username, password}
     */
    @Override
    public String login(Map<String, Object> params) {
        Map<String, Object> userInfo = userDao.queryLogin(params);
        if(userInfo == null) {
            throw new RuntimeException("用户名或者密码不存在");
        }
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        String value = JSON.toJSONString(userInfo);
        System.err.println(jedisCluster.get("xxxxx"));
        jedisCluster.setex(key, 7*24*60*60, value);
        return key;
    }

    /**
     * 登录之后的验证， 从请求头拿到token (测试，这个只是临时的测试一下，正确的应该在handlerInterceptor拦截器里面做这个事)
     * @return
     */
    @Override
    public Map<String, Object> verifyLogin(String token) {
        String value = jedisCluster.get(token);
        System.err.println("58: "+value);
        if(value == null || "".equals(value)) {
            throw new RuntimeException("您还没有登录");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("ok", "您可以正常的访问本网站了");
        return map1;
    }
}
