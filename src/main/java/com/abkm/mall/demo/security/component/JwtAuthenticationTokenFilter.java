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
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    //tokenHeader: Authorization
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    //tokenHead: 'Bearer '
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 从请求中获得token
        String authToken = request.getHeader(tokenHeader);
        // 处理token串，得到token中的username信息
        if (authToken!=null && authToken.startsWith(this.tokenHead)) {
            String token = authToken.substring(this.tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            LOGGER.info("checking username: {}"+username);
            //判断是否已经认证
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据username信息获取userDetails
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 验证token是否过期
                if (jwtTokenUtil.validateToken(token,userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    // 认证通过
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 放行到下个filter
        doFilter(request,response,filterChain);
    }
}
