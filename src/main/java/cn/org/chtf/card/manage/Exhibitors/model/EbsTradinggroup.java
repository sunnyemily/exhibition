package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 参展商管理-交易团
 * @author lm
 */
@Data
public class EbsTradinggroup implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer orders;
    
    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    private String showRoomName;

    private Integer shuliang;
    
    private Integer boothNumber;
    
    /**
     * 交易团名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;
    
    private String typename;

    /**
     * 联系人
     */
    private String contactperson;

    /**
     * 电话
     */
    private String tel;

    /**
     * 手机
     */
    private String phone;

    /**
     * 添加时间
     */
    private Date addtime;

    private String exportname;

    private Integer agentid;
    
    private String agentname;
    
    private String engname;
    
    private String japanname;
    
    private String russianame;
    
    private String koreaname;
    
}
