package br.dm110.maury.project.mdb;

import java.sql.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.dm110.maury.project.apiEntities.EquipmentTO;
import br.dm110.maury.project.dao.EquipmentDAO;
import br.dm110.maury.project.entities.Equipment;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType",propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",propertyValue = "java:/jms/queue/equipmentControlQueue"),
		@ActivationConfigProperty(propertyName = "maxSession",propertyValue="10")
	})
public class EquipmentControlMDB implements MessageListener{
	@EJB
	private EquipmentDAO equipmentDAO;

	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessage){
				ObjectMessage objMessage = (ObjectMessage) message;
				Object object = objMessage.getObject();
				if(object instanceof EquipmentTO){
				EquipmentTO equipmentTO = (EquipmentTO) object;
				saveProduct(equipmentTO);

				}
				else
				{
					System.out.println("Mensagem não contem um status de equipamento!!!");
				}
			
			}
			else{
				System.out.println("Mensagem não é um objeto!!!");
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void saveProduct(EquipmentTO equipmentTO) {
		Equipment equipment = new Equipment();
		equipment.setIp(equipmentTO.getIp());
		equipment.setStatus(equipmentTO.getStatus());
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		
		equipment.setCheck_date(sqlDate);
		equipmentDAO.insert(equipment);
		
	}

}
