package com.abkm.mall.demo.security.component;

import com.abkm.mall.demo.security.util.JwtTokenUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: JWT认证拦截 <br>
 * date: 2020/9/24 14:43 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Value("jwt.tokenHead")
    private String tokenHead;
    @Value("jwt.tokenHeader")
    private String tokenHeader;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHead = request.getHeader(this.tokenHeader);
        if (authHead!=null && authHead.startsWith(this.tokenHead)) {
            //截取Bearer:之后的字符串
            String authToken = authHead.substring(this.tokenHead.length());
            String username =  jwtTokenUtil.getUsernameFromToken(authToken);
            LOGGER.info("checking username:{}"+ username);

            //判断该用户是否未认证
            if (Strings.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(username,userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}"+username);
                    //完成认证
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        //请求放行到下个filter
        filterChain.doFilter(request,response);
    }
}
