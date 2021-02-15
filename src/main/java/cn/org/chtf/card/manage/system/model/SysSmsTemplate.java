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
public class SysSmsTemplate implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer smsId;

    /**
     * 展会届次ID
     */
    private Integer smsSessionId;

    /**
     * 短信模板标题
     */
    private String smsTitle;

    /**
     * 模板主体
     */
    private String smsTemplate;


}
