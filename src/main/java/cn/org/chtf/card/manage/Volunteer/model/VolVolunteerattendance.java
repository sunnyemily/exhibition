package cn.org.chtf.card.manage.Volunteer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 志愿者考勤
 * @author lm
 */
@Data
public class VolVolunteerattendance implements Serializable{

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
     * 志愿者ID
     */
    private Integer vid;
    private String name;

    /**
     * 考勤日期
     */
    private String attendancedate;

    /**
     * 上午上班时间
     */
    private String amstart;

    /**
     * 上午上班时间
     */
    private String amend;

    /**
     * 下午上班时间
     */
    private String pmstart;

    /**
     * 下午下班时间
     */
    private String pmend;

    /**
     * 0正常 1迟到 2早退 3请假 4旷工
     */
    private Integer attendancestatus;
    
    private String attendancestatusname;

    /**
     * 
     */
    private Timestamp addtime;

    /**
     * 创建者
     */
    private Integer createby;
    private String createbyname;

}
