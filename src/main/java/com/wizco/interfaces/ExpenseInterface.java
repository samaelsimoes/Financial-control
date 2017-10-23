package com.wizco.interfaces;

import java.util.List;

import com.wizco.entity.Expense;

public interface ExpenseInterface {
	
		public Expense addExpense(Expense exp);
		
		public List<Expense> list();
		
		public void deleteExpense(int code);
		
		public Expense editExpense(Expense exp);
		
		public List<Expense> searchByName(Expense exp);

}
