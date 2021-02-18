package com.cvh.rest.prueba.accesoadatos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cvh.rest.prueba.entidades.Factura;

public class FacturaDaoMysqlJpa {

	private EntityManagerFactory emf;

	private FacturaDaoMysqlJpa() {
		emf = Persistence.createEntityManagerFactory("com.cvh.rest.prueba.entidades");
	}

	private static final FacturaDaoMysqlJpa INST = new FacturaDaoMysqlJpa();

	public static FacturaDaoMysqlJpa getInstance() {
		return INST;
	}
	
	public Factura obtenerPorId(Long id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Factura factura = em.createQuery("from Factura c where c.id=?1", Factura.class). setParameter(1, id)
				.getSingleResult();
		em.getTransaction().commit();

		return factura;
	}
	
	public void insertar(Factura factura) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();	
		em.persist(factura);
		em.getTransaction().commit();
	}
	
}
