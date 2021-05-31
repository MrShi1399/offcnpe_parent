package com.offcn.controller;


import com.offcn.pojo.Checkitem;
import com.offcn.service.ICheckitemService;
import com.offcn.util.MessageConstant;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * <p>
 *  该前端控制器主要处理预约管理界面的请求<br/>
 *  预约界面的路径为/pages/checkitem.html
 * </p>
 * @author zs
 * @since 2021-05-25
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {
    @Reference
    private ICheckitemService iCheckitemService;

    /**
     * 将新建的预约项的信息进行保存
     * @param checkitem
     * @return
     */
    @RequestMapping("save")
    public Result save(@RequestBody Checkitem checkitem){
        try {
            boolean save = iCheckitemService.save(checkitem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 获取分页请求，并将该页下的预约项和当前分页信息返回给前端
     * @param queryPageBean 接收前端的分页信息
     * @return 返回分页后的结果
     */
    @RequestMapping("listPage")
    public PageResult listPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return iCheckitemService.listPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }

    /**
     * 将选中的预约项进行逻辑删除
     * @param id 该预约项的id
     * @return 返回成功或者失败信息
     */
    @RequestMapping("remove")
    public Result remove(Integer id){
        try {
            iCheckitemService.removeById(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询需要修改的预约项
     * @param id 该预约项的id
     * @return 返回结果和相关提示信息，成功将查询结果返回
     */
    @RequestMapping("getById")
    public Result getById(Integer id){
        try {
            Checkitem checkitem = iCheckitemService.getById(id);
            if(checkitem==null){
                return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
            }
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 执行修改操作
     * @param checkitem 需要修改的预约项
     * @return
     */
    @RequestMapping("update")
    public Result update(@RequestBody Checkitem checkitem){
        try {
            boolean save = iCheckitemService.updateById(checkitem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有的检查项
     * @return
     */
    @RequestMapping("listAll")
    public Result listAll(){
        try {
            List<Checkitem> checkitemList = iCheckitemService.list();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}

