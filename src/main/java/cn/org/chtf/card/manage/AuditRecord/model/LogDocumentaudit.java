package cn.org.chtf.card.manage.AuditRecord.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 证件审核记录
 * @author ggwudivs
 */
@Data
public class LogDocumentaudit implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 0 人员   1车辆
     */
    private Integer act;

    /**
     * 审核人
     */
    private Integer reviewer;

    /**
     * 证件ID
     */
    private Integer documentid;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 
     */
    private Timestamp addtime;
    
    private Integer status;


}
