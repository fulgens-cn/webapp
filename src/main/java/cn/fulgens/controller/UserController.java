package cn.fulgens.controller;

import cn.fulgens.entity.User;
import cn.fulgens.exception.Error;
import cn.fulgens.exception.UserNotFoundException;
import cn.fulgens.service.MsgService;
import cn.fulgens.service.UserService;
import cn.fulgens.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MsgService msgService;

    @GetMapping(value = "/register")
    public String toRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * 接收表单提交user数据进行用户注册
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register")
    public String register(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        // 调用service进行用户注册
        userService.register(user);
        // 发送用户添加消息（测试activemq）
        msgService.sendUserAddMsg(user);
        return "redirect:/login";
    }

    /**
     * 接收json格式的user数据保存用户
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public User saveUser(@RequestBody @Valid User user,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // ...
        }
        userService.register(user);
        return user;
    }

    @GetMapping(value = "/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("verifyCode_session", verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 34;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /*@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // You can redirect wherever you want, but generally it's a good practice to show login screen again.
        return "redirect:/";
    }*/

    /*@RequestMapping("/user/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") String id) {
        return userService.getById(id);
    }*/

    @RequestMapping(value = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public User getUserById(@PathVariable("id") User user) {
        if (user == null) {
            throw new UserNotFoundException(user.getUid());
        }
        return user;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public Error userNotFound(UserNotFoundException exception) {
        String uid = exception.getUid();
        return new Error(4, "User" + uid + "not found");
    }

    @RequestMapping(value = "/user/list",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    // jpa与springmvc集成的分页查询，会自动接收page、size、sort参数实现分页与排序
    public Page<User> getUserList(Pageable pageable) {
        return userService.getUserList(pageable);
    }

    @RequestMapping("/user/remove/{id}")
    public void removeUser(@PathVariable("id") String id) {
        userService.remove(id);
    }

}
