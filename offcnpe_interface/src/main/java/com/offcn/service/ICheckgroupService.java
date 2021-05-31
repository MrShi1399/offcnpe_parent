package com.offcn.service;

import com.offcn.pojo.Checkgroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;
import com.offcn.util.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-05-25
 */
public interface ICheckgroupService extends IService<Checkgroup> {
    /**
     * 将选择的检查项保存到检查组
     * @param checkgroup 保存到哪个检查组
     * @param checkitemIds 需要保存到检查组的检查项的数组
     */
    void save(Checkgroup checkgroup,Integer[] checkitemIds);

    /**
     * 修改检查组的基本信息和该检查组写的检查项
     * @param checkgroup 检查组的基本信息
     * @param checkitemIds 该检查组下的检查项
     */
    void update(Checkgroup checkgroup,Integer[] checkitemIds);

    /**
     * 将查询的检查组按要求进行分页
     * @param queryPageBean 封装的查询条件类
     * @return 分页结果
     */
    PageResult listPage(QueryPageBean queryPageBean);


}
