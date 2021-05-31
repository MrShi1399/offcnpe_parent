package com.offcn.controller;


import com.offcn.pojo.Setmeal;
import com.offcn.service.ISetmealService;
import com.offcn.util.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Value("${imgFilePath}")
    private String imgFilePath;

    @Reference
    private ISetmealService iSetmealService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 上传文件并返回文件名称
     * @param multipartFile 文件
     * @return 提示信息和结果
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile){
        try {
            File file = MyFileUtils.upload(multipartFile, imgFilePath);
            String fileName = file.getName();
            //将用户文件上传的文件名保存到redis
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_UPLOAD,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 保存套餐信息
     * @param setmeal 套餐信息
     * @param checkgroupIds 套餐下的检查组数组
     * @return 提示信息
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            iSetmealService.save(setmeal,checkgroupIds);
            //将套餐中的图片文件名保存到redis
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB, setmeal.getImg());
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

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
}

