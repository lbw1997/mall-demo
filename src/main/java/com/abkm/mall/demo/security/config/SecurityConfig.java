package com.abkm.mall.demo.security.config;

import com.abkm.mall.demo.security.compoent.JwtAuthenticationTokenFilter;
import com.abkm.mall.demo.security.compoent.RestAuthenticationEntryPoint;
import com.abkm.mall.demo.security.compoent.RestfulAccessDeniedHandler;
import com.abkm.mall.demo.security.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * description: SecurityConfig <br>
 * date: 2020/9/13 19:57 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
                = http.authorizeRequests();
        //不需要保护的资源路径允许访问
        for (String url:ignoreUrlConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        //允许跨域的OPTIONS请求
        registry.antMatchers(HttpMethod.POST)
                .permitAll();
        //任何请求都需要认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                //关闭跨域请求来不及使用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //自定义权限拒绝类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                //自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 注入认证请求点，在异常情况下表示未登陆或登陆失败
     */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }
}
