package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.user.mapper
 * @className: UserAddressMapper
 * @author: XiaoHB
 * @date: 2024/3/10 14:49
 */
@Mapper
public interface UserAddressMapper {
    public List<UserAddress> findUserAddressList(Long userId);

    UserAddress getAddressById(Long id);
}
