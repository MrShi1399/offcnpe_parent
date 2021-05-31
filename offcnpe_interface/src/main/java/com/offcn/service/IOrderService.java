package com.offcn.service;

import com.offcn.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 * 体检预约服务
 * @author zs
 * @since 2021-05-25
 */
public interface IOrderService extends IService<Order> {
    /**
     * 进行体检预约操作
     * @param map
     * @return
     * @throws Exception
     */
    Result submit(Map<String,Object> map) throws Exception;
}
