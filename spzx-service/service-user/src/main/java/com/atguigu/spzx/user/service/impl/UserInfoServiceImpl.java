package com.atguigu.spzx.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.service.impl
 * @className: UserInfoServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/7 0:26
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    /**
     * 1.从Dto中获取数据
     * 2.比对Dto的验证码是否与redis中的一致
     * 3.校验用户名，不能重复
     * 4.封装数据，添加到数据库
     * 5.删除redis中的验证码
     * @param userRegisterDto
     */
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String nickName = userRegisterDto.getNickName();
        String password = DigestUtils.md5DigestAsHex(userRegisterDto.getPassword().getBytes());
        /*
        验证码如果不一致则抛出异常
         */
        if (!StrUtil.equals(
                userRegisterDto.getCode(),
                redisTemplate.opsForValue().get("phone:code:" + username))) {
            System.out.println(userRegisterDto.getCode()+" "+redisTemplate.opsForValue().get("phone:code:" + username));
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        /*
         用户名已存在则抛出异常，不存在封装数据，操作数据库进行插入
         */
        if (!ObjectUtil.isNull(userInfoMapper.getByUsername(username))) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_EXISTS);
        }else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setNickName(nickName);
            userInfo.setPassword(password);
            userInfo.setPhone(username);
            userInfo.setStatus(1);
            userInfo.setSex(0);
            userInfo.setAvatar("http://127.0.0.1:9000/spzx-bucket/20240105/a738a92254304300924dd37a22ca8837111.png");
            userInfoMapper.save(userInfo);
            /*
             更新redis 清空
             */
//            redisTemplate.delete("phone:code:" + username) ;
        }
    }

    /**
     * 会员登录
     * 1.获取dto中的用户名和密码
     * 2.根据用户名查询数据库得到用户信息，比对密码是否一致
     * 3.校验用户是否已被禁用
     * 4.生成token，放到放到redis中
     * 5.返回token
     * @param userLoginDto
     * @return
     */
    @Override
    public String login(UserLoginDto userLoginDto) {
        /*
        获取dto中的数据（用户名，密码）
         */
        String username = userLoginDto.getUsername();
        String password = DigestUtils.md5DigestAsHex(userLoginDto.getPassword().getBytes());
        
        /*
        数据库中的用户信息
         */
        UserInfo userInfo = userInfoMapper.getByUsername(username);

        if (ObjectUtil.isNull(userInfo)) {
            throw new GuiguException(ResultCodeEnum.USER_NOT_EXISTED_ERROR);
        }
        
        /*
        判断dto用户信息（用户名和密码）是否与数据库中的一致
         */
        if (!StrUtil.equals(
                password,  //dto中的密码
                userInfo.getPassword()  //数据库中的密码
        )) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        
        /*
        判断用户是否可用
         */
        if (userInfo.getStatus()==0){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        
        /*
        生成token并放到redis中
         */
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        String userInfoJSON = JSONObject.toJSONString(userInfo);
        redisTemplate.opsForValue().set("user:spzx:"+token,userInfoJSON,30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
//        String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
//        UserInfo userInfo = JSONObject.parseObject(userInfoJSON, UserInfo.class);

        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
