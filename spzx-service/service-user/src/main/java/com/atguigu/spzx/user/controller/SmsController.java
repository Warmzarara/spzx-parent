package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.controller
 * @className: SmsController
 * @author: XiaoHB
 * @date: 2024/3/5 18:00
 */
@RestController
@RequestMapping("api/user/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;
    
    @GetMapping("/getCode/{phone}")
    public Result getCode(@PathVariable String phone){
        smsService.getCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
