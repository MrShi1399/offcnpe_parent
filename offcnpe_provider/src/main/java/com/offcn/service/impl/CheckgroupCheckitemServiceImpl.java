package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.offcn.pojo.CheckgroupCheckitem;
import com.offcn.mapper.CheckgroupCheckitemMapper;
import com.offcn.service.ICheckgroupCheckitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.MessageConstant;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
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
public class CheckgroupCheckitemServiceImpl extends ServiceImpl<CheckgroupCheckitemMapper, CheckgroupCheckitem> implements ICheckgroupCheckitemService {

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public Result listByChcekgroupId(Integer checkgroupId) {
        QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("checkgroup_id",checkgroupId);
        List<CheckgroupCheckitem> checkgroupCheckitemList = checkgroupCheckitemMapper.selectList(queryWrapper);
        //把集合变成前台可以支持的数据格式
        List<Integer> checkitemIds=new ArrayList<>();
        for (CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitemList) {
            checkitemIds.add(checkgroupCheckitem.getCheckitemId());
        }
        return new Result(true, MessageConstant.QUERY_CHECKGROUPCHECKITEM_SUCCESS,checkitemIds);
    }

}
