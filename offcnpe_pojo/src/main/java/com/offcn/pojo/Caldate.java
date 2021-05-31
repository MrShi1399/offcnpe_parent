package com.offcn.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 13320
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caldate extends Model {
    /**
     * 每个月的哪一天
     */
    private Integer date;
    /**
     * 可预约人数
     */
    private Integer number;

    /**
     * 已预约人数
     */
    private Integer reservations;
}
