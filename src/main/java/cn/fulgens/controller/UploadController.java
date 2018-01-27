package cn.fulgens.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    /**
     * 以MultipartFile接口（spring提供）接收上传文件
     * @param uploadFile
     * @param request
     * @return
     */
    @PostMapping(value = "/upload")
    public String upload(MultipartFile uploadFile,
                         HttpServletRequest request,
                         Model model) {
        // 获取原文件名
        String originalFilename = uploadFile.getOriginalFilename();
        // 获取文件扩展名
        // String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String extName = FilenameUtils.getExtension(originalFilename);
        // 保存文件
        try {
            String path = request.getServletContext().getRealPath("/WEB-INF/tmp");
            uploadFile.transferTo(new File(path + "/" + originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 重定向到主页（跨重定向传递数据参见spring实战p220）
        model.addAttribute("data", "文件上传成功");
        return "redirect:/";
    }

    /**
     * 以javax.servlet.http.Part接口接收上传文件
     * 需和@RequestPart注解配合使用
     * 使用这个接口不需要配置MultipartResolver
     * @param uploadFile
     * @return
     */
    /*@PostMapping(value = "/upload")
    public String upload(@RequestPart("uploadFile") Part uploadFile,
                         HttpServletRequest request,
                         Model model) {
        int i = 1/0;
        // 获取原文件名
        String submittedFileName = uploadFile.getSubmittedFileName();
        // 获取文件扩展名
        // String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String extName = FilenameUtils.getExtension(submittedFileName);
        // 保存文件
        try {
            String path = request.getServletContext().getRealPath("/WEB-INF/tmp");
            uploadFile.write(path + "/" + submittedFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 重定向到主页（跨重定向传递数据参见spring实战p220）
        model.addAttribute("data", "File Upload Success");
        return "redirect:/";
    }*/
}
