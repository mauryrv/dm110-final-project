package br.dm110.maury.project.mdb;


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
import br.dm110.maury.project.entities.ListHelper;
import br.dm110.maury.project.helperBean.HelperBean;

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
				if(object instanceof ListHelper){
					
				ListHelper ipList = (ListHelper) object;
				
				for (String ip : ipList.getIps()) {
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
					saveProduct(equipment);
				}
				
				}
				else
				{
					System.out.println("Mensagem não contem lista de ips!!!");
				}
			
			}
			else{
				System.out.println("Mensagem não é um objeto!!!");
			}
		} catch (JMSException e) {
			
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
