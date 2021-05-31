package com.offcn.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.offcn.pojo.Checkgroup;
import com.offcn.service.ICheckgroupService;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private ICheckgroupService iCheckgroupService;

    /**
     * 将检查项添加到检查组
     *
     * @param checkgroup   添加到哪个检查组
     * @param checkitemIds 需要添加到检查组的检查项数组
     * @return 提示信息
     */
    @RequestMapping("save")
    public Result save(@RequestBody Checkgroup checkgroup, Integer[] checkitemIds) {
        try {
            iCheckgroupService.save(checkgroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 设置分页效果
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("listPage")
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = iCheckgroupService.listPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, null);
        }
    }

    /**
     * 获取需要修改的检查组信息
     *
     * @param id 检查组id
     * @return 提示信息和查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        try {
            Checkgroup checkgroup = iCheckgroupService.getById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkgroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 将前台请求需要更改的检查组的信息和检查组下面的检查项进行更改
     * @param checkgroup 检查组
     * @param checkitemIds 检查组下面的检查项id
     * @return 提示信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Checkgroup checkgroup,Integer[] checkitemIds){
        try {
            iCheckgroupService.update(checkgroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * 将检查组进行逻辑删除
     * @param id 检查组id
     * @return 提示信息
     */
    @RequestMapping("remove")
    public Result remove(Integer id){
        try {
            iCheckgroupService.removeById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 加载所有的检查组并将结果返回给前台
     * @return 提示信息和结果
     */
    @RequestMapping("listAll")
    public Result listAll(){
        try {
            List<Checkgroup> checkgroupList = iCheckgroupService.list();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}

