package br.dm110.maury.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/poller")
public interface EquipmentControlService {
	
	@GET
	@Path("/start/{IP}/{Mask}")
	@Produces(MediaType.APPLICATION_JSON)
	String start(@PathParam("IP") String ip,@PathParam("Mask") int mask);
	
	@GET
	@Path("/status/{IP}")
	@Produces(MediaType.APPLICATION_JSON)
	String checkStatus(@PathParam("IP") String ip);

}
