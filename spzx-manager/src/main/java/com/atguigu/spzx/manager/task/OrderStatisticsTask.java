package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.task
 * @className: OrderStatisticsTask
 * @author: XiaoHB
 * @date: 2024/2/5 17:21
 */
//交给spring管理
@Component
/**
 * 测试定时任务
 * 每隔5s执行一次
 */
public class OrderStatisticsTask {
    
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;
    
    /**
     * @Scheuled + cron表达式
     * cron表达式设置执行规则
     */
//    @Scheduled(cron = "0/5 * * * * ? ")
//    public void hello(){
//        System.out.println( new Date().toInstant()+" hello");
//    }

    /**
     * 每天凌晨2点执行
     * 1.获取前一天的日期
     * 2.根据前一天日期，统计交易金额
     * 3.统计之后的数据添加到统计结果表中
     */
    @Scheduled(cron = "0 0 2 * * ? ")
//    @Scheduled(cron = "0/10 * * * * ? ")

    public void orderTotalAmountStatistics(){
        String createDate = DateUtil
                .offsetDay(new Date(), -1)
                .toString("yyyy-MM-dd");
        
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(createDate);
        
        if(orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
 