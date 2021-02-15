package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 展会信息
 * @author lm
 */
@Data
public class SysExhibition implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private Integer id;

    /**
     * 展会名称
     */
    private String exhibitionname;

    /**
     * 二级域名
     */
    private String url;

    /**
     * 是否可用  0不可用  1可用
     */
    private Integer useable;
    
    
    private String russianexhibitionname;
    private String japaneseexhibitionname;
    private String englishexhibitionname;
    private String hangulexhibitionname;
    



}
