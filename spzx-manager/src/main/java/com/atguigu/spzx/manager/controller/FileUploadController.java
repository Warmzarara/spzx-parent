package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: fileUploadController
 * @author: XiaoHB
 * @date: 2024/1/5 18:56
 */
@Controller
@ResponseBody
@RequestMapping("/admin/system")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 1.获取上传的文件
     * 2.调用service上传，返回minion路径
     * @return
     */
    @PostMapping("/fileUpload")
    //<input type="file" name="file"/>
    public Result fileUpload(@RequestParam("file") MultipartFile file){ //名字必须为file
        String url = fileUploadService.uploadFile(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
