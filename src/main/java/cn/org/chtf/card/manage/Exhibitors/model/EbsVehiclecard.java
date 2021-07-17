package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

/**
 * 参展商管理-车辆证件审核
 * @author lm
 */
@Data
public class EbsVehiclecard implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;
    
    private Integer isback;
    
    private String jiaoyituan;
    
    private String plastictime;

    /**
     * 届次
     */
    private String session;

    /**
     * 单位名称
     */
    private String companyname;

    /**
     * 司机姓名
     */
    private String drivername;

    /**
     * 车牌号码
     */
    private String platenumber;

    /**
     * 证件类型
     */
    private Integer cardtype;
    
    private String cardtypename;

    /**
     * 车辆行驶证
     */
    private String drivinglicense;

    /**
     * 备注
     */
    private String remark;

    /**
     * 代办员
     */
    private Integer agent;

    /**
     * 0待审核 -1审核未通过  1审核通过
     */
    private Integer status;

    /**
     * 
     */
    private Date addtime;

    private String typename;
    
    private String agentname;
    
    private Integer printstatus;
    private String printstatusname;
    
    private String printtime;
    
    private Integer printtype;
    private String printtypename;
    
    private Integer cardnature;
    private String cardnaturename;
    
    private String makecardtime;
    
    private Integer companyid;
    
    /**
     * 是否取证  0未取  1已取
     */
    private Integer isforensics;

    /**
     * 取证时间
     */
    private String forensicstime;

    /**
     * 证件编号
     */
    private String certificatenumber;

    /**
     * 打印批号
     */
    private String printnumber;
    
    private String reviewremark;
    
    private Integer isplastic;
    
    private String middleid;

    private String phone;
    private Timestamp audittime;

    private String auditFlag;

}
