package br.dm110.maury.project.apiEntities;

import java.io.Serializable;

public class EquipmentTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String ip;
	private String status;
	
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

}
