package cn.org.chtf.card.manage.online.model;

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
public class OnlineActivityManage implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 活动日期
     */
    private String activitydate;

    /**
     * 星期几
     */
    private String theweek;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 状态  0不可用  1可用
     */
    private Integer status;
    
    private String session;


}
