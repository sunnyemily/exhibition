package cn.org.chtf.card.manage.PreviousInformation.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 历届信息管理-代办员类型
 * @author ggwudivs
 */
@Data
public class PimAgenttype implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 代办员ID
     */
    private Integer agentid;

    /**
     * 证件类型
     */
    private Integer cardtype;

    /**
     * 数量
     */
    private Integer quantity;
    
    private String rounds;


}
