package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class NewsParams implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer page;
    
    private Integer limit;
    
    private String newstype;
    
    private String keywords;
    
    private Integer uid;
    
    private String session;

}
