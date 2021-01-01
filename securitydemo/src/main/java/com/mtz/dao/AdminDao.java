package com.mtz.dao;

import com.mtz.pojo.po.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {
    /**
     * 通过用户名查询相关信息
     * @param username 用户名
     * @return 该用户相关信息
     */
    Admin findByUsername(String username);
}
