package com.inventory.component;
/* Generated 27 Jul, 2019 11:49:54 PM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
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
import com.yugandhar.mdm.extern.dobj.InventoryOrderDO;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.keygen.YugandharKeygenerator;

/**
 * Component object for domain model InventoryOrderDO class
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see Documentation
*/

@Scope(value = "prototype")
@Component
public class InventoryOrderComponent {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	//  entityManager instance injection
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	InventoryOrderComponentRule theInventoryOrderComponentRule;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	//default transaction response object
	TxnTransferObj respTxnTransferObj;

	// default constructor
	public InventoryOrderComponent() {
	}

	/**
	*This method creates a record in database. generates the idpk if not provided in the request and 
	*populate the updatedByUser, updatedByTxnId, createdTsString, updatedTsString attributes.
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with persisted instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or other mandatory attributes not present
	*
	*/

	public TxnTransferObj persist(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforePersist(txnTransferObj);
			theInventoryOrderComponentRule.prevalidateInventoryOrderCompPersit(txnTransferObj);
			// check if idpk is provided, else generate idpk with default UUID generator
			InventoryOrderDO reqInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload()
					.getInventoryOrderDO();
			if (null == reqInventoryOrderDO.getPrimaryKeyDO()
					|| null == reqInventoryOrderDO.getPrimaryKeyDO().getIdPk()) {
				logger.debug("Persist Method - pk Id in request is null, generating new id");
				YugandharKeygenerator yugandharKeygenerator = new YugandharKeygenerator();
				reqInventoryOrderDO.setIdPk(yugandharKeygenerator.generateKey());
			} else {
				reqInventoryOrderDO.setIdPk(reqInventoryOrderDO.getPrimaryKeyDO().getIdPk());
				InventoryOrderDO dbimageInventoryOrderDO = entityManager.find(InventoryOrderDO.class,
						reqInventoryOrderDO.getIdPk());
				if (null != dbimageInventoryOrderDO) {
					throw commonValidationUtil.createErrorResponse(txnTransferObj, "101",
							"InventoryOrderComponent.persist failed with Validation Exception");
					//Record already present for given Idpk
				}
			}
			theInventoryOrderComponentRule.preExecuteInventoryOrderCompPersist(reqInventoryOrderDO, txnTransferObj);
			entityManager.persist(reqInventoryOrderDO);
			entityManager.flush();

			reqInventoryOrderDO.setPrimaryKeyDO(null);
			respTxnTransferObj.getTxnPayload().setInventoryOrderDO(new InventoryOrderDO(reqInventoryOrderDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryOrderDO());
			theInventoryOrderComponentRule.postExecuteInventoryOrderCompPersit(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryOrderComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryOrderComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryOrderComponent.persist failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**This service updates the record in database. populate the updatedByUser, updatedByTxnId, updatedTsString attributes
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes primary key is not present
	*/

	public TxnTransferObj merge(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		try {
			performCommonvalidationBeforeMerge(txnTransferObj);
			theInventoryOrderComponentRule.PrevalidateInventoryOrderCompMerge(txnTransferObj);
			InventoryOrderDO reqInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload()
					.getInventoryOrderDO();
			InventoryOrderDO dbimageInventoryOrderDO = entityManager.find(InventoryOrderDO.class,
					reqInventoryOrderDO.getIdPk());
			if (null == dbimageInventoryOrderDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryOrderComponent.merge failed with Validation Exception");
				//Record not found for given Idpk
			}

			IgnoreAttributesUtil ignoreAttributesUtil = new IgnoreAttributesUtil();
			String[] strIgnoreProperties = ignoreAttributesUtil.getAttributesToIgnore(reqInventoryOrderDO);
			BeanUtils.copyProperties(reqInventoryOrderDO, dbimageInventoryOrderDO, strIgnoreProperties);
			entityManager.detach(dbimageInventoryOrderDO);
			theInventoryOrderComponentRule.preExecuteInventoryOrderCompMerge(reqInventoryOrderDO,
					dbimageInventoryOrderDO, txnTransferObj);
			InventoryOrderDO mergedInventoryOrderDO = entityManager.merge(dbimageInventoryOrderDO);
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventoryOrderDO(new InventoryOrderDO(mergedInventoryOrderDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryOrderDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryOrderComponentRule.postExecuteInventoryOrderCompMerge(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (OptimisticLockException oe) {
			logger.error("merge failed with OptimisticLockException", oe);
			oe.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "2", oe,
					"OptimisticLockException in InventoryOrderComponent.merge");
			//OptimisticLockException- Row was updated or deleted by another transaction
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryOrderComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryOrderComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryOrderComponent.merge failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**
	* This method search the database record based on primary key. 
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes primary key is not present
	*/
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindById(txnTransferObj);
			theInventoryOrderComponentRule.prevalidateInventoryOrderCompFindById(txnTransferObj);
			InventoryOrderDO reqInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload()
					.getInventoryOrderDO();
			InventoryOrderDO dbimageInventoryOrderDO = entityManager.find(InventoryOrderDO.class,
					reqInventoryOrderDO.getIdPk());
			if (null == dbimageInventoryOrderDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryOrderComponent.findbyId failed with Validation Exception");
				//Record not found
			}
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventoryOrderDO(new InventoryOrderDO(dbimageInventoryOrderDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryOrderDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryOrderComponentRule.postExecuteInventoryOrderCompFindById(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryOrderComponent.findById failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;

	}

	/**
	* perform the common validation before persisting InventoryOrderDO object in the database.
	* populate createdTimestamp, updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforePersist(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryOrderDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryOrderDO is needed in the request");
		}
		InventoryOrderDO theInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload().getInventoryOrderDO();
		theInventoryOrderDO.setCreatedTs(new Date());
		theInventoryOrderDO.setUpdatedTs(new Date());
		theInventoryOrderDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryOrderDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation before updating InventoryOrderDO object in the database.
	* populate updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeMerge(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getInventoryOrderDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryOrderDO is needed in the request");
		}
		if (null == txnTransferObj.getTxnPayload().getInventoryOrderDO().getIdPk()
				|| txnTransferObj.getTxnPayload().getInventoryOrderDO().getIdPk().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryOrderDO.idpk should not be null");
		}

		if (null == txnTransferObj.getTxnPayload().getInventoryOrderDO().getVersion()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1010",
					"InventoryOrderDO.version should not be null");
		}

		InventoryOrderDO theInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload().getInventoryOrderDO();
		theInventoryOrderDO.setUpdatedTs(new Date());
		theInventoryOrderDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryOrderDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation that InventoryOrderDO and idpk is not null
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeFindById(TxnTransferObj txnTransferObj) {

		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryOrderDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryOrderDO is needed in the request");
		}
		InventoryOrderDO theInventoryOrderDO = (InventoryOrderDO) txnTransferObj.getTxnPayload().getInventoryOrderDO();
		if (null == theInventoryOrderDO.getIdPk()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryOrderDO.idpk should not be null");
		}

	}

	//Implement this method to get the Key-Value pair of reference data attributes
	protected void populateReferenceDataAttributes(String requesterLanguage, InventoryOrderDO theInventoryOrderDO) {

		/*	// SAMPLEREFLOVRefkey
		if (!(null == theInventoryOrderDO.getSAMPLEREFLOVRefkey() || theInventoryOrderDO.getSAMPLEREFLOVRefkey().isEmpty())) {
			RefSAMPLEREFLOVDO theRefSAMPLEREFLOVDO = referenceTableHelper.getRefSAMPLEREFLOVValueByKey(
					requesterLanguage, theInventoryOrderDO.getSAMPLEREFLOVRefkey(),
					yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null != theRefSAMPLEREFLOVDO) {
				theInventoryOrderDO.setSAMPLEREFLOVRefValue(theRefSAMPLEREFLOVDO.getValue());
			}
		} */

	}

}
