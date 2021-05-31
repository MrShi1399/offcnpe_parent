package com.offcn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.pojo.Member;
import com.offcn.mapper.MemberMapper;
import com.offcn.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public PageResult listPage(QueryPageBean queryPageBean) {
        Page<Member> page=new Page<>(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        String queryString = queryPageBean.getQueryString();
        if(queryString!=null && !queryPageBean.equals("")){
            queryWrapper.like("name",queryString)
                    .or().eq("phoneNumber",queryString);
        }
        Page<Member> memberPage = memberMapper.selectPage(page, queryWrapper);
        return new PageResult(memberPage.getTotal(),memberPage.getRecords());
    }

    @Override
    public List<Object> getAllYear() {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("min(regTime) as regTime");
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        int sysYear = memberList.get(0).getRegtime().getYear();
        Calendar calendar = Calendar.getInstance();
        //求当前时间
        int currentYear = calendar.get(Calendar.YEAR);
        List<Object> list=new ArrayList<>();
        list.add("不限");
        //生成列表
        for (int i=sysYear;i<=currentYear;i++){
            list.add(i);
        }
        return list;
    }
}
