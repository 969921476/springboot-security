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

    /**
     * 数据库
     *
     * CREATE TABLE IF NOT EXISTS USER (
     *   id       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     *   name     VARCHAR(10),
     *   password VARCHAR(50)
     * )
     *   CHARSET utf8;
     *
     * CREATE TABLE IF NOT EXISTS ROLE (
     *   id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     *   name VARCHAR(10)
     * )
     *   CHARSET utf8;
     *
     * CREATE TABLE IF NOT EXISTS ROLE_USER (
     *   user_id INT,
     *   role_id INT
     * )
     *   CHARSET utf8;
     *
     * # 防止项目启动，出现重复插入而报错
     * INSERT INTO `ROLE` (`id`, `name`) SELECT
     *                                     '1',
     *                                     'ROLE_ADMIN'
     *                                   FROM dual
     *                                   WHERE NOT exists(SELECT id
     *                                                    FROM `ROLE`
     *                                                    WHERE id = '1');
     * INSERT INTO `ROLE` (`id`, `name`) SELECT
     *                                     '2',
     *                                     'ROLE_USER'
     *                                   FROM dual
     *                                   WHERE NOT exists(SELECT id
     *                                                    FROM `ROLE`
     *                                                    WHERE id = '2');
     *
     * INSERT INTO `USER` (`id`, `password`, `name`) SELECT
     *                                                 '1',
     *                                                 'root',
     *                                                 'root'
     *                                               FROM dual
     *                                               WHERE NOT exists(SELECT id
     *                                                                FROM `USER`
     *                                                                WHERE id = '1');
     * INSERT INTO `USER` (`id`, `password`, `name`) SELECT
     *                                                 '2',
     *                                                 'yiaz',
     *                                                 'yiaz'
     *                                               FROM dual
     *                                               WHERE NOT exists(SELECT id
     *                                                                FROM `USER`
     *                                                                WHERE id = '2');
     *
     *
     * INSERT INTO `ROLE_USER` (`user_id`, `role_id`) SELECT
     *                                                  '1',
     *                                                  '1'
     *                                                FROM dual
     *                                                WHERE NOT exists(SELECT user_id
     *                                                                 FROM `ROLE_USER`
     *                                                                 WHERE user_id = '1');
     * INSERT INTO `ROLE_USER` (`user_id`, `role_id`) SELECT
     *                                                  '2',
     *                                                  '2'
     *                                                FROM dual
     *                                                WHERE NOT exists(SELECT user_id
     *                                                                 FROM `ROLE_USER`
     *                                                                 WHERE user_id = '2');
     */
}
