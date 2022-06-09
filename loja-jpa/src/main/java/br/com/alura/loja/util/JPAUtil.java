package br.com.alura.loja.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory FACTOTY = Persistence.createEntityManagerFactory("loja-jpa");
	
	public static EntityManager getEntityManager() {
		return FACTOTY.createEntityManager();
	}
}
