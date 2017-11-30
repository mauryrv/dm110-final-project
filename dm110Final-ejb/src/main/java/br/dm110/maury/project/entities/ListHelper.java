package br.dm110.maury.project.entities;

import java.io.Serializable;
import java.util.List;

public class ListHelper implements Serializable{


	private static final long serialVersionUID = 1L;
	public List<String> ips;

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}
}
