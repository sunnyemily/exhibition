package cn.org.chtf.card.manage.ReportManagement.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReportManagement implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String leixing;
    
    private Integer id;
    
    private String cardtypename;
    
    private String tjleixing;
    
    private String companyname;
    
    private String name;
    
    private String imagepath;
    
    private String agentname;
    
    private String cardnumber;

}
