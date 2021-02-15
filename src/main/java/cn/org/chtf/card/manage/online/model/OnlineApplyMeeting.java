package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 
 * @author lm
 */
@Data
public class OnlineApplyMeeting implements Serializable{

    private static final long serialVersionUID = 1L;
    
    

    /**
     * 
     */
    private Integer id;

    /**
     * 会议开始时间
     */
    private String meetingstart;

    /**
     * 会议结束时间
     */
    private String meetingend;

    /**
     * 联系人
     */
    private String contactperson;

    /**
     * 联系电话
     */
    private String contacttel;

    /**
     * 公司名称
     */
    private String companyname;

    /**
     * 会议类型
     */
    private String meetingtype;

    /**
     * 参会人员手机号码
     */
    private String phones;

    /**
     * 操作人
     */
    private Integer createby;

    /**
     * 申请时间
     */
    private Timestamp addtime;

    /**
     * 会议号
     */
    private String meetingnumber;

    /**
     * 0审核拒绝  1待审核  2审核通过
     */
    private Integer status;

    /**
     * 审核拒绝理由
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
    
    private String session;


}
