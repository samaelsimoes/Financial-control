package com.wizco.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.wizco.entity.CostCenter;
import com.wizco.restUtil.UtilRest;
import com.wizco.service.CostCenterService;

@Path("/costcenter")
public class CostCenterRest extends UtilRest {

	@Inject
	private CostCenterService costCenterService;
	private Gson gson = new Gson();

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response get(String jsonUser) {
		try {
			
			CostCenter costc = this.gson.fromJson(jsonUser, CostCenter.class);
			List result = costCenterService.save(costc);
			
			if ( result.isEmpty() ) {
				return Response.ok(costc).build();
			}else {
				return getResponseValidate("Não foi possivel realizar cadastro,"
						+ "  motivo: ja possui código cadastrado!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return getResponseError(e);
		}
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<CostCenter> getAll() {
		List<CostCenter> costCenters = this.costCenterService.list();
		return costCenters;
	} 
	
	@PUT
	@Produces("application/json")
	public Response editar(String jsonUser) {

		try{
			CostCenter costc = this.gson.fromJson(jsonUser, CostCenter.class);		
			Object result = costCenterService.edit(costc);
			
			if(result == null){
				return getResponseEdit("Centro de Custo cadastrado", result);
			}else{
				return getResponseEdit(result);				
			}
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@DELETE
	@Produces("application/json")
	public Response delete(@QueryParam("ids") List<String> ids) {
		try{
			
			this.costCenterService.delete(ids);
			return getResponseRemove();
		} catch (Exception e) {
			
			e.printStackTrace();
			return getResponseError(e);
		}
	}

}
