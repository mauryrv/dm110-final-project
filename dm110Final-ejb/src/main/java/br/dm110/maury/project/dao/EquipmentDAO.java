package br.dm110.maury.project.dao;

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
		return "Entrouuuuu";
		//return em.createQuery("from Equipment p where ip="+ip);
		
	}
	
	public void insert(Equipment equipment) {
		em.persist(equipment);
	}

}
