package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 参展商管理-嘉宾B-布撤展企业管理
 * @author lm
 */
@Data
public class EbsGuestbexhibition implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;
    
    private Integer memberid;

    /**
     * 届次
     */
    private String session;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String loginpass;

    /**
     * 企业名称
     */
    private String companyname;

    /**
     * 证件数
     */
    private Integer cardnumber;

    /**
     * 电话
     */
    private String tel;

    /**
     * 4嘉宾B   3布撤展
     */
    private Integer type;

    /**
     * 
     */
    private Date addtime;


}
