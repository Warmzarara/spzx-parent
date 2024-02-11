package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController //交给spring管理，返回json数据
@RequestMapping("/admin/system/index")  //路径
public class IndexController {
    @Resource
    SysUserService sysUserService;
    
    @Resource
    ValidateCodeService validateCodeService;
    
    @Resource
    SysMenuService sysMenuService;

    /**
     * 登录
     * @return
     */
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 1.从ajax请求头中获取token
     * 2.根据token在redis中查询用户信息
     * 3.用户信息返回
     * @return
     */
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/getUserInfo")
//    public Result getUserInfo(@RequestHeader(name = "token") String token){
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
//    }
    /**
     * 直接从ThreadLocal中获取用户信息
     */
    public Result getUserInfo(){
        return Result.build(AuthContextUtil.get(),ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 生成验证码图片
     * @return
     */
    @GetMapping("/generateValidateCode")
    @Operation(summary = "生成图片验证码")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
    
    @GetMapping("/menus")
    public Result menus(){
        List<SysMenuVo> list = sysMenuService.findMenusByUserId();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
