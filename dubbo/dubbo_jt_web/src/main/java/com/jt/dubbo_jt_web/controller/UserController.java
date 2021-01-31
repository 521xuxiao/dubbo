package com.jt.dubbo_jt_web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo_jt_common.service.UserService;
import com.jt.dubbo_jt_web.returnData.ReturnData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Reference(check = false, timeout = 5000, loadbalance="roundrobin")
    private UserService userService;

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/queryUser")
    public List<Map<String, Object>> queryUser() {
        return userService.queryUser();
    }

    /**
     * 新增用户列表
     * @param params  {name, age}
     * @return
     */
    @PostMapping("/addUser")
    public String addUser(@RequestBody Map<String, Object> params) {
        userService.addUser(params);
        return "新增成功";
    }


    //////////////////////////////////////  sso登录系统   //////////////////////////////////////////////////////////

    /**
     * 登录接口
     * {username, password}
     */
    @PostMapping("login")
    public String login(@RequestBody Map<String, Object> params){
        String key = userService.login(params);
        return "登陆成功,你的登录凭证：" + key;
    }

    /**
     * 登录完成之后验证是否登陆过，登陆之后就可以正常的请求数据了
     * @return
     */
    @PostMapping("/verifyLogin")
    public ReturnData verifyLogin(HttpServletRequest request) {
        String token = request.getHeader("token");   // 获取请求头里面的token
        Map<String, Object> map1 = userService.verifyLogin(token);
        return new ReturnData(map1);
    }
}


















