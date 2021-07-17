package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 参展商管理-人员证件管理
 * @author ggwudivs
 */
@Data
public class EbsPersonnelcard implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String tjleixing;
    
    private String jiaoyituan;
    
    private String plastictime;

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;
    
    private String sexname;

    /**
     * 国家
     */
    private Integer country;

    /**
     * 省份
     */
    private Integer province;
    
    private Integer city;

    /**
     * 职务
     */
    private String jobtitle;

    /**
     * 企业名称
     */
    private String companyname;

    /**
     * 所属企业
     */
    private Integer companyid;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 身份证/护照
     */
    private Integer idcardpassport;
    
    private String idcardpassportname;

    /**
     * 证件编号
     */
    private String cardnumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 证件类型
     */
    private Integer cardtype;
    
    private String cardtypename;

    /**
     * 公司网址
     */
    private String website;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 照片
     */
    private String imagepath;

    /**
     * 证件照
     */
    private String idphotopath;

    /**
     * 
     */
    private Timestamp addtime;

    /**
     * 审核状态   0待审核  -1审核未通过  1审核通过
     */
    private Integer status;

    private Integer bussinessarea;
    private String industryname;
    private Integer participants;
    private String participantsname;
    private Integer visitexhibition;
    private String zqname;
    private String purpose;
    private String knowexhibition;
    private String businessnature;
    
    private Integer agent;
    private String agentname;
    
    private String remark;
    
    /**
     * 0未打印  1打印中 2已打印
     */
    private Integer printstatus;
    
    private String printstatusname;
    
    private String printtime;

    /**
     * 打印类型  0正常打印  1 IC卡打印
     */
    private Integer printtype;
    
    private String printtypename;

    /**
     * 0未验证  1已验证
     */
    private Integer verificationstatus;
    
    private String verificationstatusname;

    /**
     * IC卡号
     */
    private String iccode;
    
    private String iccodeaes;

    /**
     * 贵宾卡号
     */
    private String vipcode;

    /**
     * 制证性质  0网络  1现场
     */
    private Integer cardnature;
    private String cardnaturename;

    /**
     * 制证时间
     */
    private String makecardtime;
    
    private String boothcode;
    
    private String issuevalid;
    
    private String countryAndprovince;
    
    private Integer isback;
    
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
    
    private String sessionid;
    
    private Integer isout;
    private String isoutname;
    
    private String businesslicense;
    private String purchasingintention;
    
    private Integer isgreen;
    
    private Integer isplastic;
    
    private String middleid;

    private String profession;

    private Timestamp audittime;

    private String auditFlag;
}
