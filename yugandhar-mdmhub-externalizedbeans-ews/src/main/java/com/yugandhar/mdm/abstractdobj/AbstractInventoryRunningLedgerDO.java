package com.yugandhar.mdm.abstractdobj;
/* Generated 12 Oct, 2019 12:26:33 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.yugandhar.mdm.misc.dobj.PrimaryKeyDO;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;

/**
 * Abstract DO class for InventoryRunningLedgerDO class mapped to database inventory_running_ledger entity
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see com.yugandhar.com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO
*/

@MappedSuperclass
public abstract class AbstractInventoryRunningLedgerDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected String idPk;
	protected Integer version;
	protected Date createdTs;
	protected Date deletedTs;
	protected Date updatedTs;
	protected String updatedByUser;
	protected String updatedByTxnId;
	protected String legalentityIdpk;
	protected String productId;
	protected String transactionType;
	protected Integer transactionQuantity;
	protected String inquiryFilter;
	protected PrimaryKeyDO primaryKeyDO;
	//custom fields
	protected String productname;
	protected String displayname;

	public AbstractInventoryRunningLedgerDO() {
	}

	public AbstractInventoryRunningLedgerDO(InventoryRunningLedgerDO theInventoryRunningLedgerDO) {
		idPk = theInventoryRunningLedgerDO.idPk;
		version = theInventoryRunningLedgerDO.version;
		createdTs = theInventoryRunningLedgerDO.createdTs;
		deletedTs = theInventoryRunningLedgerDO.deletedTs;
		updatedTs = theInventoryRunningLedgerDO.updatedTs;
		updatedByUser = theInventoryRunningLedgerDO.updatedByUser;
		updatedByTxnId = theInventoryRunningLedgerDO.updatedByTxnId;
		legalentityIdpk = theInventoryRunningLedgerDO.legalentityIdpk;
		productId = theInventoryRunningLedgerDO.productId;
		transactionType = theInventoryRunningLedgerDO.transactionType;
		transactionQuantity = theInventoryRunningLedgerDO.transactionQuantity;
		inquiryFilter = theInventoryRunningLedgerDO.inquiryFilter;
		productname = theInventoryRunningLedgerDO.productname;
		displayname = theInventoryRunningLedgerDO.displayname;
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

	@Column(name = "LEGALENTITY_IDPK", nullable = false, length = 50)
	public String getLegalentityIdpk() {
		return this.legalentityIdpk;
	}

	public void setLegalentityIdpk(String legalentityIdpk) {
		this.legalentityIdpk = legalentityIdpk;
	}

	@Column(name = "PRODUCT_ID", nullable = false, length = 50)
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "TRANSACTION_TYPE", nullable = false, length = 50)
	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionTye) {
		this.transactionType = transactionTye;
	}

	@Column(name = "TRANSACTION_QUANTITY", nullable = false, precision = 22, scale = 0)
	public Integer getTransactionQuantity() {
		return this.transactionQuantity;
	}

	public void setTransactionQuantity(Integer transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
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

	/**
	 * @return the productname
	 */
	@Transient
	public String getProductname() {
		return productname;
	}

	/**
	 * @param productname the productname to set
	 */
	
	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * @return the displayname
	 */
	@Transient
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * @param dislayname the dislayname to set
	 */
	public void setDisplayname(String dislayname) {
		this.displayname = displayname;
	}

	
}
