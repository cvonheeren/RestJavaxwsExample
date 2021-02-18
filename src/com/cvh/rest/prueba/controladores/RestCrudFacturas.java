package com.cvh.rest.prueba.controladores;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.cvh.rest.prueba.accesoadatos.FacturaDaoMysqlJpa;
import com.cvh.rest.prueba.entidades.Factura;


@Path("/facturas/")
@Consumes("application/json")
@Produces("application/json")
public class RestCrudFacturas {

	FacturaDaoMysqlJpa instancia;
	
	public RestCrudFacturas() {
		instancia = FacturaDaoMysqlJpa.getInstance();
	}
	
	@GET
	@Path("{id}")
	public Response mostrarPorId(@PathParam("id") Long id) {		
		return Response.ok().entity(instancia.obtenerPorId(id)).build();
	}
	
	@POST
	public Response insertar(Factura factura) throws URISyntaxException {
		instancia.insertar(factura);
		
		return Response.created(new URI ("/factura/" + factura.getId())).entity(factura).build();
	}
	
}
