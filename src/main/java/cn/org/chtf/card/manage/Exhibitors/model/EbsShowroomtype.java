package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 参展商管理-展厅类型
 * @author lm
 */
@Data
public class EbsShowroomtype implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;
    
    private Integer shuliang;

    /**
     * 届次
     */
    private String session;

    /**
     * 展厅类型
     */
    private String name;

    /**
     * 收费方式
     */
    private String tollmehod;

    /**
     * 是否室外展区
     */
    private Integer isoutdoor;

    /**
     * 面积
     */
    private BigDecimal area;

    /**
     * 标准展位价格
     */
    private BigDecimal price1;

    /**
     * 角位展位价格
     */
    private BigDecimal price2;

    /**
     * 特装展位价格
     */
    private BigDecimal price3;

    /**
     * 价格单位
     */
    private String priceunit;

    /**
     * 参展证数
     */
    private Integer exhibitorsnumber;
    
    /**
     * 布撤展证数量
     */
    private Integer withdrawalsnumber;

    /**
     * 特装展位限制
     */
    private BigDecimal boothlimit;

    /**
     * 填报说明
     */
    private String explanation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 是否可用  0不可用  1可用
     */
    private Integer status;


}
