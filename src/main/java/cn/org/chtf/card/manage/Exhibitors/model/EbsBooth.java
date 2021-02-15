package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 参展商管理-展位
 * @author ggwudivs
 */
@Data
public class EbsBooth implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 所属展厅
     */
    private String showRoomName;
    
    /**
     * 所属企业
     */
    private String companyName;
    
    /**
     * 所属交易团
     */
    private String tradingGroupName;
    
    /**
     * 届次名
     */
    private String sessionId;
    
    /**
     * 
     */
    private Integer id;

    /**
     * 届次
     */
    private String session;

    /**
     * 展位名称
     */
    private String name;

    /**
     * 展位价格
     */
    private BigDecimal price;

    /**
     * 是否可用  0不可用  1可用
     */
    private Integer useable;

    /**
     * 是否是角位  0不是  1是
     */
    private Integer angular;

    /**
     * 所属交易团
     */
    private Integer tradinggroupid;

    /**
     * 所属企业id
     */
    private Integer companyId;

    /**
     * 所属展厅id
     */
    private Integer showRoomId;

    /**
     * 添加时间
     */
    private Timestamp addtime;


}
