package cn.fulgens.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@ComponentScan(basePackages = {"cn.fulgens.controller"})
@EnableWebMvc   // 启用spring mvc
@EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        // 设置解析为Jstl视图
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /********************************Thymeleaf*********************************/
    /*@Bean
    // 配置模板解析器
    // Thymeleaf3使用ITemplateResolver接口，SpringResourceTemplateResolver实现类
    // Thymeleaf3之前使用TemplateResolver接口，ServletContextTemplateResolver实现类
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver =
                new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // 设置templateMode属性为HTML5
        templateResolver.setTemplateMode("HTML5");
        // 设置编码格式为UTF-8
        templateResolver.setCharacterEncoding("UTF-8");
        // templateResolver.setOrder(1);
        // templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public TemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // 注入模板解析器
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    @Primary
    public ViewResolver viewResolver(TemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }*/

    /********************************放行静态资源*********************************/
    @Override
    // 通过继承WebMvcConfigurerAdapter重写configureDefaultServletHandling方法放行静态资源
    // 相当于xml配置中的<mvc:default-servlet-handler/>
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /********************************国际化信息源*********************************/
    /*@Bean
    // 国际化信息源ResourceBundleMessageSource
    // 需配合spring标签<s:message code="..." />使用
    // 会在类路径下查找信息源配置文件
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 设置国际化信息源的基本名称
        messageSource.setBasename("messages");
        return messageSource;
    }*/

    // 国际化信息源ReloadableResourceBundleMessageSource
    // 不同于ResourceBundleMessageSource它能够重新加载信息属性，而不必重新编译或重启应用
    // baseName属性可以设置在类路径下（以"classpath:"作为前缀）、文件系统中（以"file:"作为前缀）
    // 或web应用的根路径下（没有前缀）
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        // 设置国际化信息源的基本名称
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(10);
        return messageSource;
    }

    /********************************文件上传解析器*********************************/
    /*******************MultipartFile接口接收上传文件时才需要配置********************/
    /*@Bean
    // StandardServletMultipartResolver（依赖于servlet3.0对multipart请求的支持，始于spring3.1）
    // 必须设置临时目录
    // 上传文件限制是通过重载customizeRegistration方法实现的
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }*/

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(2097152);
        multipartResolver.setMaxInMemorySize(0);
        return multipartResolver;
    }

}
