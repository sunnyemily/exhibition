package cn.org.chtf.card.manage.online.model;


import java.io.Serializable;

import lombok.Data;

@Data
public class ExhibitionInfoModel implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String exhibitionname; 
    
    private String exhibitionengname;
    
    private String exhibitionjapanname;
    
    private String exhibitionrussianame;
    
    private String exhibitionkoreaname;
    
    private String exhibitionid;
    
    private Integer type;
    
    private String shortname;
    
    private String shortnametwo;
    
    private String smallimage;
    
    private String bigimage;

}
