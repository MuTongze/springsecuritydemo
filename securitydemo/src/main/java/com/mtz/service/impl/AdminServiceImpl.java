package com.mtz.service.impl;

import com.mtz.dao.AdminDao;
import com.mtz.pojo.po.Admin;
import com.mtz.service.AdminService;
import com.mtz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin getAdminByUsername(String username) {
        Admin byUsername = adminDao.findByUsername(username);
        return byUsername;
    }

    @Override
    public List<String> getPermissionList(Long id) {
        Optional<Admin> admin = adminDao.findById(id);
        List<String> roles = new ArrayList<>(Arrays.asList(admin.map(Admin::getRole).get()));
        return roles;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("用户名或密码错误");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Admin admin = adminDao.findByUsername(username);
            token = JwtUtil.sign(username, String.valueOf(admin.getId()), admin.getRole());
        } catch(AuthenticationException e) {
            log.info("登录异常：{}", e.getMessage());
        }
        return token;
    }

    @Override
    public Admin register(Admin admin) {
        Admin registerAdmin = new Admin();
        BeanUtils.copyProperties(admin, registerAdmin);
        registerAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        // 查询用户名是否存在同名
        Admin byUsername = adminDao.findByUsername(registerAdmin.getUsername());
        if(!ObjectUtils.isEmpty(byUsername)){
            // 存在同名
            log.info("存在同名用户为：{}", byUsername);
            return null;
        }
        adminDao.save(registerAdmin);
        return registerAdmin;
    }


}
