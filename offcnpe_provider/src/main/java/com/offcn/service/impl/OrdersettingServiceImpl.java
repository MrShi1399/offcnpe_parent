package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.pojo.Caldate;
import com.offcn.pojo.Member;
import com.offcn.pojo.Ordersetting;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.service.IOrdersettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Service
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting> implements IOrdersettingService {

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void saveList(List<Ordersetting> ordersettingList) {
        for (Ordersetting ordersetting : ordersettingList) {
            QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
            //从数据库中查询是否有这个日期
            queryWrapper.eq("orderDate",ordersetting.getOrderdate());
            Ordersetting sysOrdersetting = ordersettingMapper.selectOne(queryWrapper);
            //没有这个日期
            if(sysOrdersetting==null) {
                ordersettingMapper.insert(ordersetting);
            }else {
                //存在这个日期
                //修改新的预约人数
                sysOrdersetting.setNumber(ordersetting.getNumber());
                ordersettingMapper.updateById(sysOrdersetting);
            }
        }
    }

    @Override
    public List<Caldate> listOrdersetting(String date) {
        //将年月组合成年月日
        String beginTime=date+"-01";
        String endTime=date+"-31";
        QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("orderDate",beginTime,endTime);
        List<Ordersetting> ordersettingList=ordersettingMapper.selectList(queryWrapper);
        //把集合转换成前台需要的数据格式
        List<Caldate> caldateList=new ArrayList<>();
        if(ordersettingList!=null && ordersettingList.size()>0){
            for (Ordersetting ordersetting : ordersettingList) {
                int month=ordersetting.getOrderdate().getDayOfMonth();
                Integer number = ordersetting.getNumber();
                Integer reservations = ordersetting.getReservations();
                caldateList.add(new Caldate(month,number,reservations));
            }
        }
        return caldateList;
    }

    @Override
    public void update(String date, Integer number) {
        QueryWrapper<Ordersetting> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderDate",date);
        Ordersetting ordersetting = ordersettingMapper.selectOne(queryWrapper);
        //有预约日期
        if(ordersetting!=null){
            ordersetting.setNumber(number);
            ordersettingMapper.updateById(ordersetting);
        }else { //没有预约日期
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Ordersetting ordersetting1 = new Ordersetting(0, localDate, number, 0);
            ordersettingMapper.insert(ordersetting1);
        }
    }


}
