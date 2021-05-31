package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.pojo.Checkitem;
import com.offcn.mapper.CheckitemMapper;
import com.offcn.service.ICheckitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;


/**
 * <p>
 *  服务实现类
 * </p>
 * 预约管理
 * @author zs
 * @since 2021-05-25
 */
@Service
public class CheckitemServiceImpl extends ServiceImpl<CheckitemMapper, Checkitem> implements ICheckitemService {

    @Resource
    private CheckitemMapper checkitemMapper;

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        //创建分页对象
        Page<Checkitem> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //设置查询条件
        QueryWrapper<Checkitem> queryWrapper=new QueryWrapper<>();
        //注意这里写表的列名称，不是写实体类的属性名称
        if(queryPageBean.getQueryString()!=null&&!queryPageBean.getQueryString().equals("")){
            queryWrapper.like("name",queryPageBean.getQueryString())
                    .or().like("code",queryPageBean.getQueryString());
        }
        //没有查询条件不是查全部
        Page<Checkitem> checkitemPage=checkitemMapper.selectPage(page,queryWrapper);
        return new PageResult(checkitemPage.getTotal(),checkitemPage.getRecords());
    }
}
