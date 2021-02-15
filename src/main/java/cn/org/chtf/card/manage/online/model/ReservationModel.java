package cn.org.chtf.card.manage.online.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservationModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String exhibitiondate;
	
	private Integer useable;
	
	private Integer onlinevotes;
	
	private Integer totalvotes;
	
	private Integer dangtiantotal;
	
	private Integer dangtiancount;
	
	private Integer tiqiancount;
}
