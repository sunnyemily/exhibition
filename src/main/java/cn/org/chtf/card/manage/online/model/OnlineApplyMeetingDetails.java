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
public class OnlineApplyMeetingDetails implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 会议ID
     */
    private Integer meetingid;

    /**
     * 参会手机号
     */
    private String mobilephone;

    /**
     * 参会密码
     */
    private String pass;

    /**
     * 是否主持人  0否   1是
     */
    private Integer ishost;


}
