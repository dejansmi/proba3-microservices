package com.dejans.proba3microservices;

import java.util.Date;

public class PaymentOrder {
	
	private String debtorIBAN; // max 34 chars
	private String creditorIBAN; // max 34 chars
	private int instructedAmount; // 
	private int instructedAmountDecimalPlaces; // 2 in many cases 
	private Date requestedExecutionDate;

	public PaymentOrder (String debtorIBAN, String creditorIBAN,
			int instructedAmount, int instructedAmountDecimalPlaces, 
			Date requestedExecutionDate) {

		this.debtorIBAN = debtorIBAN;
		this.creditorIBAN = creditorIBAN;
		this.instructedAmount = instructedAmount;
		this.instructedAmountDecimalPlaces = instructedAmountDecimalPlaces;
		this.requestedExecutionDate = requestedExecutionDate;
		
		
	}
	
	public String getDebtorIBAN () {
		return debtorIBAN;
	}

	public void setDebtorIBAN (String priv, int num) {
return;
	}
	
	public void setDebtorIBAN (String debtorIBAN) {
		this.debtorIBAN = debtorIBAN;
	}
	
	public String getCreditorIBAN () {
		return creditorIBAN;
	}
	
	public void setCreditorIBAN (String creditorIBAN) {
		this.creditorIBAN = creditorIBAN;
	}

	public int getInstructedAmount () {
		return instructedAmount;
	}
	
	public void setInstructedAmount (int instructedAmount) {
		this.instructedAmount = instructedAmount;
	}

	public int getInstructedAmountDecimalPlaces () {
		return instructedAmountDecimalPlaces;
	}
	
	public void setInstructedAmountDecimalPlaces (int instructedAmountDecimalPlaces) {
		this.instructedAmountDecimalPlaces = instructedAmountDecimalPlaces;
	}

	public Date getRequestedExecutionDate () {
		return requestedExecutionDate;
	}
	
	public void setRequestedExecutionDate (Date requestedExecutionDate) {
		this.requestedExecutionDate = requestedExecutionDate;
	}


}