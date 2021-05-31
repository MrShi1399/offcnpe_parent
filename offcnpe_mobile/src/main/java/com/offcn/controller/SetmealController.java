package com.offcn.controller;


import com.offcn.pojo.Setmeal;
import com.offcn.service.ISetmealService;
import com.offcn.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private ISetmealService iSetmealService;

    /**
     * 查询该页下的套餐信息
     * @param queryPageBean 分页信息
     * @return 分页查询结果
     */
    @RequestMapping("/listPage")
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iSetmealService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    /**
     * 获取该套餐的所有信息
     * @param id
     * @return
     */
    @PostMapping("/getInfo/{id}")
    public Result getInfo(@PathVariable("id") Integer id){
        try {
            Setmeal setmeal = iSetmealService.getInfo(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}

