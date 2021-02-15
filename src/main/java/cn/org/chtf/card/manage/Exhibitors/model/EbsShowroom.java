package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 参展商管理-展厅管理
 * @author lm
 */
@Data
public class EbsShowroom implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    /**
     * 展厅代码
     */
    private String code;

    /**
     * 展厅名称
     */
    private String name;

    /**
     * 展厅类型
     */
    private Integer type;

    /**
     * 是否可用   0不可用  1可用
     */
    private Integer useable;

    /**
     * 显示顺序
     */
    private Integer orders;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addtime;

    private String typename;
    
    private Integer shuliang;

}
