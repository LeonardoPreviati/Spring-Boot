package br.com.empreendedorismo.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

public class HibernateConfiguration {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	protected EntityManager entityManager() {
		return entityManager;
	}
		
	protected Session getSession() {
		return (Session) entityManager().getDelegate();
	}
	
	

}
