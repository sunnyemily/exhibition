package cn.org.chtf.card.manage.AuditRecord.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 展位审核记录
 * @author ggwudivs
 */
@Data
public class LogBoothaudit implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 0财务确认   1展览处确认
     */
    private Integer act;

    /**
     * 审核人
     */
    private Integer reviewer;

    /**
     * 企业ID
     */
    private Integer companyid;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private Timestamp addtime;

    private Integer status;

}
