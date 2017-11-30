package br.dm110.maury.project.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.dm110.maury.project.api.EquipmentControlService;
import br.dm110.maury.project.helperImpl.HelperImpl;
import br.dm110.maury.project.interfaces.EquipmentControlRemote;


@RequestScoped
public class EquipmentControlImpl implements EquipmentControlService {
	@EJB(lookup="java:app/dm110Final-ejb-0.1-SNAPSHOT/EquipmentControlBean!br.dm110.maury.project.interfaces.EquipmentControlRemote")
	private EquipmentControlRemote equipmentControlBean;

	@Override
	public void start(String ip, int mask) {
		
		String[] ipList = HelperImpl.generateIps(ip, mask);
		List<String> ips = new ArrayList<>();
		
		for (String ipGen : ipList) {
			ips.add(ipGen);
			
			if(ips.size()==10){
				equipmentControlBean.insertEquipmentInfo(ips);
				ips = new ArrayList<>();
			}

		}
		
		if(ips.size()>0){
			equipmentControlBean.insertEquipmentInfo(ips);
		}
		
	}

	@Override
	public String checkStatus(String ip) {
		return equipmentControlBean.getEquipmentStatus(ip);
	}


	
}
