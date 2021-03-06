package br.dm110.maury.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_equipment", sequenceName = "seq_equipment", allocationSize = 1)
public class Equipment {
	@Id
	@GeneratedValue(generator = "seq_equipment", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String ip;
	private String status;
	private java.sql.Timestamp check_date;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public java.sql.Timestamp getCheck_date() {
		return check_date;
	}
	public void setCheck_date(java.sql.Timestamp check_date) {
		this.check_date = check_date;
	}



}
