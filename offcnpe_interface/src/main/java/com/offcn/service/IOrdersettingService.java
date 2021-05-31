package com.offcn.service;

import com.offcn.pojo.Caldate;
import com.offcn.pojo.Ordersetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * 文件服务
 * @author zs
 * @since 2021-05-25
 */
public interface IOrdersettingService extends IService<Ordersetting> {
    /**
     * 上传模板文件
     * @param ordersettingList
     */
    void saveList(List<Ordersetting> ordersettingList);

    /**
     * 获取某年某月某日的预约情况
     * @param date 当前年月，例如 2020/05
     * @return 预约情况
     */
    List<Caldate> listOrdersetting(String date);

    /**
     * 修改预约设置
     * @param date
     */
    void update(String date,Integer number);

}
