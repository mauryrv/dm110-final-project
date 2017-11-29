package br.dm110.maury.project.interfaces;

import java.util.List;

import br.dm110.maury.project.apiEntities.EquipmentTO;

public interface EquipmentControl {
	
	void insertEquipmentInfo(List<String> ips);
	String getEquipmentStatus(String ip);

}
