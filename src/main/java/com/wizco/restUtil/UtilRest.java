package com.wizco.restUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wizco.exception.PrincipalException;

public class UtilRest {
	
	@Context
	private HttpHeaders headers;
	protected RestResponse response;
	
	
	public ObjectMapper getObjectMapper() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		return new ObjectMapper()
				.setDateFormat(dateFormat)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.INDENT_OUTPUT, true)
				.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	public Response getResponseAdd(Object e) {
		return this.getResponse(new RestResponse(
				"Registro incluido com sucesso.", null, e),
				Response.Status.CREATED);
	}
	
	public Response getResponseAdd(String msg, Object e) {
		return this.getResponse(new RestResponse(msg, null, e), Response.Status.CREATED);
	}

	public Response getResponseEdit(Object data) {
		return this.getResponse(new RestResponse("Registro editado com suceso!", null, data), Response.Status.OK);
	}
	
	public Response getResponseEdit(String msg, Object data) {
		return this.getResponse(new RestResponse(msg, null, data), Response.Status.OK);
	}
	public Response getResponseValidate(String msg) {
		return this.getResponse(msg, Response.Status.BAD_REQUEST);
	}
	public Response getResponseRemove() {
		String msg = "Registro removido com sucesso.";
		
		if(this.response != null){
			if(this.response.getDescription() != null){
				msg = "Registro em uso!";
			}			
		}
		return this.getResponse(msg, Response.Status.OK);
	}

	public Response getResponseList(Object data) {
		return this.getResponse(new RestResponse(null, null, data),
				Response.Status.OK);
	}

	public Response getResponsePrivate() {
		return this
				.getResponse(
						new RestResponse(
								"Acesso negado!",
								"Esta funcionalidade não esta dísponivel para seu nível de acesso!",
								null), Response.Status.FORBIDDEN);
	}

	public Response getResponseUnauthorized(){
		return this
				.getResponse(
						new RestResponse(
								"Credenciais invalidas!",
								"Suas credenciais não são validas para este acesso!",
								null), Response.Status.UNAUTHORIZED);
	}
	
	public Response getResponseEditError() {
		return null;
	}
	
	public Response getResponseError(Exception e) {

		Response.Status status = null;
		if (e instanceof PrincipalException) {
			status = Response.Status.BAD_REQUEST;
		} else {
			status = Response.Status.INTERNAL_SERVER_ERROR;
		}

		return this.getResponse(new RestResponse("Erro na operação!", null, e), status);
	}

	private Response getResponse(Object obj, Response.Status status) {

		String json = null;
		try {
			json = this.getObjectMapper().writeValueAsString(obj);

			return Response.status(status).entity(json).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(null).build();
		}
	}

	public void filter(ContainerRequestContext requestContext) throws IOException{}
	
	protected List<Long> convertToList(JSONArray ids){
		List<Long> result = new ArrayList<Long>();
		
		for (int i = 0; i < ids.length(); i++) {
			result.add(ids.getLong(i));
		}
		
		return result;
	}
	protected List<String> convertToArray(String ids) {
		
		String newStr = ids.replace("[", "");
		newStr = newStr.replace("]", "");
		
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(newStr.split(",")));
		System.out.println(result);
		return result;
	}
	
}
