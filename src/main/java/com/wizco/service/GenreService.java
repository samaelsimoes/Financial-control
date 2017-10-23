package com.wizco.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wizco.core.transaction.Managed;
import com.wizco.core.transaction.Transacional;
import com.wizco.entity.Genre;
import com.wizco.repository.GenreRepository;

@RequestScoped
@Named
public class GenreService extends Managed {

	@Inject
	private GenreRepository genreRepository;

	@Transacional
	public List save(Genre typeEx) {
		List resultVal = null;
		try {	
		
			resultVal = validate(typeEx.getCode());
			
			if ( resultVal.isEmpty() ) {
				this.getEntityManager().persist(typeEx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultVal;
	}

	@Transacional
	public Genre edit(Genre typeEx){
		try {

			this.getEntityManager().merge(typeEx);						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return typeEx;
	}

	@Transacional
	public void delete(List<String> ids) {
		try {
			for (String id : ids) {

				Genre finds = this.getEntityManager().find(Genre.class, id);
				this.getEntityManager().remove(finds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transacional
	public List<Genre> list() {
		return this.genreRepository.findAll();
	}
	
	@Transacional
	public List validate(int code){		
		return this.genreRepository.findValidate(code);
	}
}
