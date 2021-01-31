package com.jt.dubbo_jt_common.service;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<Map<String, Object>> queryUser();

    void addUser(Map<String, Object> params);

    String login(Map<String, Object> params);

    Map<String, Object> verifyLogin(String token);
}
