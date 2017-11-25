package br.dm110.maury.project.interfaces;

import br.dm110.maury.project.apiEntities.EquipmentTO;

public interface EquipmentControl {
	
	void insertEquipmentInfo(EquipmentTO equipment);
	String getEquipmentStatus(String ip);

}
