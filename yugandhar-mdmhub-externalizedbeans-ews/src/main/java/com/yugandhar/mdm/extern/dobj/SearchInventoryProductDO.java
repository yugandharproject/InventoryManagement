package com.yugandhar.mdm.extern.dobj;

public class SearchInventoryProductDO {

	private String InventoryProductIdPk;
	private String productName;
	private String productId;
	private String productType;
	private String productCategory;
	
	//filter
	protected String inquiryFilter;
	protected String inquiryLevel;
	
	/**
	 * @return the inventoryProductIdPk
	 */
	public String getInventoryProductIdPk() {
		return InventoryProductIdPk;
	}
	/**
	 * @return the inquiryFilter
	 */
	public String getInquiryFilter() {
		return inquiryFilter;
	}
	/**
	 * @param inquiryFilter the inquiryFilter to set
	 */
	public void setInquiryFilter(String inquiryFilter) {
		this.inquiryFilter = inquiryFilter;
	}
	/**
	 * @return the inquiryLevel
	 */
	public String getInquiryLevel() {
		return inquiryLevel;
	}
	/**
	 * @param inquiryLevel the inquiryLevel to set
	 */
	public void setInquiryLevel(String inquiryLevel) {
		this.inquiryLevel = inquiryLevel;
	}
	/**
	 * @param inventoryProductIdPk the inventoryProductIdPk to set
	 */
	public void setInventoryProductIdPk(String inventoryProductIdPk) {
		InventoryProductIdPk = inventoryProductIdPk;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	
}
