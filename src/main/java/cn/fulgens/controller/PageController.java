package cn.fulgens.controller;

import cn.fulgens.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
public class PageController {

    /**
     * 首页展示
     * @param data
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "/index"})
    public String homePage(@RequestParam(required = false) String data,
                        Model model) {
        try {
            if (data != null) {
                // 解决跨重定向后get请求中文参数乱码
                byte[] bytes = data.getBytes("ISO-8859-1");
                data = new String(bytes, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("data", data);
        return "index";
    }

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String toPage(@PathVariable(value = "page") String page) {
        return page;
    }

}
