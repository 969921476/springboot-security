package com.springboot.security.service;

import com.springboot.security.domain.MyUser;
import com.springboot.security.domain.Role;
import com.springboot.security.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/16 10:18
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private LoginMapper loginMapper;

    // 加载数据库中的账号和用户名
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser myUser = loginMapper.loadUserByUsername(s);

        if(myUser == null){
            throw new UsernameNotFoundException("用户不存在") ;
        }

        // 根据用户ID，查询用户的角色
        List<Role> roles = loginMapper.findRoleByUserId(myUser.getId());
        // 添加角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            authorities.add(new SimpleGrantedAuthority(roles.get(i).getName()));
        }

        // 构建 Security 的 User 对象
        User user = new User(myUser.getName(), myUser.getPassword(), authorities);
        return user;
    }
}
