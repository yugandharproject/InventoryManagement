package com.inventory.component;
/* Generated 12 Oct, 2019 12:26:35 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.common.util.IgnoreAttributesUtil;
import com.yugandhar.mdm.component.util.ReferenceTableHelper;
import com.yugandhar.mdm.extern.dobj.InventorySummaryDO;
import com.yugandhar.mdm.keygen.YugandharKeygenerator;

/**
 * Component object for domain model InventorySummaryDO class
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see Documentation
*/

@Scope(value = "prototype")
@Component
public class InventorySummaryComponent {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	//  entityManager instance injection
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	InventorySummaryComponentRule theInventorySummaryComponentRule;

	@Autowired
	ReferenceTableHelper referenceTableHelper;
	
	@Autowired
	InventorySummaryRepository theInventorySummaryRepository;

	//default transaction response object
	TxnTransferObj respTxnTransferObj;

	// default constructor
	public InventorySummaryComponent() {
	}

	/**
	*This method creates a record in database. generates the idpk if not provided in the request and 
	*populate the updatedByUser, updatedByTxnId, createdTsString, updatedTsString attributes.
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with persisted instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or other mandatory attributes not present
	*
	*/

	public TxnTransferObj persist(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforePersist(txnTransferObj);
			theInventorySummaryComponentRule.prevalidateInventorySummaryCompPersit(txnTransferObj);
			// check if idpk is provided, else generate idpk with default UUID generator
			InventorySummaryDO reqInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
					.getInventorySummaryDO();
			if (null == reqInventorySummaryDO.getPrimaryKeyDO()
					|| null == reqInventorySummaryDO.getPrimaryKeyDO().getIdPk()) {
				logger.debug("Persist Method - pk Id in request is null, generating new id");
				YugandharKeygenerator yugandharKeygenerator = new YugandharKeygenerator();
				reqInventorySummaryDO.setIdPk(yugandharKeygenerator.generateKey());
			} else {
				reqInventorySummaryDO.setIdPk(reqInventorySummaryDO.getPrimaryKeyDO().getIdPk());
				InventorySummaryDO dbimageInventorySummaryDO = entityManager.find(InventorySummaryDO.class,
						reqInventorySummaryDO.getIdPk());
				if (null != dbimageInventorySummaryDO) {
					throw commonValidationUtil.createErrorResponse(txnTransferObj, "101",
							"InventorySummaryComponent.persist failed with Validation Exception");
					//Record already present for given Idpk
				}
			}
			theInventorySummaryComponentRule.preExecuteInventorySummaryCompPersist(reqInventorySummaryDO,
					txnTransferObj);
			entityManager.persist(reqInventorySummaryDO);
			entityManager.flush();

			reqInventorySummaryDO.setPrimaryKeyDO(null);
			respTxnTransferObj.getTxnPayload().setInventorySummaryDO(new InventorySummaryDO(reqInventorySummaryDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventorySummaryDO());
			theInventorySummaryComponentRule.postExecuteInventorySummaryCompPersit(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventorySummaryComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventorySummaryComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventorySummaryComponent.persist failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**This service updates the record in database. populate the updatedByUser, updatedByTxnId, updatedTsString attributes
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or mandatory attributes primary key is not present
	*/

	public TxnTransferObj merge(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		try {
			performCommonvalidationBeforeMerge(txnTransferObj);
			theInventorySummaryComponentRule.PrevalidateInventorySummaryCompMerge(txnTransferObj);
			InventorySummaryDO reqInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
					.getInventorySummaryDO();
			InventorySummaryDO dbimageInventorySummaryDO = entityManager.find(InventorySummaryDO.class,
					reqInventorySummaryDO.getIdPk());
			if (null == dbimageInventorySummaryDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventorySummaryComponent.merge failed with Validation Exception");
				//Record not found for given Idpk
			}

			IgnoreAttributesUtil ignoreAttributesUtil = new IgnoreAttributesUtil();
			String[] strIgnoreProperties = ignoreAttributesUtil.getAttributesToIgnore(reqInventorySummaryDO);
			BeanUtils.copyProperties(reqInventorySummaryDO, dbimageInventorySummaryDO, strIgnoreProperties);
			entityManager.detach(dbimageInventorySummaryDO);
			theInventorySummaryComponentRule.preExecuteInventorySummaryCompMerge(reqInventorySummaryDO,
					dbimageInventorySummaryDO, txnTransferObj);
			InventorySummaryDO mergedInventorySummaryDO = entityManager.merge(dbimageInventorySummaryDO);
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventorySummaryDO(new InventorySummaryDO(mergedInventorySummaryDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventorySummaryDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventorySummaryComponentRule.postExecuteInventorySummaryCompMerge(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (OptimisticLockException oe) {
			logger.error("merge failed with OptimisticLockException", oe);
			oe.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "2", oe,
					"OptimisticLockException in InventorySummaryComponent.merge");
			//OptimisticLockException- Row was updated or deleted by another transaction
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventorySummaryComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventorySummaryComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventorySummaryComponent.merge failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**
	* This method search the database record based on primary key. 
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or mandatory attributes primary key is not present
	*/
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindById(txnTransferObj);
			theInventorySummaryComponentRule.prevalidateInventorySummaryCompFindById(txnTransferObj);
			InventorySummaryDO reqInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
					.getInventorySummaryDO();
			InventorySummaryDO dbimageInventorySummaryDO = entityManager.find(InventorySummaryDO.class,
					reqInventorySummaryDO.getIdPk());
			if (null == dbimageInventorySummaryDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventorySummaryComponent.findbyId failed with Validation Exception");
				//Record not found
			}
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventorySummaryDO(new InventorySummaryDO(dbimageInventorySummaryDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventorySummaryDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventorySummaryComponentRule.postExecuteInventorySummaryCompFindById(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventorySummaryComponent.findById failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;

	}

	/**
	* perform the common validation before persisting InventorySummaryDO object in the database.
	* populate createdTimestamp, updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforePersist(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventorySummaryDO is needed in the request");
		}
		InventorySummaryDO theInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
				.getInventorySummaryDO();
		theInventorySummaryDO.setCreatedTs(new Date());
		theInventorySummaryDO.setUpdatedTs(new Date());
		theInventorySummaryDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventorySummaryDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation before updating InventorySummaryDO object in the database.
	* populate updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeMerge(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventorySummaryDO is needed in the request");
		}
		if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO().getIdPk()
				|| txnTransferObj.getTxnPayload().getInventorySummaryDO().getIdPk().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventorySummaryDO.idpk should not be null");
		}

		if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO().getVersion()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1010",
					"InventorySummaryDO.version should not be null");
		}

		InventorySummaryDO theInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
				.getInventorySummaryDO();
		theInventorySummaryDO.setUpdatedTs(new Date());
		theInventorySummaryDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventorySummaryDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation that InventorySummaryDO and idpk is not null
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventorySummaryDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeFindById(TxnTransferObj txnTransferObj) {

		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventorySummaryDO is needed in the request");
		}
		InventorySummaryDO theInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload()
				.getInventorySummaryDO();
		if (null == theInventorySummaryDO.getIdPk()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventorySummaryDO.idpk should not be null");
		}

	}

	//Implement this method to get the Key-Value pair of reference data attributes
	protected void populateReferenceDataAttributes(String requesterLanguage, InventorySummaryDO theInventorySummaryDO) {

		/*	// SAMPLEREFLOVRefkey
		if (!(null == theInventorySummaryDO.getSAMPLEREFLOVRefkey() || theInventorySummaryDO.getSAMPLEREFLOVRefkey().isEmpty())) {
			RefSAMPLEREFLOVDO theRefSAMPLEREFLOVDO = referenceTableHelper.getRefSAMPLEREFLOVValueByKey(
					requesterLanguage, theInventorySummaryDO.getSAMPLEREFLOVRefkey(),
					yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null != theRefSAMPLEREFLOVDO) {
				theInventorySummaryDO.setSAMPLEREFLOVRefValue(theRefSAMPLEREFLOVDO.getValue());
			}
		} */

	}
	
	
	public TxnTransferObj findByBusinessKey(TxnTransferObj txnTransferObj) { 
		respTxnTransferObj = new TxnTransferObj(); 
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 

	try { 

	performCommonvalidationBeforeFindByBusinessKey(txnTransferObj); 

	InventorySummaryDO reqInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload().getInventorySummaryDO(); 
	//theInventorySummaryComponentRule.preValidateInventorySummaryfindByBusinessKey(txnTransferObj); 
	//theInventorySummaryComponentRule.preExecuteInventorySummaryfindByBusinessKey(txnTransferObj); 

	InventorySummaryDO dbimageInventorySummaryDO = executeRepositoryQuery( 
			reqInventorySummaryDO.getLegalentityIdpk(), reqInventorySummaryDO.getProductId(), 
			reqInventorySummaryDO.getInquiryFilter()); 

	if (null == dbimageInventorySummaryDO) { 
		throw commonValidationUtil.createErrorResponse(txnTransferObj, "102", 
				"InventorySummaryComponent.findByBusinessKey search result have no records"); 
		// Record not found
	 } else { 

	dbimageInventorySummaryDO.setInquiryFilter(txnTransferObj.getTxnPayload().getInventorySummaryDO().getInquiryFilter()); 
	respTxnTransferObj.getTxnPayload().setInventorySummaryDO(new InventorySummaryDO (dbimageInventorySummaryDO)); 

	} 

	//theInventorySummaryComponentRule.postExecuteInventorySummaryfindByBusinessKey 
	respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS); 

	} catch (YugandharCommonException yce) {
		throw yce; 

	} catch (RuntimeException re) { 
		logger.error("RefInventorySummaryComponent findByBusinessKey failed", re); 
		re.printStackTrace(); throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re, 
				"RefInventorySummaryComponent.findByBusinessKey failed unexpectedly"); 
	}
	return respTxnTransferObj; 
	} 

	
	public InventorySummaryDO executeRepositoryQuery(String legalentityIdpk, String productId, String filter) { 
		InventorySummaryDO dbimageInventorySummaryDO = null; 
		if (filter.toUpperCase().equals("ACTIVE")) { 
			dbimageInventorySummaryDO = this.theInventorySummaryRepository.findByBusinessKeyActive (legalentityIdpk, productId);
		} else if (filter.toUpperCase().equals("INACTIVE")) { 
			dbimageInventorySummaryDO = this.theInventorySummaryRepository.findByBusinessKeyInactive (legalentityIdpk, productId); 
		} else if (filter.toUpperCase().equals("ALL")) { 
		dbimageInventorySummaryDO = this.theInventorySummaryRepository.findByBusinessKeyAll (legalentityIdpk, productId); 
	} 
		return dbimageInventorySummaryDO; 
	}

	private void performCommonvalidationBeforeFindByBusinessKey(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj); 
	
	if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO()) { 

	throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001", "InventorySummaryDO is needed in the request"); 
	}
	
	InventorySummaryDO theInventorySummaryDO = (InventorySummaryDO) txnTransferObj.getTxnPayload().getInventorySummaryDO(); 

	if (null == theInventorySummaryDO.getLegalentityIdpk() || theInventorySummaryDO.getLegalentityIdpk ().isEmpty()) {

	throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1006", "InventorySummaryDO.legalentityIdpk should not be null"); 
	} 
	if(null == theInventorySummaryDO.getProductId() || theInventorySummaryDO.getProductId().isEmpty()) { 
		throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1007", "InventorySummaryDo.productId should not be null"); 
		} 

	// if inquiry filter is null then consider the inquiry is for ACTIVE 
	// records 

	if (null == txnTransferObj.getTxnPayload().getInventorySummaryDO().getInquiryFilter() || 
			txnTransferObj.getTxnPayload().getInventorySummaryDO(). getInquiryFilter().isEmpty()) {

	txnTransferObj.getTxnPayload().getInventorySummaryDO().setInquiryFilter("ACTIVE"); 
	} else { 

	commonValidationUtil.validateFilterValue(txnTransferObj, txnTransferObj.getTxnPayload().getInventorySummaryDO().getInquiryFilter()); 
	} 
}

}
