package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 预约洽谈
 * @author lm
 */
@Data
public class OnlineNegotiate implements Serializable{

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
     * 会员Id
     */
    private Integer memberid;

    /**
     * 被预约企业
     */
    private Integer companyid;
    private String companyname;
    /**
     * 被预约产品
     */
    private Integer productid;
    private String productname;
    /**
     * 预约者姓名
     */
    private String name;

    /**
     * 预约者联系方式
     */
    private String tel;

    /**
     * 内容
     */
    private String content;

    /**
     * 添加时间
     */
    private Timestamp addtime;

    /**
     * 0审核拒绝 1待审 2审核通过
     */
    private Integer status;

    /**
     * 拒绝理由
     */
    private String remark;

    /**
     * 审核时间
     */
    private String audittime;

    /**
     * 审核人
     */
    private Integer audituser;
    // 0意向订单  1预约洽谈
    private Integer act;


}
