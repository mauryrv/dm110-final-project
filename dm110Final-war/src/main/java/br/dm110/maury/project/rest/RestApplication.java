package br.dm110.maury.project.rest;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.dm110.maury.project.impl.EquipmentControlImpl;

@ApplicationPath("/api")
public class RestApplication extends Application{
	
@Override
public Set<Class<?>> getClasses() {
	// TODO Auto-generated method stub
	Set<Class<?>> classes = new HashSet<>();
	classes.add(EquipmentControlImpl.class);
	return classes;
}

}
