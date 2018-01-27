package cn.fulgens.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义全局异常解析器（需实现HandlerExceptionResolver接口）
 * 需要在spring mvc中配置这个bean
 */
public class CustomizedExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomizedExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object handler, Exception exception) {
        // 将异常信息打印到控制台
        exception.printStackTrace();
        // 输出到日志（日志级别从高到低：OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、ALL）
        // 程序会打印高于或等于所设置级别的日志，设置的日志等级越高，打印出来的日志就越少
        logger.error(exception.getMessage());
        // 发送邮件、短信等方式通知系统管理员

        // 用户友好的方式展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "您的网络出现异常，请重试！");
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
