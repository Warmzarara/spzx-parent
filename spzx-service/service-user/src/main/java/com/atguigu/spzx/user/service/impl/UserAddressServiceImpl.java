package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.service.impl
 * @className: UserAddressServiceImpl
 * @author: XiaoHB
 * @date: 2024/3/10 14:49
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    
    @Autowired
    private UserAddressMapper userAddressMapper;
    
    @Override
    public List<UserAddress> findUserAddressList() {
        return userAddressMapper.findUserAddressList(this.getUserInfo().getId());
    }

    @Override
    public UserAddress getAddressById(Long id) {
        return userAddressMapper.getAddressById(id);
    }

    private UserInfo getUserInfo(){
        return AuthContextUtil.getUserInfo();
    }
}
