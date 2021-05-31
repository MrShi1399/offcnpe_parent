package com.offcn.controller;


import com.offcn.service.IOrderService;
import com.offcn.util.MessageConstant;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IOrderService orderService;

    /**
     * 进行预约操作
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result order(@RequestBody Map<String,Object> map){
        try {
            return orderService.submit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }
}

