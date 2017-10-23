package com.wizco.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wizco.core.transaction.Managed;
import com.wizco.core.transaction.Transacional;
import com.wizco.entity.CostCenter;
import com.wizco.repository.CostCenterRepository;

@RequestScoped 
@Named
public class CostCenterService extends Managed {
	
	@Inject
	private CostCenterRepository costCenterRepository;
	
	@Transacional
	public List save(CostCenter costc) {
		
		List val = null ;
		
		try {
			val = validate(costc.getCode());
			
			if (val.isEmpty() ) {
				this.getEntityManager().persist(costc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return val;
	}
	
	
	@Transacional
	public CostCenter edit(CostCenter costc) {
		try {
			this.getEntityManager().merge(costc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return costc;
	}
	
	
	@Transacional
	public void delete( List<String> ids) {
		try {
			for (String id : ids) {
				
				CostCenter finds = this.getEntityManager().find(CostCenter.class, id);
				this.getEntityManager().remove(finds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transacional
	public List<CostCenter> list() {
		return this.costCenterRepository.findAll();
	}
	@Transacional
	public List validate(int code){		
		return this.costCenterRepository.findValidate(code);
	}

}
