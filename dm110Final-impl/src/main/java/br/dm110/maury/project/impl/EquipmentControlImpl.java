package br.dm110.maury.project.impl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.dm110.maury.project.api.EquipmentControlService;
import br.dm110.maury.project.apiEntities.EquipmentTO;
import br.dm110.maury.project.helper.Helper;
import br.dm110.maury.project.interfaces.EquipmentControlRemote;


@RequestScoped
public class EquipmentControlImpl implements EquipmentControlService {
	@EJB(lookup="java:app/dm110Final-ejb-0.1-SNAPSHOT/EquipmentControlBean!br.dm110.maury.project.interfaces.EquipmentControlRemote")
	private EquipmentControlRemote equipmentControlBean;

	@Override
	public void start(String ip, int mask) {
		
		String[] ipList = Helper.generateIps(ip, mask);
		boolean resultPing = false;
		String status = "Ativo";
		for (String ipGen : ipList) {
			EquipmentTO equipment = new EquipmentTO();
			equipment.setIp(ipGen);
			resultPing = Helper.execPing(ipGen);
			if(resultPing)
				status = "Ativo";
			else
				status = "Inativo";
			
			equipment.setStatus(status);
			equipmentControlBean.insertEquipmentInfo(equipment);
		}
		
	}

	@Override
	public String checkStatus(String ip) {
		return equipmentControlBean.getEquipmentStatus(ip);
	}


	
}
