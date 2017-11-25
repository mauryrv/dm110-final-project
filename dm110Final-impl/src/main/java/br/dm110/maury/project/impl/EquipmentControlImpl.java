package br.dm110.maury.project.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.dm110.maury.project.api.EquipmentControlService;
import br.dm110.maury.project.apiEntities.EquipmentTO;
import br.dm110.maury.project.interfaces.EquipmentControlRemote;


@RequestScoped
public class EquipmentControlImpl implements EquipmentControlService {
	@EJB(lookup="java:app/dm110Final-ejb-0.1-SNAPSHOT/EquipmentControlBean!br.dm110.maury.project.interfaces.EquipmentControlRemote")
	private EquipmentControlRemote equipmentControlBean;

	@Override
	public void start(String ip, int mask) {
		String[] ipList = generateIps(ip, mask);
		boolean resultPing = false;
		String status = "Ativo";
		for (String ipGen : ipList) {
			EquipmentTO equipment = new EquipmentTO();
			equipment.setIp(ipGen);
			resultPing = execPing(ipGen);
			if(resultPing)
				status = "Ativo";
			else
				status = "Inativo";
			
			equipment.setStatus(status);
		}
		
	}

	@Override
	public String checkStatus(String ip) {
		return equipmentControlBean.getEquipmentStatus(ip);
	}

	private static String[] generateIps(String networkIp, int cidr) {
		int rangeSize = 0;
		for (int i = 0; i < 32 - cidr; i++) {
			rangeSize |= 1 << i;
		}
		long networkAddress = fromIp(networkIp);
		String[] ips = new String[rangeSize - 1];
		for (int i = 1; i < rangeSize; i++) {
			ips[i - 1] = toIp(networkAddress + i);
		}
		return ips;
	}

	private static long fromIp(String ip) {
		String[] octs = ip.split("\\.");
		long octs1 = Long.parseLong(octs[0]);
		long octs2 = Long.parseLong(octs[1]);
		long octs3 = Long.parseLong(octs[2]);
		long octs4 = Long.parseLong(octs[3]);
		return (octs1 << 24) | (octs2 << 16) | (octs3 << 8) | octs4;
	}

	private static String toIp(long value) {
		return String.format("%s.%s.%s.%s", value >> 24, (value >> 16) & 255, (value >> 8) & 255, value & 255);
	}
	
	public static boolean execPing(String address) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -n 1 " + address);
			InputStream is = process.getInputStream();
			InputStream es = process.getErrorStream();
			String input = processStream(is);
			String error = processStream(es);
			int code = process.waitFor();
			if (code != 0) {
				return false;
			}
			if (error.length() > 0) {
				return false;
			}
			if (input.contains("Host de destino inacess")) {
				return false;
			}
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	public static String processStream(InputStream is) {
		StringBuilder input = new StringBuilder();
		try (Scanner scanner = new Scanner(is)) {
			while (scanner.hasNextLine()) {
				input.append(scanner.nextLine()).append("\n");
			}
		}
		return input.toString();
	}
	
	
	
	
}
