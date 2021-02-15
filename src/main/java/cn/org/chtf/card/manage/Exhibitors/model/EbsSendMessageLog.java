package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 发送短信日志
 * @author ggwudivs
 */
@Data
public class EbsSendMessageLog implements Serializable{

    private static final long serialVersionUID = 1L;
    
    /**
     * 届次
     */
    private String session;

    /**
     * 
     */
    private Integer id;

    /**
     * 1：通知参展商，2：通知代办员，3：自由发送
     */
    private String type;

    /**
     * 发送号码
     */
    private String number;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Timestamp addtime;

    /**
     * 返回码
     */
    private String sendcode;

}
