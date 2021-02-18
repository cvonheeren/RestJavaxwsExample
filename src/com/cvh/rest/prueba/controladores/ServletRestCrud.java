package com.cvh.rest.prueba.controladores;


import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cvh.rest.prueba.entidades.Cliente;
import com.google.gson.Gson;


@WebServlet({"/api/clientes", "/api/clientes/*"})
public class ServletRestCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static TreeMap<Long, Cliente> clientes = new TreeMap<>();
	
	static {
		clientes.put(1L, new Cliente(1L, "Javier", "Lete"));
		clientes.put(2L, new Cliente(2L, "Pepe", "Pérez"));
		clientes.put(3L, new Cliente(3L, "Juan", "Gómez"));
	}
	
	private static Gson gson = new Gson();

	/**
	 *  MOSTRAR TODOS ··· MOSTRAR POR ID
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Long id = obtenerId(request);
			
		if (id == null) {
			response.getWriter().write(gson.toJson(clientes.values()));
		} else {
			try {
				response.getWriter().write(gson.toJson(clientes.get(id)));
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
	
	/**
	 *  INSERTAR
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Cliente cliente = gson.fromJson(request.getReader(), Cliente.class);
		
		cliente.setId(clientes.lastKey() + 1);	
		clientes.put(cliente.getId(), cliente);
		
		response.setStatus(HttpServletResponse.SC_CREATED);	
		response.getWriter().write(gson.toJson(cliente));
	}
	
	/**
	 *  MODIFICAR
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Long id = obtenerId(request);
		Cliente cliente = gson.fromJson(request.getReader(), Cliente.class);
		
		clientes.put(id, cliente);
		
		response.getWriter().write(gson.toJson(cliente));
	}

	/**
	 *  BORRAR
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = obtenerId(request);
		
		try {
			clientes.remove(id);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	
	
	
	
	// ----------------------------------------
	
	private Long obtenerId(HttpServletRequest request) {
		String sId = request.getPathInfo();
			
		if (sId != null && !"/".equals(sId)) {
			return Long.parseLong(sId.substring(1));
		}
		
		return null;
	}
}
