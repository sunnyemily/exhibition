package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductModel implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer productid;
    private String productname;
    private String productpicture;
    private String companyid;

}
