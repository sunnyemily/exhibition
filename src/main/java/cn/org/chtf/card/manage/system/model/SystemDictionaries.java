package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 字典表
 * @author ggwudivs
 */
@Data
public class SystemDictionaries implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 届次
     */
    private String session;

    /**
     * 字典ID
     */
    private Integer dicid;

    /**
     * 字典编号
     */
    private String dicCode;

    /**
     * 中文名称
     */
    private String dicCnName;

    /**
     * 英文名称
     */
    private String dicEnName;

    /**
     * 俄文名称
     */
    private String dicRusName;

    /**
     * 日文名称
     */
    private String dicJapName;

    /**
     * 韩文名称
     */
    private String dicKorName;

    /**
     * 父编号
     */
    private Integer dicParentid;

    /**
     * 字典类型
     */
    private Integer dicType;

    /**
     * 描述
     */
    private String dicDescription;

    /**
     * 排序
     */
    private Integer dicOrder;

    /**
     * 是否可用
     */
    private Integer useable;


}
