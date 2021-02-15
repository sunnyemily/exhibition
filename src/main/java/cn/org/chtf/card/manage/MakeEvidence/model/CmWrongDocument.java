package cn.org.chtf.card.manage.MakeEvidence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 
 * @author ggwudivs
 */
@Data
public class CmWrongDocument implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;
    private String sessionname;

    /**
     * 证件类型
     */
    private Integer cardtype;
    private String cardtypename;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 操作时间
     */
    private String controltime;


}
