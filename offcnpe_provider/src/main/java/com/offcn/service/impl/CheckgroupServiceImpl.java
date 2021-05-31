package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.CheckgroupCheckitemMapper;
import com.offcn.mapper.CheckitemMapper;
import com.offcn.pojo.Checkgroup;
import com.offcn.mapper.CheckgroupMapper;
import com.offcn.pojo.CheckgroupCheckitem;
import com.offcn.service.ICheckgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Service
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements ICheckgroupService {

    @Resource
    private CheckgroupMapper checkgroupMapper;

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public void save(Checkgroup checkgroup, Integer[] checkitemIds) {
        //添加检查组
        int insert = checkgroupMapper.insert(checkgroup);
        //获取刚刚被添加的那个检查组对象id
        Integer checkgroupId = checkgroup.getId();
        //添加中间表
        for (Integer checkitemId : checkitemIds) {
            CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckgroupId(checkgroupId);
            checkgroupCheckitem.setCheckitemId(checkitemId);
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }
    }

    @Override
    public void update(Checkgroup checkgroup, Integer[] checkitemIds) {
        //修改检查组的基本信息
        checkgroupMapper.updateById(checkgroup);
        //选择删除中间表原来的数据
        QueryWrapper<CheckgroupCheckitem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("checkgroup_id",checkgroup.getId());
        checkgroupCheckitemMapper.delete(queryWrapper);
        //重新添加到中间表
        for (Integer checkitemId : checkitemIds) {
            CheckgroupCheckitem checkgroupCheckitem=new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
            checkgroupCheckitem.setCheckitemId(checkitemId);
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }
    }

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        Page<Checkgroup> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        String querString=queryPageBean.getQueryString();
        QueryWrapper<Checkgroup> queryWrapper=new QueryWrapper<>();
        if(querString!=null&&!querString.equals("")){
            queryWrapper.like("code",querString)
                    .or().like("name",querString)
                    .or().like("helpCode",querString);
        }
        Page<Checkgroup> checkgroupPage=checkgroupMapper.selectPage(page,queryWrapper);
        return new PageResult(checkgroupPage.getTotal(),checkgroupPage.getRecords());
    }

}
