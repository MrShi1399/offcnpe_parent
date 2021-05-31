package com.offcn.controller;


import com.offcn.service.ICheckgroupCheckitemService;
import com.offcn.util.MessageConstant;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/checkgroup-checkitem")
public class CheckgroupCheckitemController {

    @Reference
    private ICheckgroupCheckitemService iCheckgroupCheckitemService;

    /**
     * 将前台请求的检查组下的检查项返回给前台
     * @param checkgroupId 检查组id
     * @return 提示信息和结果
     */
    @GetMapping("/{checkgroupId}")
    public Result getListByCheckgroupId(@PathVariable("checkgroupId") Integer checkgroupId){
        try {
            Result result = iCheckgroupCheckitemService.listByChcekgroupId(checkgroupId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUPCHECKITEM_FAIL);
        }
    }
}

