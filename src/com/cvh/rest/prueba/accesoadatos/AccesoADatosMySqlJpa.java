package com.cvh.rest.prueba.accesoadatos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cvh.rest.prueba.entidades.Cliente;

public class AccesoADatosMySqlJpa {

	private EntityManagerFactory emf;

	private AccesoADatosMySqlJpa() {
		emf = Persistence.createEntityManagerFactory("com.cvh.rest.prueba.entidades");
	}

	private static final AccesoADatosMySqlJpa INST = new AccesoADatosMySqlJpa();

	public static AccesoADatosMySqlJpa getInstance() {
		return INST;
	}

	public Cliente obtenerPorId(Long id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Cliente cliente = em.createQuery("from Cliente c where c.id=?1", Cliente.class). setParameter(1, id)
				.getSingleResult();
		em.getTransaction().commit();

		return cliente;
	}

	public Iterable<Cliente> obtenerTodos() {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = em.createNativeQuery("SELECT * FROM clientes", Cliente.class).getResultList();
		em.getTransaction().commit();

		return clientes;
	}

	public void insertar(Cliente cliente) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();	
		em.persist(cliente);
		em.getTransaction().commit();
	}

	// por id?
	public void modificar(Cliente cliente) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();	
		em.merge(cliente);
		em.getTransaction().commit();
	}

	public void borrar(Long id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Cliente cliente = getInstance().obtenerPorId(id);
		// TODO probar
		// https://stackoverflow.com/questions/17027398/java-lang-illegalargumentexception-removing-a-detached-instance-com-test-user5
		// entityManager.createQuery("delete from RootEntity r where r.id = :id").setParameter("id", id).executeUpdate();
		em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
		em.getTransaction().commit();
	}

}
