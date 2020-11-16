package com.springboot.security.mapper;

import com.springboot.security.domain.MyUser;
import com.springboot.security.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/16 10:29
 */
public interface LoginMapper {
    @Select("select * from user where name = #{name}")
    MyUser loadUserByUsername(String name);

    @Select("SELECT role.`name` FROM role WHERE role.id in (SELECT role_id FROM " +
            " role_user as r_s JOIN `user` as u  ON r_s.user_id = u.id and u.id = #{id})")
    List<Role> findRoleByUserId(int id);
}
