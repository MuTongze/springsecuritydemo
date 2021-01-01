package com.mtz.controller;

import com.mtz.common.CommonResult;
import com.mtz.dao.AdminDao;
import com.mtz.pojo.po.Admin;
import com.mtz.service.AdminService;
import com.mtz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody Map<String, Object> param) {
        String username = (String) param.get("username");
        String password = (String) param.get("password");
        String token = adminService.login(username, password);
        if(!StringUtils.hasLength(token)) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return CommonResult.success(map);
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody Admin register) {
        Admin register1 = adminService.register(register);
        if(ObjectUtils.isEmpty(register1)) {
            return CommonResult.failed("存在同名");
        }
        return CommonResult.success(register);
    }

    @GetMapping("test")
    public CommonResult test() {
        return CommonResult.success("访问成功");
    }
}
