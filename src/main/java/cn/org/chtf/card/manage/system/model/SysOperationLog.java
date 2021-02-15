package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 
 * @author lm
 */
@Data
public class SysOperationLog implements Serializable{

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
     * 操作类型
     */
    private String act;

    /**
     * 0后台 1前台
     */
    private Integer isback;

    /**
     * 操作者
     */
    private Integer operator;

    /**
     * 数据主键
     */
    private Integer primarykey;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作时间
     */
    private Timestamp operatingtime;


}
