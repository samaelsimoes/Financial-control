package com.wizco.interfaces;

import java.util.List;

import com.wizco.entity.CostCenter;

public interface CostCenterInterface {

    public CostCenter addCostCenter(CostCenter costc);
	
	public List<CostCenter> list();
	
	public void deleteCostCenter(int code);
	
	public CostCenter editCostCenter(CostCenter costc);
	
	public List<CostCenter> searchByName(CostCenter costc);
	
}
