package com.wizco.repository;

import java.util.List;

import com.wizco.core.transaction.Managed;
import com.wizco.entity.Genre;

public class GenreRepository extends Managed {
	
	public Genre persist(Genre typeEx) {
		this.getEntityManager().persist(typeEx);
		return typeEx;
	}

	@SuppressWarnings("unchecked")
	public List<Genre> findAll() {
		return this.getEntityManager().createQuery("SELECT u FROM Genre u ORDER BY u.name").getResultList();
	}

	public List findValidate(int code) {
		return this.getEntityManager().createQuery("SELECT u FROM Genre u where code = :code")
				.setParameter("code", code).getResultList();
	}
}
