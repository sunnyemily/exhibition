package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 行业表
 * @author ggwudivs
 */
@Data
public class SysIndustry implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 届次
     */
    private String session;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称（中文）
     */
    private String chineseName;

    /**
     * 名称（俄文）
     */
    private String russianName;

    /**
     * 名称（日文）
     */
    private String japaneseName;

    /**
     * 名称（英文）
     */
    private String englishName;

    /**
     * 名称（朝鲜文）
     */
    private String hangulName;

    /**
     * 排序编号
     */
    private Integer orderNum;

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
