package cn.org.chtf.card.manage.ReportManagement.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PurchaseIntentionStatistics implements Serializable{

    private static final long serialVersionUID = 1L;
    
        
    private String name;
    
    private Integer shuliang;
    
    private String percent;

}
