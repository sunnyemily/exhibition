package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 参展商管理-交易团展位分配
 * @author ggwudivs
 */
@Data
public class EbsTradinggroupboothallocation implements Serializable{

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
     * 交易团ID
     */
    private Integer tradinggroupid;

    /**
     * 展厅ID
     */
    private Integer showroomid;

    /**
     * 展位ID
     */
    private Integer boothid;


}
