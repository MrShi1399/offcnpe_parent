package com.offcn.service;

import com.offcn.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.offcn.util.PageResult;
import com.offcn.util.QueryPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *  套餐管理
 * @author zs
 * @since 2021-05-25
 */
public interface ISetmealService extends IService<Setmeal> {
    /**
     * 添加套餐业务
     * @param setmeal 套餐信息
     * @param checkgroupIds 该套餐下的检查组
     */
    void save(Setmeal setmeal,Integer[] checkgroupIds);

    /**
     * 查询分页后的数据
     * @param queryPageBean 分页条件
     * @return 分页信息和该分页的结果
     */
    PageResult listPage(QueryPageBean queryPageBean);

    /**
     * 查询该套餐下有哪些检查组，检查组中有哪些检查项
     * @param id
     * @return
     */
    Setmeal getInfo(Integer id);
}
