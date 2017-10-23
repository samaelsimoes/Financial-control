package com.wizco.service;

import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wizco.core.transaction.Managed;
import com.wizco.core.transaction.Transacional;
import com.wizco.entity.CostCenter;
import com.wizco.entity.Expense;
import com.wizco.entity.Genre;
import com.wizco.entity.User;
import com.wizco.pojo.DespesaPojo;
import com.wizco.repository.ExpenseRepository;

@RequestScoped 
@Named
public class ExpenseService extends Managed {
	
	@Inject
	private ExpenseRepository expenseRepository;
	
	@Transacional
	public DespesaPojo save(DespesaPojo exp) {
		try {   
		 
		    Expense expense = pojoPersis(exp);
			this.getEntityManager().persist(expense);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return exp;
	}

	@Transacional
	public DespesaPojo edit(DespesaPojo expe) {
		try {
			
			Expense expense = pojoexpenseEdit(expe);
			
			if(!expense.equals("")) {
				this.getEntityManager().merge(expense);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expe;
	}
	
	@Transacional
	public void delete( List<String> ids) {
		try {
			for (String id : ids) {
				
				Expense finds = this.getEntityManager().find(Expense.class, id);
				this.getEntityManager().remove(finds);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transacional
	public List<DespesaPojo> list() throws ParseException {
		
		List<Expense> ex = this.expenseRepository.findAll();	
		List<DespesaPojo> pojo = pojoconsulta(ex);
		 
		return pojo;
	}
	
	private Expense pojoexpenseEdit(DespesaPojo exp) throws ParseException{
		
		Expense ex = new Expense();		
		CostCenter cs = new CostCenter();
		Genre ge = new Genre();
		User us = new User();
		
		Date date = new Date(exp.getDateMillis());
	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");

		ex.setDate(date);
		ex.setCode(exp.getCode());
		ex.setOid(exp.getOid());
		ex.setDescription(exp.getDescription());
		ex.setPaidValue(exp.getPaidValue());
		ex.setReason(exp.getReason());
		
		if (exp.getSituation().equals("0")) {	
			
			ex.setSituation("Aberto");
	    }else if (exp.getSituation().equals("1")) {
	    	
			ex.setSituation("Concluido");
	    }else if (exp.getSituation().equals("2")) {
	    	
	    	ex.setSituation("Aprovado");
	    }else if (exp.getSituation().equals("3")) {
	    	
	    	ex.setSituation("Pago");
	    }		
		
		ex.setValue(exp.getValue());
		cs.setOid(exp.getCostcenterid());
		ge.setOid(exp.getTypeid());
		us.setOid(exp.getUserid());
		ex.setCostCenter(cs);
		ex.setType(ge);
		ex.setUser(us);
		
		return ex;
	}
	
	private Expense pojoPersis(DespesaPojo exp) throws ParseException {
		Expense ex = new Expense();

		try {			
			CostCenter cs = new CostCenter();
			Genre ge = new Genre();
			User us = new User();
			
			Date date = new Date(exp.getDateMillis());
		    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		    
			ex.setDate(date);
			ex.setCode(exp.getCode());
			ex.setOid(ex.getOid());
			ex.setDescription(exp.getDescription());
			ex.setPaidValue(exp.getPaidValue());
			ex.setReason(exp.getReason());
			
			if (exp.getSituation().equals("0")) {	
				
				ex.setSituation("Aberto");
		    }else if (exp.getSituation().equals("1")) {
		    	
				ex.setSituation("Concluido");
		    }else if (exp.getSituation().equals("2")) {
		    	
		    	ex.setSituation("Aprovado");
		    }else if (exp.getSituation().equals("3")) {
		    	
		    	ex.setSituation("Pago");
		    }
			ex.setValue(exp.getValue());
	
			cs.setOid(exp.getCostecenterOid());
			ge.setOid(exp.getTypeOid());
			us.setOid(exp.getUserOid());
			
			ex.setCostCenter(cs);
			ex.setType(ge);
			ex.setUser(us);	

		}catch (Exception e) {
			e.printStackTrace();
		}
		return ex;
	}
	
	private List<DespesaPojo> pojoPersistEdit(List<Expense> ex) throws ParseException {
		
		List<DespesaPojo> list = new ArrayList<>();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = null;
		
		for (Expense expense : ex) {
			
			DespesaPojo pojoexp = new DespesaPojo();
			
			pojoexp.setCostecenterOid(expense.getCostCenter().getName());
			pojoexp.setCostcenterid(expense.getCostCenter().getOid());
			
			pojoexp.setTypeOid(expense.getType().getName());
			pojoexp.setTypeid(expense.getType().getOid());
			
			pojoexp.setUserOid(expense.getUser().getName());
			pojoexp.setUserid(expense.getUser().getOid());
			
			pojoexp.setOid(expense.getOid());
			pojoexp.setDateMillis(expense.getDate().getTime());
			
			Date date = new Date(pojoexp.getDateMillis());	
			dataFormatada = formatoDesejado.format(date);
			String d = String.valueOf(dataFormatada);
			
			pojoexp.setDate(d);
			
			pojoexp.setCode(expense.getCode());
			pojoexp.setDescription(expense.getDescription());
			pojoexp.setPaidValue(expense.getPaidValue());
			pojoexp.setReason(expense.getReason());
			
			if (expense.getSituation().equals("0")) {	
				
				pojoexp.setSituation("Aberto");
		    }else if (expense.getSituation().equals("1")) {
		    	
		    	pojoexp.setSituation("Concluido");
		    }else if (expense.getSituation().equals("2")) {
		    	
		    	pojoexp.setSituation("Aprovado");
		    }else if (expense.getSituation().equals("3")) {
		    	
		    	pojoexp.setSituation("Pago");
		    }

			pojoexp.setValue(expense.getValue());			
			list.add(pojoexp);
		}
		return list;
	}
	private List<DespesaPojo> pojoconsulta(List<Expense> ex) throws ParseException {
				
		List<DespesaPojo> list = new ArrayList<>();
		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = null;
		
		for (Expense expense : ex) {
			
			DespesaPojo pojoexp = new DespesaPojo();		
				
			pojoexp.setCostecenterOid(expense.getCostCenter().getName());
			pojoexp.setCostcenterid(expense.getCostCenter().getOid());			
			pojoexp.setTypeOid(expense.getType().getName());
			pojoexp.setTypeid(expense.getType().getOid());			
			pojoexp.setUserOid(expense.getUser().getName());
			pojoexp.setUserid(expense.getUser().getOid());			
			pojoexp.setOid(expense.getOid());
			pojoexp.setDateMillis(expense.getDate().getTime());			
			Date date = new Date(pojoexp.getDateMillis());	
			dataFormatada = formatoDesejado.format(date);
			String d = String.valueOf(dataFormatada);			
			pojoexp.setDate(d);
			pojoexp.setCode(expense.getCode());
			pojoexp.setDescription(expense.getDescription());
			pojoexp.setPaidValue(expense.getPaidValue());
			pojoexp.setReason(expense.getReason());
			
			if (expense.getSituation().equals("Aberto")) {	
				
				pojoexp.setSituation("0");
		    }else if (expense.getSituation().equals("Concluido")) {
		    	
		    	pojoexp.setSituation("1");
		    }else if (expense.getSituation().equals("Aprovado")) {
		    	
		    	pojoexp.setSituation("2");
		    }else if (expense.getSituation().equals("Pago")) {
		    	
		    	pojoexp.setSituation("3");
		    }

			pojoexp.setValue(expense.getValue());			
			list.add(pojoexp);
		}
		return list;
	}
}
