package cn.org.chtf.card.manage.Volunteer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import lombok.Data;

/**
 * 志愿者信息
 * @author lm
 */
@Data
public class VolVolunteer implements Serializable{

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
     * 处室ID
     */
    private Integer officeid;
    private String officename;

    /**
     * 部门ID
     */
    private Integer departmentid;
    private String departmentname;

    /**
     * 语种
     */
    private Integer languageid;
    private String languagename;

    /**
     * 上岗日期
     */
    private String postdate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String contactinfo;

    /**
     * 学院
     */
    private String college;

    /**
     * 学号
     */
    private String studentnumber;

    /**
     * 0男   1女
     */
    private Integer sex;
    private String sexname;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Double bodyweight;

    /**
     * 腰围
     */
    private Double waist;

    /**
     * 组长
     */
    private String teamleader;

    /**
     * 添加时间
     */
    private Timestamp addtime;


}
