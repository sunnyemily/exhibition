package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 在线询盘
 * @author lm
 */
@Data
public class OnlineInquiry implements Serializable{

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
     * 
     */
    private Integer memberid;

    /**
     * 产品类别
     */
    private Integer productmenuid;
    
    private String menuname;

    /**
     * 姓名
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 数量单位
     */
    private Integer quantityunit;
    
    private String quantityunitname;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 内容
     */
    private String content;

    /**
     * 添加时间
     */
    private String addtime;

    /**
     * 0审核拒绝 1待审 2审核通过
     */
    private Integer status;

    /**
     * 拒绝理由
     */
    private String remark;

    /**
     * 审核时间
     */
    private String audittime;

    /**
     * 审核人
     */
    private Integer audituser;


}
