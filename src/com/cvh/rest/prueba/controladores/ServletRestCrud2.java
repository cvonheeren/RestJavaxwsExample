package com.cvh.rest.prueba.controladores;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cvh.rest.prueba.accesoadatos.AccesoADatosMySqlJpa;
import com.cvh.rest.prueba.entidades.Cliente;

@Path("/clientes/")
@Consumes("application/json")
@Produces("application/json")
public class ServletRestCrud2 {

	AccesoADatosMySqlJpa instancia;
	
	public ServletRestCrud2() {
		instancia = AccesoADatosMySqlJpa.getInstance();
	}
	
	@GET
	public Response mostrarTodos() {	
		return Response.ok().entity(instancia.obtenerTodos()).build();
	}
	
	@GET
	@Path("{id}")
	public Response mostrarPorId(@PathParam("id") Long id) {
		return Response.ok().entity(instancia.obtenerPorId(id)).build();	
	}
	
	@PUT
	@Path("{id}")
	public Response editar(@PathParam("id") Long id, Cliente cliente) {
		TreeMap<String, String> errores = new TreeMap<>();
		
		if(cliente.getNombre().trim().length() == 0) {
			errores.put("nombre", "No puede estar vacío");
			return Response.status(Status.BAD_REQUEST).entity(errores).build();
		}
		
		instancia.modificar(cliente);
			
		// TODO que no permita cambiar el id
		return Response.ok().entity(cliente).build();	
	}
	
	@POST
	public Response insertar(Cliente cliente) throws URISyntaxException {
		instancia.insertar(cliente);
		
		return Response.created(new URI ("/clientes/" + cliente.getId())).entity(cliente).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response borrar(@PathParam("id") Long id) {
		instancia.borrar(id);
		
		return Response.noContent().build();
	}
		
}
