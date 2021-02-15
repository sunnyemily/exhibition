package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 展位申请表
 * @author ggwudivs
 */
@Data
public class EbsBoothApply implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String memberUsername;

    /**
     * 申请主键id
     */
    private Integer applyId;

    /**
     * 届次id
     */
    private Integer sessionId;

    /**
     * 申请产品
     */
    private String applyProducts;

    /**
     * 营业执照
     */
    private String applyLicense;

    /**
     * 其他文件
     */
    private String applyFile;

    /**
     * 申请人id
     */
    private Integer memberId;

    /**
     * 申请企业id
     */
    private Integer companyId;

    /**
     * 申请日期
     */
    private Timestamp applyAddtime;


}
