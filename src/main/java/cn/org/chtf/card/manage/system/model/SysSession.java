package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 届次信息
 * @author lm
 */
@Data
public class SysSession implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;

    /**
     * 展会ID
     */
    private Integer exhibitionid;

    /**
     * 届次名
     */
    private String sessionid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态  0不可用   1可用
     */
    private Integer status;

    /**
     * 
     */
    private Date addtime;

    /**
     * 网站顶部logo
     */
    private String siteHeadLogo;

    /**
     * 网站小logo，用于登录、会员等位置
     */
    private String siteSmartLogo;

    /**
     * 采购商注册状态，0:关，1:开
     */
    private Integer purchaserRegisterStatus;

    /**
     * 采购商参展证数量上限
     */
    private Integer purchaserCardLimit;

    /**
     * 取证凭证延时时间，单位为分钟
     */
    private Integer evidenceDelay;

    /**
     * 交易团添加企业截止时间
     */   
    private String delegationAddCompanyEndDate;

    /**
     * 零散参展商注册截止日期
     */
    private String exhibitorsRegistEndDate;

    /**
     * 国外零散参展商注册截止日期
     */
    private String foreignExhibitorsRegistEndDate;

    /**
     * 证件添加截止日期
     */
    private String certificatesEndDate;

    /**
     * 嘉宾B、采购商、布撤展证件截止日期
     */
    private String certificatesFprEndDate;

    /**
     * 零散参展商证件注册截止日期
     */
    private String certificatesExhibitorsEndDate;

    /**
     * 展会开始日期
     */
    private String startDate;

    /**
     * 展会结束日期
     */
    private String endDate;

    /**
     * 网站标题
     */
    private String siteName;

    /**
     * 描述
     */
    private String siteDescription;

    /**
     * 关键词
     */
    private String siteKeywords;

    private String exhibitionname;

    /**
     * 搭建商报馆申请截止日期
     */
    private String stadiumDecoratorEndDate;

}
