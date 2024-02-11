package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: OrderInfoServiceImpl
 * @author: XiaoHB
 * @date: 2024/2/5 19:14
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired 
    private OrderStatisticsMapper orderStatisticsMapper;

    /**\
     * 1.根据dto中开始日期到结束日期查询统计结果数据，返回List集合
     * 2.遍历list，得到所有的日期，把所有的日期封装进新的集合中
     * 3.遍历list，得到所有日期对应的金额总额，将总金额封装进集合中
     * 4.将两个list封装进OrderStatisticsVo中并返回
     * @param orderStatisticsDto
     * @return
     */
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        /*
         1.
         */
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        /*
         2.使用输出流，遍历集合，得到集合中的每个日期，封装进一个新的集合中
         */
        // TODO: 2024/2/6 此处用到了流来遍历集合，Lamda表达式，回头看一下 
        List<String> dateList = orderStatisticsList.stream()
                .map(a -> DateUtil.format(a.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());

        /*
         3.得到金额
         */
        List<BigDecimal> decimalList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                .collect(Collectors.toList());
        
    
        decimalList.forEach(a-> System.out.println(a));
        decimalList.forEach(System.out::println);
        
        
        /*
        4.将得到的日期集合和金额集合封装进Vo对象
         */
        // TODO: 2024/2/6 这里考虑优化成链式调用 
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(decimalList);
        return orderStatisticsVo;
    }
}
