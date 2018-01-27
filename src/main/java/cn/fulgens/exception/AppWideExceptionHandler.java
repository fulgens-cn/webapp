package cn.fulgens.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;

/**
 * 自定义全局异常处理器（参见spring实战p218）
 * ControllerAdvice注解是spring提供的控制器类的特定切面，其自身使用了@Component注解无需配置
 *
 */
// @ControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public String exceptionHandler(Model model) {
        model.addAttribute("msg", "您的网络出现异常，请重试！");
        return "error";
    }

}
