package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 展位申请详细表
 * @author ggwudivs
 */
@Data
public class EbsBoothApplyList implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 展位申请主键
     */
    private Integer applyId;

    /**
     * 交易团主键
     */
    private Integer tradinggroupId;

    /**
     * 展厅类型主键
     */
    private Integer showroomTypeId;

    /**
     * 搭建类型：0特装，1标准。
     */
    private Integer applyBuildType;

    /**
     * 申请个数
     */
    private Integer applyCount;

    /**
     * 申请面积
     */
    private Double applyArea;

    /**
     * 是否拆除标准隔板
     */
    private Integer applyRemoveSeparator;

    /**
     * 设备长度
     */
    private Double applyDeviceLength;

    /**
     * 设备宽
     */
    private Double applyDeviceWidth;

    /**
     * 设备高
     */
    private Double applyDeviceHigh;

    /**
     * 用电总量
     */
    private Double applyElectricityAmount;

    /**
     * 电压
     */
    private String applyVoltage;


}
