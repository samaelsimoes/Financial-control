package com.wizco.repository;

import java.util.List;

import com.wizco.core.transaction.Managed;
import com.wizco.entity.CostCenter;

public class CostCenterRepository extends Managed {
	
	public CostCenter persist(CostCenter costc) {
		this.getEntityManager().persist(costc);
		return costc;
	}

	@SuppressWarnings("unchecked")
	public List<CostCenter> findAll() {
		return this.getEntityManager().createQuery("SELECT u FROM CostCenter u ORDER BY u.name").getResultList();
	}

	public List findValidate(int code) {
		return this.getEntityManager().createQuery("SELECT u FROM CostCenter u where code = :code")
				.setParameter("code", code).getResultList();
	}
}
