package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.mapper.*;
import com.offcn.pojo.*;
import com.offcn.service.ISetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {

    @Resource
    private SetmealMapper setmealMapper;

    @Resource
    private CheckgroupMapper checkgroupMapper;

    @Resource
    private CheckitemMapper checkitemMapper;

    @Resource
    private SetmealCheckgroupMapper setmealCheckgroupMapper;

    @Resource
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;

    @Override
    public void save(Setmeal setmeal, Integer[] checkgroupIds) {
        //保存套餐信息
        setmealMapper.insert(setmeal);
        //保存套餐下有哪些检查组
        for (Integer checkgroupId : checkgroupIds) {
            SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
            setmealCheckgroup.setSetmealId(setmeal.getId());
            setmealCheckgroup.setCheckgroupId(checkgroupId);
            setmealCheckgroupMapper.insert(setmealCheckgroup);
        }
    }

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        //设置分页条件
        Page<Setmeal> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        //设置查询条件
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        if (queryString != null && !queryString.equals("")) {
            queryWrapper.like("code", queryString)
                    .or().like("name", queryString)
                    .or().like("helpCode", queryString);
        }
        //执行分页条件和查询条件，
        Page<Setmeal> setmealPage = setmealMapper.selectPage(page, queryWrapper);
        return new PageResult(setmealPage.getTotal(), setmealPage.getRecords());
    }

    @Override
    public Setmeal getInfo(Integer id) {
        //查询套餐信息
        Setmeal setmeal = setmealMapper.selectById(id);
        //查询该套餐下有哪些检查组
        QueryWrapper<SetmealCheckgroup> setmealCheckgroupQueryWrapper = new QueryWrapper<>();
        setmealCheckgroupQueryWrapper.eq("setmeal_id", setmeal.getId());
        List<SetmealCheckgroup> setmealCheckgroupList = setmealCheckgroupMapper.selectList(setmealCheckgroupQueryWrapper);
        //判断是否有检查组,没有直接返回套餐
        if (setmealCheckgroupList == null || setmealCheckgroupList.size() == 0) {
            return setmeal;
        }
        //查找该套餐下的检查组信息
        //保存检查组的信息
        List<Checkgroup> checkgroupList = new ArrayList<>();
        for (SetmealCheckgroup setmealCheckgroup : setmealCheckgroupList) {
            //获取该循环下的检查组信息
            Checkgroup checkgroup = checkgroupMapper.getById(setmealCheckgroup.getCheckgroupId());
            //查找该循环下检查组有哪些检查项
            QueryWrapper<CheckgroupCheckitem> checkgroupCheckitemQueryWrapper = new QueryWrapper<>();
            checkgroupCheckitemQueryWrapper.eq("checkgroup_id", checkgroup.getId());
            List<CheckgroupCheckitem> checkgroupCheckitemList = checkgroupCheckitemMapper.selectList(checkgroupCheckitemQueryWrapper);
            //查找该循环检查组下的检查项信息
            if (checkgroupCheckitemList != null && checkgroupCheckitemList.size() > 0) {
                //保存检查项信息
                List<Checkitem> checkitemList=new ArrayList<>();
                for (CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitemList) {
                    Checkitem checkitem = checkitemMapper.getById(checkgroupCheckitem.getCheckitemId());
                    checkitemList.add(checkitem);
                }
                //把检查项集合中的数据设置到Checkgroup对象中
                checkgroup.setCheckitemList(checkitemList);
            }
            checkgroupList.add(checkgroup);
        }
        //把检查组中的数据保存到套餐集合中
        setmeal.setCheckgroupList(checkgroupList);
        return setmeal;
    }
}
