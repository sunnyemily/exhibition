package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 调查结果表
 * @author lm
 */
@Data
public class SysSurveyResult implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 调查表ID
     */
    private Integer surveyid;

    /**
     * 答案ID
     */
    private Integer answerid;

    /**
     * 文本类答案
     */
    private String remark;

    /**
     * 用户id
     */
    private Integer userid;

    /**
     * 企业ID
     */
    private Integer companyid;
    
    private Integer questionid;


}
