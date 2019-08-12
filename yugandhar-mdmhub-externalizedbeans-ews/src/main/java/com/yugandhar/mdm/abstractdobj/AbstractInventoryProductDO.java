package com.yugandhar.mdm.abstractdobj;
/* Generated 28 Jul, 2019 12:31:57 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.misc.dobj.PrimaryKeyDO;

/**
 * Abstract DO class for InventoryProductDO class mapped to database inventory_product entity
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see com.yugandhar.mdm.extern.dobj.InventoryProductDO
*/

@MappedSuperclass
public abstract class AbstractInventoryProductDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected String idPk;
	protected Integer version;
	protected Date createdTs;
	protected Date deletedTs;
	protected Date updatedTs;
	protected String updatedByUser;
	protected String updatedByTxnId;
	protected String productId;
	protected String supplierId;
	protected String productName;
	protected String productType;
	protected String productCategory;
	protected String hsnSacCode;
	protected String productStatus;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date productExpiryDate;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date productArrivalDate;
	protected Integer quantityRecevied;
	protected Integer quantityInhand;
	protected String unitOfMeasureRefKey;
	protected String unitOfMeasureRefValue;
	protected String billNumber;
	protected String inquiryFilter;
	protected PrimaryKeyDO primaryKeyDO;

	public AbstractInventoryProductDO() {
	}

	public AbstractInventoryProductDO(InventoryProductDO theInventoryProductDO) {
		idPk = theInventoryProductDO.idPk;
		version = theInventoryProductDO.version;
		createdTs = theInventoryProductDO.createdTs;
		deletedTs = theInventoryProductDO.deletedTs;
		updatedTs = theInventoryProductDO.updatedTs;
		updatedByUser = theInventoryProductDO.updatedByUser;
		updatedByTxnId = theInventoryProductDO.updatedByTxnId;
		productId = theInventoryProductDO.productId;
		supplierId = theInventoryProductDO.supplierId;
		productName = theInventoryProductDO.productName;
		productType = theInventoryProductDO.productType;
		productCategory = theInventoryProductDO.productCategory;
		hsnSacCode = theInventoryProductDO.hsnSacCode;
		productStatus = theInventoryProductDO.productStatus;
		productExpiryDate = theInventoryProductDO.productExpiryDate;
		productArrivalDate = theInventoryProductDO.productArrivalDate;
		quantityRecevied = theInventoryProductDO.quantityRecevied;
		quantityInhand = theInventoryProductDO.quantityInhand;
		unitOfMeasureRefKey = theInventoryProductDO.unitOfMeasureRefKey;
		billNumber = theInventoryProductDO.billNumber;
		inquiryFilter = theInventoryProductDO.inquiryFilter;
	}

	@Id

	@Column(name = "ID_PK", unique = true, nullable = false, length = 50)
	public String getIdPk() {
		return this.idPk;
	}

	public void setIdPk(String idPk) {
		this.idPk = idPk;
	}

	@Version
	@Column(name = "VERSION", nullable = false, precision = 22, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TS", nullable = false, length = 26)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_TS", length = 26)
	public Date getDeletedTs() {
		return this.deletedTs;
	}

	public void setDeletedTs(Date deletedTs) {
		this.deletedTs = deletedTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_TS", nullable = false, length = 26)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	@Column(name = "UPDATED_BY_USER", nullable = false, length = 50)
	public String getUpdatedByUser() {
		return this.updatedByUser;
	}

	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
	}

	@Column(name = "UPDATED_BY_TXN_ID", length = 100)
	public String getUpdatedByTxnId() {
		return this.updatedByTxnId;
	}

	public void setUpdatedByTxnId(String updatedByTxnId) {
		this.updatedByTxnId = updatedByTxnId;
	}

	@Column(name = "PRODUCT_ID", length = 50)
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "Supplier_ID", length = 50)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "Product_Name", length = 30)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "Product_Type", length = 50)
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name = "Product_Category", length = 50)
	public String getProductCategory() {
		return this.productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Column(name = "hsn_sac_code", length = 10)
	public String getHsnSacCode() {
		return this.hsnSacCode;
	}

	public void setHsnSacCode(String hsnSacCode) {
		this.hsnSacCode = hsnSacCode;
	}

	@Column(name = "Product_Status", length = 50)
	public String getProductStatus() {
		return this.productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Product_Expiry_Date", length = 26)
	public Date getProductExpiryDate() {
		return this.productExpiryDate;
	}

	public void setProductExpiryDate(Date productExpiryDate) {
		this.productExpiryDate = productExpiryDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Product_Arrival_Date", length = 26)
	public Date getProductArrivalDate() {
		return this.productArrivalDate;
	}

	public void setProductArrivalDate(Date productArrivalDate) {
		this.productArrivalDate = productArrivalDate;
	}

	@Column(name = "Quantity_Recevied", precision = 22, scale = 0)
	public Integer getQuantityRecevied() {
		return this.quantityRecevied;
	}

	public void setQuantityRecevied(Integer quantityRecevied) {
		this.quantityRecevied = quantityRecevied;
	}

	@Column(name = "Quantity_Inhand", precision = 22, scale = 0)
	public Integer getQuantityInhand() {
		return this.quantityInhand;
	}

	public void setQuantityInhand(Integer quantityInhand) {
		this.quantityInhand = quantityInhand;
	}

	@Column(name = "Quantity_Unitofmeasure", length = 50)
	public String getUnitOfMeasureRefKey() {
		return this.unitOfMeasureRefKey;
	}

	public void setUnitOfMeasureRefKey(String quantityUnitOfMeasure) {
		this.unitOfMeasureRefKey = quantityUnitOfMeasure;
	}
	
	

	/**
	 * @return the unitOfMeasureRefValue
	 */
	@Transient
	public String getUnitOfMeasureRefValue() {
		return unitOfMeasureRefValue;
	}

	/**
	 * @param unitOfMeasureRefValue the unitOfMeasureRefValue to set
	 */
	public void setUnitOfMeasureRefValue(String unitOfMeasureRefValue) {
		this.unitOfMeasureRefValue = unitOfMeasureRefValue;
	}

	
	@Column(name = "Bill_number", length = 100)
	public String getBillNumber() {
		return this.billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	/**
	 * @return the inquiryFilter
	 */
	@Transient
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
	* @return the primaryKeyDO
	*/
	@Transient
	public PrimaryKeyDO getPrimaryKeyDO() {
		return primaryKeyDO;
	}

	/**
	 * @param primaryKeyDO the primaryKeyDO to set
	 */
	public void setPrimaryKeyDO(PrimaryKeyDO primaryKeyDO) {
		this.primaryKeyDO = primaryKeyDO;
	}

	
	
}
