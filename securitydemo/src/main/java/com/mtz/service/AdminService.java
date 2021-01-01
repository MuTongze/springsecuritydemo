package com.mtz.service;

import com.mtz.pojo.po.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdminByUsername(String username);
    List<String> getPermissionList(Long id);
    String login(String username, String password);
    Admin register(Admin admin);
}
