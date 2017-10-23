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
import com.wizco.entity.User;
import com.wizco.restUtil.UtilRest;
import com.wizco.service.UserService;

@Path("/user")
public class UserRest extends UtilRest {

	@Inject
	private UserService userService;
	private Gson gson = new Gson();
	
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response get(String jsonUser) {
		try {
			
			User user = this.gson.fromJson(jsonUser, User.class);
			List result = userService.save(user);			
			
			if( result.isEmpty()) {
				return Response.ok(user).build();
			}else {
				return getResponseValidate("Não foi possivel cadastrar, motivo ja possui usuário cadastrado no sistema!");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return getResponseError(e);
		}
	}
	
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<User> getAll() {
		return this.userService.list();
	} 
	
	@PUT
	@Produces("application/json")
	public Response editar(String jsonUser) {
		try{
			
			User user = this.gson.fromJson(jsonUser, User.class);		
			boolean result = this.userService.edit(user);

			if(result){
				return getResponseEdit("Usuário Editado com sucesso!", result);
			}else {
				return getResponseValidate("Não foi possivel editar, motivo ja possui usuário cadastrado no sistema!");			
			}
		}catch(Exception e){
			return getResponseError(e);
		}
	}
	
	@DELETE
	@Produces("application/json")
	public Response delete(@QueryParam("ids") List<String> ids) {
		Boolean retorno;
		try{
			retorno = this.userService.delete(ids);
			if ( retorno == false ) {
				return getResponseRemove();
			}else {
				return getResponseValidate("Não foi possivel excluir esse usuário,  motivo: possui registro vinculados em despesa!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResponseError(e);
		}
	}
}
