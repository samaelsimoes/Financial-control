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
import com.wizco.entity.Genre;
import com.wizco.restUtil.UtilRest;
import com.wizco.service.GenreService;

@Path("/genre")
public class GenreRest extends UtilRest {

	@Inject
	private GenreService genreService;
	private Gson gson = new Gson();

	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response get(String jsonUser) {
		try {
			
			Genre typeEx = this.gson.fromJson(jsonUser, Genre.class);
			List result = genreService.save(typeEx);
			if ( result.isEmpty() ) {
				return Response.ok(typeEx).build();
			}else {	
				return getResponseValidate("Não foi possivel cadastrar, motivo ja possui código cadastrado!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return getResponseError(e);
		}
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Genre> getAll() {

		List<Genre> typeExs = this.genreService.list();
		return typeExs;
	}

	@PUT
	@Produces("application/json")
	public Response editar(String jsonUser) {
		try {

			Genre typeEx = this.gson.fromJson(jsonUser, Genre.class);			
			Genre result = genreService.edit(typeEx);
						
			return getResponseEdit("Tipo de despesa cadastrada", result);
			
		} catch (Exception e) {			
			e.printStackTrace();
			return getResponseError(e);
		}
	}

	@DELETE
	@Produces("application/json")
	public Response delete(@QueryParam("ids") List<String> ids) {
		try {
			
			this.genreService.delete(ids);
			return getResponseRemove();
		} catch (Exception e) {

			e.printStackTrace();
			return getResponseError(e);
		}
	}
}
