package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.mapper.MemberMapper;
import com.offcn.mapper.OrdersettingMapper;
import com.offcn.pojo.Member;
import com.offcn.pojo.Order;
import com.offcn.mapper.OrderMapper;
import com.offcn.pojo.Ordersetting;
import com.offcn.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.DateUtils;
import com.offcn.util.MessageConstant;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Resource
    private MemberMapper memberMapper;

    @Override
    public Result submit(Map<String, Object> map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有则无法进行预约
        QueryWrapper<Ordersetting> ordersettingQueryWrapper=new QueryWrapper<>();
        String orderDate = (String) map.get("orderDate");
        ordersettingQueryWrapper.eq("orderDate",orderDate);
        Ordersetting ordersetting = ordersettingMapper.selectOne(ordersettingQueryWrapper);
        //没有进行预约设置
        if(ordersetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //进行了预约设置
        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        if (ordersetting.getNumber()<=ordersetting.getReservations()){
            //已经约满了
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //没有约满
        //3、检查是否为会员
        QueryWrapper<Member> memberQueryWrapper=new QueryWrapper<>();
        memberQueryWrapper.eq("phoneNumber",(String)map.get("telephone"));
        Member member = memberMapper.selectOne(memberQueryWrapper);
        //不是会员，进行自动注册
        if(member==null){
            //创建会员并进行预约
            member=new Member();
            member.setPhonenumber((String)map.get("telephone"));
            member.setSex((String)map.get("sex"));
            member.setIdcard((String)map.get("idCard"));
            member.setName((String)map.get("name"));
            member.setRegtime(LocalDate.now());
            member.setPassword("123456");
            int insert = memberMapper.insert(member);
        }
        //是会员或者已经注册
        //4、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约，则无法完成再次预约
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("member_id",member.getId());
        orderQueryWrapper.eq("orderDate", DateUtils.parseString2Date(orderDate));
        orderQueryWrapper.eq("setmeal_id",map.get("setmealId"));
        Order order = orderMapper.selectOne(orderQueryWrapper);
        //重复预约了
        if(order!=null){
            return new Result(false,MessageConstant.HAS_ORDERED);
        }
        //没有预约过，开始预约
        Order inOrder=new Order();
        inOrder.setMemberId(member.getId());
        inOrder.setOrdertype("微信公众号预约");
        inOrder.setOrderdate(LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        inOrder.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderMapper.insert(inOrder);
        //预约成功，更新当日的预约人数
        ordersetting.setReservations(ordersetting.getReservations()+1);
        ordersettingMapper.updateById(ordersetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS);
    }
}
