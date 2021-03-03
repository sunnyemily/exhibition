package cn.org.chtf.card.manage.Exhibitors.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 搭建商管理-报馆申请
 * @author lm
 */
@Data
public class EbsStadium implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private Integer isback;
    
    private String jiaoyituan;

    /**
     * 届次
     */
    private String session;

    private Integer companyid;

    /**
     * 单位名称
     */
    private String companyname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 代办员
     */
    private Integer agent;

    private String agentname;

    /**
     * 审核状态，1-未审核，2-审核通过，3-审核不通过
     */
    private Integer status;

    private Timestamp addtime;

    private Timestamp updatetime;
    
    private Integer printstatus;
    private String printstatusname;
    
    private String printtime;
    
    private Integer printtype;
    private String printtypename;

    /**
     * 打印批号
     */
    private String printnumber;

    /**
     * 制证时间
     */
    private String makecardtime;
    
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
     * 未通过原因
     */
    private String reviewremark;

    /**
     * 塑封状态，0-未塑封，1-已塑封
     */
    private Integer isplastic;

    private String plastictime;

    /**
     * 关联展商ID
     */
    private Integer relationcompanyid;

    /**
     * 关联展商名称
     */
    private String relationcompanyname;

    /**
     * 支付状态，0-待缴费，1-已缴费
     */
    private Integer paystatus;
    private String paystatusname;

    /**
     * 审核时间
     */
    private Timestamp audittime;

    /**
     * 特装展位搭建安全责任承诺书（提供下载）（附件）
     */
    private String safetyresponsibilitycommit;

    /**
     * 特装展位装修工程申请表（提供下载）（附件）
     */
    private String fitmentapplication;

    /**
     * 特装工程效果图（附件）
     */
    private String effectdiagram;

    /**
     * 特装工程施工图（附件）
     */
    private String constructiondiagram;

    /**
     * 特装工程载荷图或点位图（附件）
     */
    private String pointdiagram;

    /**
     * 特装工程电路图（附件）
     */
    private String circuitdiagram;

    /**
     * 施工人员身份证，电焊工上岗证等复印件（附件）
     */
    private String workercertificatecopy;

    /**
     * 特装展位器材及用料说明：包括各项器材及用料环保与消防方面合格证明（附件）
     */
    private String equipmentqualifiedprove;

    /**
     * 参与参与特装布展单位需出具为布展期间参与施工的工作人员进行施工意外伤害保险的相关证明（附件）
     */
    private String workercasualtyprove;

}
