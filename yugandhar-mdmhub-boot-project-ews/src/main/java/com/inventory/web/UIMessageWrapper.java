package com.inventory.web;

import java.util.ArrayList;

public class UIMessageWrapper {
	private ArrayList<String> successMessageList;
	private ArrayList<String> errorMessageList;
	
	
	public UIMessageWrapper() {
		successMessageList = new ArrayList<String>();
		errorMessageList = new ArrayList<String>();
	}
	
	
	/**
	 * @return the successMessageList
	 */
	public ArrayList<String> getSuccessMessageList() {
		return successMessageList;
	}
	/**
	 * @param successMessageList the successMessageList to set
	 */
	public void setSuccessMessageList(ArrayList<String> successMessageList) {
		this.successMessageList = successMessageList;
	}
	/**
	 * @return the errorMessageList
	 */
	public ArrayList<String> getErrorMessageList() {
		return errorMessageList;
	}
	/**
	 * @param errorMessageList the errorMessageList to set
	 */
	public void setErrorMessageList(ArrayList<String> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}
	

}
