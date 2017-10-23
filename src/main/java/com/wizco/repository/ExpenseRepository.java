package com.wizco.repository;

import java.util.List;

import com.wizco.core.transaction.Managed;
import com.wizco.entity.Expense;

public class ExpenseRepository extends Managed {
	
	public Expense persist(Expense expe) {
		this.getEntityManager().persist(expe);
		return expe;
	}

	@SuppressWarnings("unchecked")
	public List<Expense> findAll() {
		
		return this.getEntityManager().createQuery("SELECT u FROM Expense u ORDER BY u.oid").getResultList();
	}
}
