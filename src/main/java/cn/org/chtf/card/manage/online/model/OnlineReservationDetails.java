package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 线上预约
 * @author lm
 */
@Data
public class OnlineReservationDetails implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private Integer session;

    /**
     * 观展日期
     */
    private String exhibitiondate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String cardnumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 所属单位
     */
    private String companyname;

    /**
     * 国别
     */
    private Integer country;

    /**
     * 省份
     */
    private Integer province;

    /**
     * 城市
     */
    private Integer city;

    /**
     * 行业分类
     */
    private String industry;

    /**
     * 填报人
     */
    private String informant;

    /**
     * 填报人手机号
     */
    private String informantphone;

    /**
     * 填报人验证码
     */
    private String verificationcode;

    /**
     * 展区
     */
    private String exhibitionarea;

    /**
     * 秘钥
     */
    private String secretkey;

    /**
     * 填报时间
     */
    private Date addtime;

    /**
     * （扩展用）
     */
    private Integer status;
    
    /**
     * 类型  0普通  1VIP
     */
    private Integer type;
    
    private Integer createby;


}
