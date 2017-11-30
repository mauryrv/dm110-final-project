package br.dm110.maury.project.interfaces;

import java.util.List;

public interface EquipmentControl {
	
	void insertEquipmentInfo(List<String> ips);
	String getEquipmentStatus(String ip);

}
