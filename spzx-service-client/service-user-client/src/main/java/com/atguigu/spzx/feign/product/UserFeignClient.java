package com.atguigu.spzx.feign.product;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.feign.product
 * @className: UserFeignClient
 * @author: XiaoHB
 * @date: 2024/3/12 17:20
 */
@FeignClient(value = "service-user")
public interface UserFeignClient {
    @GetMapping("/api/user/userAddress/getAddressById/{id}")
    UserAddress getAddressById(@PathVariable Long id) ;
    
}
