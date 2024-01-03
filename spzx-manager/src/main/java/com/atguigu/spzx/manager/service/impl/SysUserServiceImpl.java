package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.common.exception.LoginException;
import com.atguigu.spzx.common.exception.UserNotFoundException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.RedisPrefixEnum;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    

    /**
     * 获取用户输入的验证码和
     * 1.获取用户数据并存到loginDto中
     * 2.根据用户名查询用户表，获取用户信息
     * 3.如果查询不到用户信息，登录失败
     * 4.获取输入的密码，与数据库中密码比较
     * 5.密码一致，登录成功，不一致，登录失败
     * 6.登录成功，生成唯一标识token
     * 7.把登录信息存进redis并设置过期时间
     * 8.返回loginVo对象
     * @param loginDto
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {

        // 校验验证码是否正确
        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();     // redis中验证码的数据key
        String key = RedisPrefixEnum.USER_LOGIN_VALIDATE.getPrefix() + codeKey;
        // 从Redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(key);
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR) ;
        }

        // 验证通过删除redis中的验证码
        redisTemplate.delete(RedisPrefixEnum.USER_LOGIN_VALIDATE.getPrefix()+ codeKey) ;
        
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null){
            throw new UserNotFoundException();
        }
        if (!sysUser.getPassword().equals(DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()))){
            throw new LoginException();
        }
        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set(RedisPrefixEnum.USER_LOGIN.getPrefix()+token,
                JSON.toJSONString(sysUser),
                7,
                TimeUnit.DAYS);
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    /**
     * 1.根据token在redis中取出相应的用户信息
     * 2.利用fastJSON工具类将取出的数据由字符串转为SysUser对象
     * 3.返回对象
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get(RedisPrefixEnum.USER_LOGIN.getPrefix()+token);
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    /**
     * 用户登出方法，根据token清除redis中存储的用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser logout(String token) {
        redisTemplate.delete(RedisPrefixEnum.USER_LOGIN.getPrefix()+token);
        return null;
    }

    /**
     * 用户列表方法，根据关键字从数据库中查询出用户列表，并进行分页展示
     * @param pageNum
     * @param pageSize
     * @param sysUserDto
     * @return
     */
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(list);
        return sysUserPageInfo;
    }

    /**
     * 用户添加方法
     * 1.用户名不可重复
     * 2.对密码进行加密处理
     * @param sysUser
     */
    @Override
    public void saveSysUser(SysUser sysUser) {
        //用户名不可重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectByUserName(userName);
        if(ObjectUtil.isNotNull(dbSysUser)){
            throw new GuiguException(ResultCodeEnum.USER_NAME_EXISTS);
        }
        //密码加密
        String password = sysUser.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(md5_password);
        //设置status 1可用 0不可以
        sysUser.setStatus(1);
        //操作数据库
        sysUserMapper.save(sysUser);
    }

    /**
     * 用户修改方法
     * 用户名不能重复
     * @param sysUser
     */
    @Override
    public void updateSysUser(SysUser sysUser) {
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectByUserName(userName);
        if(ObjectUtil.isNotNull(dbSysUser)){
            throw new GuiguException(ResultCodeEnum.USER_NAME_EXISTS);
        }
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Integer userId) {
        sysUserMapper.delete(userId);
    }
}
