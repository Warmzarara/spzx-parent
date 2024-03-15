package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.service
 * @className: UserAddressService
 * @author: XiaoHB
 * @date: 2024/3/10 14:48
 */
public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getAddressById(Long id);
}
