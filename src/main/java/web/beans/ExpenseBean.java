package web.beans;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.RequestParameterValuesMap;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import web.entity.Expense;
import web.entity.User;
import web.lib.ChartModel;
import web.lib.PageRedirectManager;
import web.lib.SessionManage;
import web.pojos.UserSession;
import web.services.CategoryService;
import web.services.ExpenseService;
import web.services.UserService;

@Named
@ViewScoped
public class ExpenseBean implements Serializable{
	
	private final String urlPattern = "/expense";
	
	@Inject
	private ExpenseService service;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private UserService userService;
	
	private User currentUser;
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	private List<Expense> expenses = new ArrayList<>();
	
	public Expense getSelectedExpense() {
		return selectedExpense;
	}

	private Expense selectedExpense = new Expense();

	@PostConstruct
	public void init() {
		this.setCurrentUser(userService.getUserById(
				((UserSession)SessionManage.getSession("LOGIN_SESSION")).getId()
		));
		expenses = service.getAllExpensesInMonth();
	}
	
	public List<Expense> getExpenses() {
		return expenses;
	}
	
	public String setHistoryExpense() {
		expenses = service.getAllExpenses();
		return "";
	}
	
	public boolean isEdit() {
		return selectedExpense.getId() != null && selectedExpense.getId() != 0;
	}
	
	public void deleteExpense(int id) {
		String code = "";
		try {
			Expense expense = new Expense();
			expense.setId(id);
			expense.setUser(currentUser);
			service.deleteExpense(expense);

			code = "deletess";
		}catch(Exception e) {
			code = "deletefl";
		}
		PageRedirectManager.redirectTo(urlPattern + "/history/month?notify=" + code);
	}
	
	public String getExpenseDetail(int id) {
		this.selectedExpense = service.getExpenseById(id);
		return "";
	}
	
	public void editOrCreateExpense() {
		String code = "";
		
		selectedExpense.setCategory(categoryService.getCategoryByID(selectedExpense.getCategory().getId()));
		selectedExpense.setUser(currentUser);
		
		if(isEdit()) {
			try {
				service.editExpense(selectedExpense);
				code = "editss";
			}catch(Exception e) {
				System.out.println(e.getMessage());
				code = "editfl";
			}
		} else {		
			try {
				service.insertExpense(selectedExpense);
				code = "recss";
			}catch(Exception e) {
				code = "recfl";
			}
		}
		PageRedirectManager.redirectTo(urlPattern + "/history/month?notify=" + code);
	}
	
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public void setSelectedExpense(Expense selectedExpense) {
		this.selectedExpense = selectedExpense;
	}
	
	public String[] getLabelAnDataForChartOne() {
		return ChartModel.generateLabelAnDataToArrayForList(service.getSumOfExpenseMoneyEachNameInMonth());
	}
	
	public String[] getLabelAnDataForChartTwo() {
		return ChartModel.generateLabelAnDataToArrayForList(service.getSpendMoneyEachMonthInYear());
	}
	
	public String[] getLabelAnDataForChartThree() {
		return ChartModel.generateLabelAnDataToArrayForList(service.getSumOfExpenseMoneyInEachCategory());
	}

	public List<web.entity.Category> getAllCategory(){
		return categoryService.getAllCategory();
	}
	
	public void getAllExpenses() {
		this.expenses = service.getAllExpenses();
	}
	
	public double getTotalMoneyInMonth() {
		return service.getTotalMoneyInMonth();
	}
	
	public void setUserQuota() {
		String code = "";
		
		try {
			
			code = "limitsc";
			userService.updateUserQuota(currentUser);
			
		}catch(Exception e) {
			
			code = "limitfl";
		}
		PageRedirectManager.redirectTo(urlPattern + "/viewQuotaLimit?notify=" + code);
	}
	
}
