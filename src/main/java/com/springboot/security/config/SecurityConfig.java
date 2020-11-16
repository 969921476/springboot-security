package com.springboot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/13 10:31
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置请求访问规则 permitAll 所有人都能访问
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/html1/**").hasRole("ADMIN")
//                .antMatchers("/html2/**").hasRole("USER")
//                .and().formLogin()
//                .loginProcessingUrl("/login").and().logout();
        http
                .authorizeRequests()
                // 放行登录
                .antMatchers("/").permitAll()
                .antMatchers("/html1/**").hasRole("ADMIN")
                .antMatchers("/html2/**").hasRole("USER")
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 开启表单认证
                .formLogin()
                // 地址写的是 映射的路径
//                .loginPage("/login")
                // 必须添加
                .loginProcessingUrl("/login")
                .permitAll()
                // 第二个参数，如果不写成true，则默认登录成功以后，访问之前被拦截的页面，而非去我们规定的页面
                .defaultSuccessUrl("/index.html", true)
                .and()

                .logout()
                .logoutUrl("/logout")
                .and()
                .csrf()
                .disable()
                .httpBasic();
    }

    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存用户
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("cjn").password(new BCryptPasswordEncoder().encode("123")).roles("vip1", "vip2")
//                .and().withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("vip1");
        auth.authenticationProvider(myAuthenticationProvider);
    }
}
