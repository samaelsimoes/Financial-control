package com.wizco.interfaces;

import java.util.List;

import com.wizco.entity.Genre;

public interface GenreInterface {
	
	public Genre addType(Genre type);
	
	public List<Genre> list();
	
	public void deleteType(int code);
	
	public Genre editExpense(Genre type);
	
	public List<Genre> searchByName(Genre type);

}
