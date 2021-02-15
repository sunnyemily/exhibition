package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 调查涉及答案表
 * @author lm
 */
@Data
public class SysSurveyAnswer implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    private Integer questionid;
    
    /**
     * 中文名称
     */
    private String cnanswername;

    /**
     * 英文名称
     */
    private String enganswername;

    /**
     * 俄文名称
     */
    private String russiaanswername;

    /**
     * 日文名称
     */
    private String japanswername;

    /**
     * 朝鲜文名称
     */
    private String hangulanswername;

    /**
     * 0不可用  1可用
     */
    private Integer useable;

    private Integer orders;

}
