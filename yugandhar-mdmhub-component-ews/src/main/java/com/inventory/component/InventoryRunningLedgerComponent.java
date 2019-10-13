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
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.util.IgnoreAttributesUtil;
import com.yugandhar.mdm.component.util.ReferenceTableHelper;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.keygen.YugandharKeygenerator;

/**
 * Component object for domain model InventoryRunningLedgerDO class
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see Documentation
*/

@Scope(value = "prototype")
@Component
public class InventoryRunningLedgerComponent {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	//  entityManager instance injection
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	InventoryRunningLedgerComponentRule theInventoryRunningLedgerComponentRule;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	//default transaction response object
	TxnTransferObj respTxnTransferObj;

	// default constructor
	public InventoryRunningLedgerComponent() {
	}

	/**
	*This method creates a record in database. generates the idpk if not provided in the request and 
	*populate the updatedByUser, updatedByTxnId, createdTsString, updatedTsString attributes.
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with persisted instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or other mandatory attributes not present
	*
	*/

	public TxnTransferObj persist(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforePersist(txnTransferObj);
			theInventoryRunningLedgerComponentRule.prevalidateInventoryRunningLedgerCompPersit(txnTransferObj);
			// check if idpk is provided, else generate idpk with default UUID generator
			InventoryRunningLedgerDO reqInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj
					.getTxnPayload().getInventoryRunningLedgerDO();
			if (null == reqInventoryRunningLedgerDO.getPrimaryKeyDO()
					|| null == reqInventoryRunningLedgerDO.getPrimaryKeyDO().getIdPk()) {
				logger.debug("Persist Method - pk Id in request is null, generating new id");
				YugandharKeygenerator yugandharKeygenerator = new YugandharKeygenerator();
				reqInventoryRunningLedgerDO.setIdPk(yugandharKeygenerator.generateKey());
			} else {
				reqInventoryRunningLedgerDO.setIdPk(reqInventoryRunningLedgerDO.getPrimaryKeyDO().getIdPk());
				InventoryRunningLedgerDO dbimageInventoryRunningLedgerDO = entityManager
						.find(InventoryRunningLedgerDO.class, reqInventoryRunningLedgerDO.getIdPk());
				if (null != dbimageInventoryRunningLedgerDO) {
					throw commonValidationUtil.createErrorResponse(txnTransferObj, "101",
							"InventoryRunningLedgerComponent.persist failed with Validation Exception");
					//Record already present for given Idpk
				}
			}
			theInventoryRunningLedgerComponentRule
					.preExecuteInventoryRunningLedgerCompPersist(reqInventoryRunningLedgerDO, txnTransferObj);
			entityManager.persist(reqInventoryRunningLedgerDO);
			entityManager.flush();

			reqInventoryRunningLedgerDO.setPrimaryKeyDO(null);
			respTxnTransferObj.getTxnPayload()
					.setInventoryRunningLedgerDO(new InventoryRunningLedgerDO(reqInventoryRunningLedgerDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDO());
			theInventoryRunningLedgerComponentRule.postExecuteInventoryRunningLedgerCompPersit(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryRunningLedgerComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryRunningLedgerComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryRunningLedgerComponent.persist failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**This service updates the record in database. populate the updatedByUser, updatedByTxnId, updatedTsString attributes
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or mandatory attributes primary key is not present
	*/

	public TxnTransferObj merge(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		try {
			performCommonvalidationBeforeMerge(txnTransferObj);
			theInventoryRunningLedgerComponentRule.PrevalidateInventoryRunningLedgerCompMerge(txnTransferObj);
			InventoryRunningLedgerDO reqInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj
					.getTxnPayload().getInventoryRunningLedgerDO();
			InventoryRunningLedgerDO dbimageInventoryRunningLedgerDO = entityManager
					.find(InventoryRunningLedgerDO.class, reqInventoryRunningLedgerDO.getIdPk());
			if (null == dbimageInventoryRunningLedgerDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryRunningLedgerComponent.merge failed with Validation Exception");
				//Record not found for given Idpk
			}

			IgnoreAttributesUtil ignoreAttributesUtil = new IgnoreAttributesUtil();
			String[] strIgnoreProperties = ignoreAttributesUtil.getAttributesToIgnore(reqInventoryRunningLedgerDO);
			BeanUtils.copyProperties(reqInventoryRunningLedgerDO, dbimageInventoryRunningLedgerDO, strIgnoreProperties);
			entityManager.detach(dbimageInventoryRunningLedgerDO);
			theInventoryRunningLedgerComponentRule.preExecuteInventoryRunningLedgerCompMerge(
					reqInventoryRunningLedgerDO, dbimageInventoryRunningLedgerDO, txnTransferObj);
			InventoryRunningLedgerDO mergedInventoryRunningLedgerDO = entityManager
					.merge(dbimageInventoryRunningLedgerDO);
			entityManager.flush();
			respTxnTransferObj.getTxnPayload()
					.setInventoryRunningLedgerDO(new InventoryRunningLedgerDO(mergedInventoryRunningLedgerDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryRunningLedgerComponentRule.postExecuteInventoryRunningLedgerCompMerge(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (OptimisticLockException oe) {
			logger.error("merge failed with OptimisticLockException", oe);
			oe.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "2", oe,
					"OptimisticLockException in InventoryRunningLedgerComponent.merge");
			//OptimisticLockException- Row was updated or deleted by another transaction
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryRunningLedgerComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryRunningLedgerComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryRunningLedgerComponent.merge failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**
	* This method search the database record based on primary key. 
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or mandatory attributes primary key is not present
	*/
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindById(txnTransferObj);
			theInventoryRunningLedgerComponentRule.prevalidateInventoryRunningLedgerCompFindById(txnTransferObj);
			InventoryRunningLedgerDO reqInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj
					.getTxnPayload().getInventoryRunningLedgerDO();
			InventoryRunningLedgerDO dbimageInventoryRunningLedgerDO = entityManager
					.find(InventoryRunningLedgerDO.class, reqInventoryRunningLedgerDO.getIdPk());
			if (null == dbimageInventoryRunningLedgerDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryRunningLedgerComponent.findbyId failed with Validation Exception");
				//Record not found
			}
			entityManager.flush();
			respTxnTransferObj.getTxnPayload()
					.setInventoryRunningLedgerDO(new InventoryRunningLedgerDO(dbimageInventoryRunningLedgerDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryRunningLedgerComponentRule.postExecuteInventoryRunningLedgerCompFindById(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryRunningLedgerComponent.findById failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;

	}

	/**
	* perform the common validation before persisting InventoryRunningLedgerDO object in the database.
	* populate createdTimestamp, updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforePersist(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryRunningLedgerDO is needed in the request");
		}
		InventoryRunningLedgerDO theInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj.getTxnPayload()
				.getInventoryRunningLedgerDO();
		theInventoryRunningLedgerDO.setCreatedTs(new Date());
		theInventoryRunningLedgerDO.setUpdatedTs(new Date());
		theInventoryRunningLedgerDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryRunningLedgerDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation before updating InventoryRunningLedgerDO object in the database.
	* populate updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeMerge(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryRunningLedgerDO is needed in the request");
		}
		if (null == txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO().getIdPk()
				|| txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO().getIdPk().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryRunningLedgerDO.idpk should not be null");
		}

		if (null == txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO().getVersion()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1010",
					"InventoryRunningLedgerDO.version should not be null");
		}

		InventoryRunningLedgerDO theInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj.getTxnPayload()
				.getInventoryRunningLedgerDO();
		theInventoryRunningLedgerDO.setUpdatedTs(new Date());
		theInventoryRunningLedgerDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryRunningLedgerDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation that InventoryRunningLedgerDO and idpk is not null
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryRunningLedgerDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeFindById(TxnTransferObj txnTransferObj) {

		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryRunningLedgerDO is needed in the request");
		}
		InventoryRunningLedgerDO theInventoryRunningLedgerDO = (InventoryRunningLedgerDO) txnTransferObj.getTxnPayload()
				.getInventoryRunningLedgerDO();
		if (null == theInventoryRunningLedgerDO.getIdPk()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryRunningLedgerDO.idpk should not be null");
		}

	}

	//Implement this method to get the Key-Value pair of reference data attributes
	protected void populateReferenceDataAttributes(String requesterLanguage,
			InventoryRunningLedgerDO theInventoryRunningLedgerDO) {

		/*	// SAMPLEREFLOVRefkey
		if (!(null == theInventoryRunningLedgerDO.getSAMPLEREFLOVRefkey() || theInventoryRunningLedgerDO.getSAMPLEREFLOVRefkey().isEmpty())) {
			RefSAMPLEREFLOVDO theRefSAMPLEREFLOVDO = referenceTableHelper.getRefSAMPLEREFLOVValueByKey(
					requesterLanguage, theInventoryRunningLedgerDO.getSAMPLEREFLOVRefkey(),
					yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null != theRefSAMPLEREFLOVDO) {
				theInventoryRunningLedgerDO.setSAMPLEREFLOVRefValue(theRefSAMPLEREFLOVDO.getValue());
			}
		} */

	}

}
