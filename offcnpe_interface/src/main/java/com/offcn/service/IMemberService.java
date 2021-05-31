package com.offcn.service;

import com.offcn.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 会员管理
 * @author zs
 * @since 2021-05-25
 */
public interface IMemberService extends IService<Member> {

    /**
     * 分页查询会员信息
     * @param queryPageBean 分页信息
     * @return 分页结果
     */
    PageResult listPage(QueryPageBean queryPageBean);

    /**
     * 获取所有年份
     * @return
     */
    List<Object> getAllYear();
}
