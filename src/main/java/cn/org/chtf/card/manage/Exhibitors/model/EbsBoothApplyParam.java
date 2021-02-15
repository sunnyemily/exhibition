package cn.org.chtf.card.manage.Exhibitors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 展位申请表 返回参数
 * @author wushixing
 */
@Data
public class EbsBoothApplyParam extends EbsBoothApply{

    private static final long serialVersionUID = 1L;
    
    /**
     * 申请展位详情
     */
    private List<EbsBoothApplyList> list;
    /**
     * 选中的展位
     */
    private Integer[] checkedBooths;
    /**
        * 未选中的展位
     */
    private Integer[] unCheckedBooths;


}
