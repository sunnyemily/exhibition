package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 调查信息表
 * @author lm
 */
@Data
public class SysSurvey implements Serializable{

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
     * 中文名称
     */
    private String cnname;

    /**
     * 英文名称
     */
    private String engname;

    /**
     * 俄文名称
     */
    private String russianname;

    /**
     * 日文名称
     */
    private String japname;

    /**
     * 朝鲜文名称
     */
    private String hangulname;

    /**
     * 0企业  1个人
     */
    private Integer type;

    /**
     * 0不可用  1可用
     */
    private Integer useable;

    /**
     * 添加时间
     */
    private Timestamp addtime;

    /**
     * 创建者
     */
    private Integer createby;

    private String createbyname;

}
