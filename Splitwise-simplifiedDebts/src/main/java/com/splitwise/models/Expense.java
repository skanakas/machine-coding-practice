package com.splitwise.models;

import java.util.Set;

public class Expense {
	private int id;
	private String expenseName;
	private Set<PaySplit> creditors;
	private Set<PaySplit> debitors;
	
	public Expense(int id, String expenseName, Set<PaySplit> creditors, Set<PaySplit> debitors) {
		this.id = id;
		this.expenseName = expenseName;
		this.creditors = creditors;
		this.debitors = debitors;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public Set<PaySplit> getCreditors() {
		return creditors;
	}
	public void setCreditors(Set<PaySplit> creditors) {
		this.creditors = creditors;
	}
	public Set<PaySplit> getDebitors() {
		return debitors;
	}
	public void setDebitors(Set<PaySplit> debitors) {
		this.debitors = debitors;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creditors == null) ? 0 : creditors.hashCode());
		result = prime * result + ((debitors == null) ? 0 : debitors.hashCode());
		result = prime * result + ((expenseName == null) ? 0 : expenseName.hashCode());
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expense other = (Expense) obj;
		if (creditors == null) {
			if (other.creditors != null)
				return false;
		} else if (!creditors.equals(other.creditors))
			return false;
		if (debitors == null) {
			if (other.debitors != null)
				return false;
		} else if (!debitors.equals(other.debitors))
			return false;
		if (expenseName == null) {
			if (other.expenseName != null)
				return false;
		} else if (!expenseName.equals(other.expenseName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Expense [id=" + id + ", expenseName=" + expenseName + ", creditors=" + creditors + ", debitors="
				+ debitors + "]";
	}
	
}
