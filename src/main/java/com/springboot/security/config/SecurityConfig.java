package com.springboot.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/13 10:31
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置请求访问规则 permitAll 所有人都能访问
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/html1/**").hasRole("vip1")
                .antMatchers("/html2/**").hasRole("vip2")
                .and().formLogin().and().logout();
    }

    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存用户
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("cjn").password(new BCryptPasswordEncoder().encode("123")).roles("vip1", "vip2")
                .and().withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("vip1");
    }
}
