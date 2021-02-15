package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 打印模版管理
 * @author lm
 */
@Data
public class SysPrintTemplate implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    /**
     * 证件类型
     */
    private String cardtypeid;
    private String cardtypename;

    /**
     * 证件模版
     */
    private String printTemplate;


}
