 package com.wizco.rest;

import java.text.ParseException;
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
import com.wizco.pojo.DespesaPojo;
import com.wizco.restUtil.UtilRest;
import com.wizco.service.ExpenseService;

@Path("/expense")
public class ExpenseRest extends UtilRest {

	@Inject
	private ExpenseService expenseService;
	private Gson gson = new Gson();
	
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response get(String expe) {
		try {
			
			DespesaPojo expenser = this.gson.fromJson(expe, DespesaPojo.class);				
			expenseService.save(expenser);
			
			return Response.ok(expe).build();
		}catch (Exception e) {
			e.printStackTrace();
			return getResponseError(e);
		}
	}
	
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<DespesaPojo> getAll() throws ParseException {
		
		return this.expenseService.list();
	} 
	
	@PUT
	@Produces("application/json; charset=UTF-8")
	public Response editar(String jsonUser) {
		try{
			
			DespesaPojo expenser = this.gson.fromJson(jsonUser, DespesaPojo.class);
			DespesaPojo result = expenseService.edit(expenser);
			
			if(!result.equals("")) {
				return getResponseEdit("Centro de Custo editado", result);
			}else {
				return getResponseValidate("Não foi possivel realizar edição verifique novamente os campos");
			}
		}catch(Exception e){
			e.printStackTrace();
			return getResponseError(e);
		}
	}
	
	@DELETE
	@Produces("application/json")
	public Response delete(@QueryParam("ids") List<String> ids) {
		try{
			this.expenseService.delete(ids);
			return getResponseRemove();
		} catch (Exception e) {
			return getResponseError(e);
		}
	}
}
