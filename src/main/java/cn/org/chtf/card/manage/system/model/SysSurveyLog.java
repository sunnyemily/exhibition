package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 参与调查的日志
 * @author lm
 */
@Data
public class SysSurveyLog implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 操作类型
     */
    private String act;

    /**
     * 操作者ID
     */
    private Integer userid;

    /**
     * 0后台  1前台
     */
    private Integer isback;

    /**
     * 调查表ID
     */
    private Integer surveyid;
    
    private Integer companyid;


}
