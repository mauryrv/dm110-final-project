package br.dm110.maury.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.dm110.maury.project.entities.Equipment;


@Stateless
public class EquipmentDAO {
	
	@PersistenceContext(unitName = "equipment")
	private EntityManager em;

	public String getStatus(String ip)
	{
	String status = "{ \"status\": \"Equipamento não encontrado\"}";
	List<Equipment> equipmentList = 
			 em.createQuery("from Equipment e where e.ip=:ip order by e.check_date desc",Equipment.class)
			.setParameter("ip", ip)
			.getResultList();
			
	if(equipmentList.size()>0){
			status = "{ \"status\":\""+equipmentList.get(0).getStatus().trim()+"\"}";
	}
	return status;
		
	}
	
	public void insert(Equipment equipment) {
		em.persist(equipment);
	}

}
