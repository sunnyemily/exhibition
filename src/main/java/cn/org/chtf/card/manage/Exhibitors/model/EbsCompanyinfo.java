package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 企业信息
 * @author ggwudivs
 */
@Data
public class EbsCompanyinfo implements Serializable{

    private static final long serialVersionUID = 1L;
        
    /**
     * 
     */
    private Integer id;
    
    private String xzname;
    
    private Integer auditStatus;
    
    private String auditRemark;
    
    private String hyname;
    
    private String loginname;

    /**
     * 届次
     */
    private String session;
    
    private String sessionname;
    
    private String qyleixing;

    /**
     * 企业类型，关联字典去吧
     */
    private Integer comanyTypeId;

    /**
     * 单位名称中文
     */
    private String chinesename;

    /**
     * 单位名称英文
     */
    private String engname;

    /**
     * 单位名称俄文
     */
    private String russianname;

    /**
     * 单位地址中文
     */
    private String chineseaddress;

    /**
     * 单位地址英文
     */
    private String engaddress;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 网址
     */
    private String website;

    /**
     * 电子邮箱
     */
    private String email;


    /**
     * 国家
     */
    private String country;
    private String countryAndprovince;
    /**
     * 省份
     */
    private Integer province;
    /**
     * 市
     */
    private Integer city;

    /**
     * 联系人
     */
    private String contactperson;

    /**
     * 联系人职务
     */
    private String jobtitle;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 行业分类
     */
    private Integer industryid;

    /**
     * 经营范围
     */
    private String busscope;

    /**
     * 希望结识客户
     */
    private String hopecustomers;

    /**
     * 企业简介
     */
    private String companyprofile;

    /**
     * 采购意向
     */
    private String purchaseintention;

    /**
     * 参展企业性质，关联表
     */
    private Integer exhibitorsNatureId;
    
    private String exhibitorsNatureIdname;

    /**
     * 营业执照路径
     */
    private String buslicensepath;

    /**
     * 公司封面
     */
    private String companypicture;

    /**
     * 公司视频
     */
    private String companyvideo;

    /**
     * 公司轮播图
     */
    private String companypictures;

    /**
     * 相关文件
     */
    private String relateddocumentspath;

    /**
     * 参展展品
     */
    private String exhibits;

    /**
     * 所属交易团
     */
    private Integer tradinggroupid;
    
    private String tradinggroupname;

    /**
     * 组织机构代码证号
     */
    private String organizationcode;

    /**
     * 
     */
    private Timestamp addtime;

    /**
     * 黑名单   1不是  0是
     */
    private Integer useable;

    private Integer shuliang;
    
    private String companylogo;
    
    private String url;
    
    private String zhanweihao;
    
    //参展次数
    private Integer attendcount;
    
  //点击量
    private Integer clickrates;
    
    private String videourl;
    
    private Integer uid;

    /**
     * 法人姓名
     */
    private String legalpersonname;

    /**
     * 法人身份证号码
     */
    private String legalpersoncardnumber;

    /**
     * 法人身份证扫描件
     */
    private String legalpersoncardpath;

    /**
     * 特装搭建服务商资质认证申请表
     */
    private String admissionapplicationform;

    /**
     * 近两年特装搭建300平方米以上合同扫描件
     */
    private String workshopcertificate;

    /**
     * 技术力量证明材料【填写说明：包括设计师、工程师的专业资格证书复印件；其他与展览工程有关工种人员上岗证复印件】
     */
    private String technicalproofdocuments;

    /**
     * 设计施工能力和资质的其他证明资料
     */
    private String othersupportingdocuments;

    /**
     * 承诺书或无违法违规记录证明
     */
    private String previouscase;

    /**
     * 审核时间
     */
    private Timestamp audittime;

    /**
     * 认证开始时间
     */
    private String approveStartTime;

    /**
     * 认证结束时间
     */
    private String approveEndTime;
    
}
