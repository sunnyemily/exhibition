package cn.org.chtf.card.manage.system.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysReservationCentificate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 届次
	 */
	private Integer session;
	
	/**
	 * 预约起始时间
	 */
	private String reserstartdate;
	
	/**
	 * 预约结束时间
	 */
	private String reserenddate;
	
	/**
	 * 可预约人数
	 */
	private Integer reservationNumber;
}
