package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 国家地区表
 * @author ggwudivs
 */
@Data
public class SysCountryArea implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 届次
     */
    private String session;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否是外国（0：不是外国，1：是外国）
     */
    private String isForeign;

    /**
     * 排序序号
     */
    private Integer orderNum;

    /**
     * 父国家或地区id
     */
    private String parentId;

    /**
     * 添加时间
     */
    private Timestamp addTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 是否可用标记
     */
    private String useFlag;


}
