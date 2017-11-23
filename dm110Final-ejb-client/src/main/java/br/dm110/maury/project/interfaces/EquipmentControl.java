package br.dm110.maury.project.interfaces;

public interface EquipmentControl {
	
	void insertEquipmentInfo(String ip,String status);
	void getEquipmentStatus(String ip);

}
