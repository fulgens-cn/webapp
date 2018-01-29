package cn.fulgens.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Spring Security需配置在实现了WebSecurityConfigurer接口的bean中
 * 简单起见继承WebSecurityConfigurerAdapter即可
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private static final String DEF_USERS_BY_USERNAME_QUERY =
            "select username, password, enabled from users " +
                    "where username = ? and enabled = 1";

    private static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
            "SELECT u.username, a.authority " +
                    "FROM users u, authorities a, users_authorities ua " +
                    "WHERE u.uid = ua.user_id AND a.auth_id = ua.auth_id and u.username = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 基于内存的用户存储认证（参见spring实战p257）
        /*auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("ADMIN", "USER");*/
        // 基于数据库的jdbc用户认证（参见spring实战p259）
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY)
                .passwordEncoder(new Md5PasswordEncoder());
        // 基于LDAP认证（参见spring实战p261，需加入spring-security-ldap模块）

        // 自定义认证逻辑
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")        // 自定义登录页面
                    //.loginProcessingUrl("/login")   // 登录表单POST请求提交地址，需要与登录表单action属性值对应，默认为/login
                    .usernameParameter("username")  // 登录表单用户名提交参数名，默认username
                    .passwordParameter("password")  // 登录表单密码提交参数名，默认password
                .and()
                .rememberMe()   // 启用rememberMe功能，spring security默认生成的cookie名为remember-me
                    .tokenValiditySeconds(604800)
                    .rememberMeCookieName("remember-me")
                .and()
                .logout()                           // 提供注销支持，当使用 WebSecurityConfigurerAdapter时这将会被自动应用
                    .logoutUrl("/logout")           // 触发注销操作的url，默认是/logout。如果开启了CSRF保护(默认开启),那么请求必须是POST方式。
                                                    // Adding CSRF will update the LogoutFilter to only use HTTP POST
                    .logoutSuccessUrl("/")          // 注销操作发生后重定向到的url，默认为 /login?logout
                    .invalidateHttpSession(true)    // 指定在注销的时候是否销毁 HttpSession 。默认为True
                    .deleteCookies("remember-me", "JSESSIONID")
                .and()
                    .authorizeRequests()
                    .antMatchers("/upload").hasAuthority("ROLE_ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/accessDenied");
                // .and()
                // .csrf().disable();  // 不启用csrf跨站请求伪造拦截功能，默认是启用的
    }
}
