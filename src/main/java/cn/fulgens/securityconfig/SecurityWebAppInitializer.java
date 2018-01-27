package cn.fulgens.securityconfig;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

/**
 * AbstractSecurityWebApplicationInitializer是WebApplicationInitializer的实现类
 * 因此Spring会发现它，并用它在web容器中注册DelegatingFilterProxy
 * DelegatingFilterProxy在spring-web这个jar包下的org.springframework.web.filter包下
 */
public class SecurityWebAppInitializer
        extends AbstractSecurityWebApplicationInitializer {

    // 通过重载beforeSpringSecurityFilterChain方法配置一个MultipartFilter在多部分表单中使用csrf保护
    // 还有一种方式是在多部分表单请求路径上加上CSRF token请求参数，如action="/upload?${_csrf.parameterName}=${_csrf.token}
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
}
