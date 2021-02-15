package cn.org.chtf.card.manage.MakeEvidence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 证件类型管理
 * @author lm
 */
@Data
public class CmCertificateType implements Serializable{

    private static final long serialVersionUID = 1L;
    public static final int TOLL_YES = 1;
    public static final int TOLL_NO = 0;

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    /**
     * 中文名称
     */
    private String chinesename;

    /**
     * 英文名称
     */
    private String engname;

    /**
     * 日文名称
     */
    private String japanname;

    /**
     * 俄文名称
     */
    private String russianame;

    /**
     * 韩文名称
     */
    private String koreaname;

    /**
     * 证件类型 0人员证件   1车辆通行证
     */
    private Integer type;

    /**
     * 是否参展证  0否  1是
     */
    private Integer isexhibitor;

    /**
     * 是否可用   0否  1是
     */
    private Integer useable;

    /**
     * 是否观众调查   0否   1是
     */
    private Integer issurvey;

    /**
     * 显示顺序
     */
    private Integer orders;
    
    private Integer istoll;

    /**
     * 印制数量
     */
    private Integer printsnum;

    /**
     * 
     */
    private Date addtime;


}
