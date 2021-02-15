package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 调查涉及问题表
 * @author lm
 */
@Data
public class SysSurveyQuestion implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 父级id（调查ID）
     */
    private Integer parentid;

    /**
     * 中文名称
     */
    private String cntitle;

    /**
     * 英文名称
     */
    private String engtitle;

    /**
     * 俄文名称
     */
    private String russiantile;

    /**
     * 日文名称
     */
    private String japtile;

    /**
     * 朝鲜文名称
     */
    private String hangultitle;

    /**
     * 0多选  1单选  2文本
     */
    private Integer showtype;
    
    private String showtypename;

    /**
     * 控件代码
     */
    private String code;

    /**
     * 0不可用  1可用
     */
    private Integer useable;

    private Integer orders;

}
