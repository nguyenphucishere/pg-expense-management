package web.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import web.services.CategoryService;

@Entity
@Table(name="expenses")
public class Expense {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	//private Integer categoryID;
	
	private float value;
	
	//private Integer userID;
	
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "categoryID")
	private Category category = new Category();
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;
	
//	public Integer getCategoryID() {
//		return categoryID;
//	}
//
//	public void setCategoryID(Integer categoryID) {
//		this.categoryID = categoryID;
//	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User users) {
		this.user = users;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "created_date")
	private Date created_date = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

//	public Integer getUserID() {
//		return userID;
//	}
//
//	public void setUserID(Integer userID) {
//		this.userID = userID;
//	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
}
