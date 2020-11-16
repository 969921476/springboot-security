package com.springboot.security.config;

import com.springboot.security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/16 10:38
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 获取封装用户信息的对象
        UserDetails userDetails = userService.loadUserByUsername(username);
        // 进行密码的比对
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String root = bCryptPasswordEncoder.encode("root");
        // 密码比较必需加密 方法比较有问题？TODO
        boolean flag = bCryptPasswordEncoder.matches(root, userDetails.getPassword());
        // 校验通过
        if (flag){
            // 将权限信息也封装进去
            return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        }

        // 验证失败返回 null
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
