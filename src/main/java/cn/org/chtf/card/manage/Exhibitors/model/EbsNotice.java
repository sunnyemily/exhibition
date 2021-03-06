package cn.org.chtf.card.manage.Exhibitors.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 搭建商公告
 * @author sunhuazhong
 */
@Data
public class EbsNotice implements Serializable {

    private Integer id;

    private String title;

    private String content;

    private Integer noticeOrder;

    private String session;

    private Integer status;

    private Integer authorId;

    private String authorName;

    private String authorAccount;

    private Timestamp addTime;

    private Timestamp updateTime;

    public String getUpdateTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getUpdateTime());
    }
}