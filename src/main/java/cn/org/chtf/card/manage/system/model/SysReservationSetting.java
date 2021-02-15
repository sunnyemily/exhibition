package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 线上预约详细
 * @author lm
 */
@Data
public class SysReservationSetting implements Serializable{

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
     * 预约区间开始日期
     */
    private String reserstartdate;

    /**
     * 预约区间结束日期
     */
    private String reserenddate;

    /**
     * 可预约展会日期
     */
    private String exhibitiondate;

    /**
     * 当天总票数
     */
    private Integer totalvotes;

    /**
     * 线上可预约票数
     */
    private Integer onlinevotes;

    /**
     * 现场票数（预留）
     */
    private Integer onsitevotes;

    /**
     * 创建者
     */
    private Integer createby;

    /**
     * 添加时间
     */
    private Timestamp addtime;

    private Integer status;
    
    private String qujian;

}
