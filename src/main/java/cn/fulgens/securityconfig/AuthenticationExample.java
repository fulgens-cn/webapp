package cn.fulgens.securityconfig;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * spring security官方认证示例程序
 *
 * spring security认证过程
 * 1.用户名和密码被获取到，并放入一个 UsernamePasswordAuthenticationToken 实例中( Authentication接口的一个实例)。
 * 2.这个token被传递到一个 AuthenticationManager 实例中进行认证
 * 3.在成功认证后， AuthenticationManager返回一个所有字段都被赋值的 Authentication 对象实例
 * 4.通过调用 SecurityContextHolder.getContext().setAuthentication(…)创建安全上下文，通过返回的验证对象进行传递。
 */
public class AuthenticationExample {

    private static AuthenticationManager am =
            new SampleAuthenticationManager();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println("Please enter your username:");
            String name = in.readLine();
            System.out.println("Please enter your password:");
            String password = in.readLine();
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(name, password);
                Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
                break;
            } catch(AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 自定义认证管理器实现
     */
    static class SampleAuthenticationManager implements AuthenticationManager {

        static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

        static {
            AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_user"));
        }

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            if (authentication.getName().equals(authentication.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials(), AUTHORITIES);
            }
            throw new BadCredentialsException("Bad Credentials");
        }
    }

}
