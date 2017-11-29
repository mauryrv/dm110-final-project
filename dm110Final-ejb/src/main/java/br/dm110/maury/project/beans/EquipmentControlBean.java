package br.dm110.maury.project.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import br.dm110.maury.project.apiEntities.EquipmentTO;
import br.dm110.maury.project.dao.EquipmentDAO;
import br.dm110.maury.project.entities.Equipment;
import br.dm110.maury.project.helperBean.HelperBean;
import br.dm110.maury.project.interfaces.EquipmentControlLocal;
import br.dm110.maury.project.interfaces.EquipmentControlRemote;

@Stateless
@Remote(EquipmentControlRemote.class)
@Local(EquipmentControlLocal.class)
public class EquipmentControlBean implements EquipmentControlLocal, EquipmentControlRemote {

	@EJB
	private EquipmentDAO equipmentDAO;
	
	@Resource(lookup="java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(lookup="java:/jms/queue/equipmentControlQueue")
	private Queue queue;
	
	@Override
	public void insertEquipmentInfo(List<String> ips){//) {
		
		for (String ip : ips) {
			boolean resultPing = false;
			String status = "Ativo";
			EquipmentTO equipment = new EquipmentTO();
			equipment.setIp(ip);
			resultPing = HelperBean.execPing(ip);
			if(resultPing)
				status = "Ativo";
			else
				status = "Inativo";
			
			equipment.setStatus(status);

		try (Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);) {
			
			ObjectMessage objMessage = session.createObjectMessage(equipment);
			producer.send(objMessage);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		}
		
	}

	@Override
	public String getEquipmentStatus(String ip) {
		
		return equipmentDAO.getStatus(ip);
		
	}

}
