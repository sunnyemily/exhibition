package cn.org.chtf.card.manage.ReportManagement.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class AudienceSurvey implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer typeid;
    
    private String type;
    
    private String typename;
    
    private String answername;
    
    private int total;
    
    private String percent;
    
    

}
