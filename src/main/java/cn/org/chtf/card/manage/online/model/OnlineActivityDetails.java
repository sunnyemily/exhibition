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
public class OnlineActivityDetails implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 活动日期
     */
    private Integer parentid;
    
    private String startdatetime;
    private String enddatetime;

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 
     */
    private String title;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 地址
     */
    private String address;

    /**
     * 链接
     */
    private String link;

    /**
     * 排序
     */
    private Integer orders;
    
    private String roomid;
    
    private String acpicture;

    private String content;
    
    private Integer zhuangtai;
    
    private String liveaddress;
    private String videortmp;
    private String videoflv;
    private String videohls;
    private String videoudp;

}
