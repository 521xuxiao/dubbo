package com.jt.dubbo_jt_sso.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    List<Map<String, Object>> queryUser();

    void addUser(Map<String, Object> params);

    Map<String, Object> queryLogin(Map<String, Object> params);
}
