package cn.org.chtf.card.manage.PreviousInformation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 历届信息管理-代办员信息
 * @author ggwudivs
 */
@Data
public class PimAgent implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;
    
    private Integer memberid;

    private String session;
    
    private String sessionname;
    
    /**
     * 登录用户名
     */
    private String loginname;

    /**
     * 登录密码
     */
    private String loginpass;

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
     * 所属单位（企业）
     */
    private String companyname;

    /**
     * 电话
     */
    private String tel;

    /**
     * 移动电话
     */
    private String phone;

    /**
     * 职务
     */
    private String jobtitle;

    /**
     * 展区负责人手机
     */
    private String areaphone;

    /**
     * 是否现场制证   0否  1是
     */
    private Integer onsitecertification;
    private String onsitecertificationname;

    /**
     * 是否制证   0否  1是
     */
    private Integer makeevidence;
    private String makeevidencename;

    /**
     * 上传车辆行驶证   0否   1是
     */
    private Integer uploadvehiclelicense;
    private String uploadvehiclelicensename;

    /**
     * 图片路径
     */
    private String imagepath;

    /**
     * 是否开通   0未开通   1开通
     */
    private Integer isopen;
    private String isopenname;
    /**
     * 添加时间
     */
    private Timestamp addtime;

    /**
     * 操作者ID
     */
    private Integer createuser;
    private String createusername;

    /**
     * 所属交易团
     */
    private Integer tradinggroupid;
    private String tradinggroupname;
    
    private String agenttype;
    
    private String cardtypes;

    private String cardtypenames;
    
    private Integer type;

}
