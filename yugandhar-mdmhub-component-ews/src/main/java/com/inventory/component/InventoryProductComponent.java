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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.common.util.IgnoreAttributesUtil;
import com.yugandhar.mdm.component.util.ReferenceTableHelper;
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.keygen.YugandharKeygenerator;

/**
 * Component object for domain model InventoryProductDO class
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see Documentation
*/

@Scope(value = "prototype")
@Component
public class InventoryProductComponent {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	//  entityManager instance injection
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	InventoryProductComponentRule theInventoryProductComponentRule;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	//default transaction response object
	TxnTransferObj respTxnTransferObj;

	// default constructor
	public InventoryProductComponent() {
	}

	/**
	*This method creates a record in database. generates the idpk if not provided in the request and 
	*populate the updatedByUser, updatedByTxnId, createdTsString, updatedTsString attributes.
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with persisted instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or other mandatory attributes not present
	*
	*/

	public TxnTransferObj persist(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforePersist(txnTransferObj);
			theInventoryProductComponentRule.prevalidateInventoryProductCompPersit(txnTransferObj);
			// check if idpk is provided, else generate idpk with default UUID generator
			InventoryProductDO reqInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
					.getInventoryProductDO();
			if (null == reqInventoryProductDO.getPrimaryKeyDO()
					|| null == reqInventoryProductDO.getPrimaryKeyDO().getIdPk()) {
				logger.debug("Persist Method - pk Id in request is null, generating new id");
				YugandharKeygenerator yugandharKeygenerator = new YugandharKeygenerator();
				reqInventoryProductDO.setIdPk(yugandharKeygenerator.generateKey());
			} else {
				reqInventoryProductDO.setIdPk(reqInventoryProductDO.getPrimaryKeyDO().getIdPk());
				InventoryProductDO dbimageInventoryProductDO = entityManager.find(InventoryProductDO.class,
						reqInventoryProductDO.getIdPk());
				if (null != dbimageInventoryProductDO) {
					throw commonValidationUtil.createErrorResponse(txnTransferObj, "101",
							"InventoryProductComponent.persist failed with Validation Exception");
					//Record already present for given Idpk
				}
			}
			theInventoryProductComponentRule.preExecuteInventoryProductCompPersist(reqInventoryProductDO,
					txnTransferObj);
			entityManager.persist(reqInventoryProductDO);
			entityManager.flush();

			reqInventoryProductDO.setPrimaryKeyDO(null);
			respTxnTransferObj.getTxnPayload().setInventoryProductDO(new InventoryProductDO(reqInventoryProductDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryProductDO());
			theInventoryProductComponentRule.postExecuteInventoryProductCompPersit(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryProductComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryProductComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryProductComponent.persist failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**This service updates the record in database. populate the updatedByUser, updatedByTxnId, updatedTsString attributes
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or mandatory attributes primary key is not present
	*/

	public TxnTransferObj merge(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		try {
			performCommonvalidationBeforeMerge(txnTransferObj);
			theInventoryProductComponentRule.PrevalidateInventoryProductCompMerge(txnTransferObj);
			InventoryProductDO reqInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
					.getInventoryProductDO();
			InventoryProductDO dbimageInventoryProductDO = entityManager.find(InventoryProductDO.class,
					reqInventoryProductDO.getIdPk());
			if (null == dbimageInventoryProductDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryProductComponent.merge failed with Validation Exception");
				//Record not found for given Idpk
			}

			IgnoreAttributesUtil ignoreAttributesUtil = new IgnoreAttributesUtil();
			String[] strIgnoreProperties = ignoreAttributesUtil.getAttributesToIgnore(reqInventoryProductDO);
			BeanUtils.copyProperties(reqInventoryProductDO, dbimageInventoryProductDO, strIgnoreProperties);
			entityManager.detach(dbimageInventoryProductDO);
			theInventoryProductComponentRule.preExecuteInventoryProductCompMerge(reqInventoryProductDO,
					dbimageInventoryProductDO, txnTransferObj);
			InventoryProductDO mergedInventoryProductDO = entityManager.merge(dbimageInventoryProductDO);
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventoryProductDO(new InventoryProductDO(mergedInventoryProductDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryProductDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryProductComponentRule.postExecuteInventoryProductCompMerge(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (OptimisticLockException oe) {
			logger.error("merge failed with OptimisticLockException", oe);
			oe.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "2", oe,
					"OptimisticLockException in InventoryProductComponent.merge");
			//OptimisticLockException- Row was updated or deleted by another transaction
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"InventoryProductComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"InventoryProductComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryProductComponent.merge failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;
	}

	/**
	* This method search the database record based on primary key. 
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or mandatory attributes primary key is not present
	*/
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindById(txnTransferObj);
			theInventoryProductComponentRule.prevalidateInventoryProductCompFindById(txnTransferObj);
			InventoryProductDO reqInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
					.getInventoryProductDO();
			InventoryProductDO dbimageInventoryProductDO = entityManager.find(InventoryProductDO.class,
					reqInventoryProductDO.getIdPk());
			if (null == dbimageInventoryProductDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"InventoryProductComponent.findbyId failed with Validation Exception");
				//Record not found
			}
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setInventoryProductDO(new InventoryProductDO(dbimageInventoryProductDO));
			populateReferenceDataAttributes(respTxnTransferObj.getTxnHeader().getRequesterLanguage(),
					respTxnTransferObj.getTxnPayload().getInventoryProductDO());
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theInventoryProductComponentRule.postExecuteInventoryProductCompFindById(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"InventoryProductComponent.findById failed unexpectedly");
			//Transaction Failed due to unknown error, please check statck trace
		}
		return respTxnTransferObj;

	}

	/**
	* perform the common validation before persisting InventoryProductDO object in the database.
	* populate createdTimestamp, updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforePersist(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryProductDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryProductDO is needed in the request");
		}
		InventoryProductDO theInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
				.getInventoryProductDO();
		theInventoryProductDO.setCreatedTs(new Date());
		theInventoryProductDO.setUpdatedTs(new Date());
		theInventoryProductDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryProductDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation before updating InventoryProductDO object in the database.
	* populate updatedTimestamp, transaction reference Id and user
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeMerge(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getInventoryProductDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryProductDO is needed in the request");
		}
		if (null == txnTransferObj.getTxnPayload().getInventoryProductDO().getIdPk()
				|| txnTransferObj.getTxnPayload().getInventoryProductDO().getIdPk().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryProductDO.idpk should not be null");
		}

		if (null == txnTransferObj.getTxnPayload().getInventoryProductDO().getVersion()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1010",
					"InventoryProductDO.version should not be null");
		}

		InventoryProductDO theInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
				.getInventoryProductDO();
		theInventoryProductDO.setUpdatedTs(new Date());
		theInventoryProductDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theInventoryProductDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	* perform the common validation that InventoryProductDO and idpk is not null
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryProductDO object is not present in the request or mandatory attributes business key is not present
	*/
	private void performCommonvalidationBeforeFindById(TxnTransferObj txnTransferObj) {

		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getInventoryProductDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"InventoryProductDO is needed in the request");
		}
		InventoryProductDO theInventoryProductDO = (InventoryProductDO) txnTransferObj.getTxnPayload()
				.getInventoryProductDO();
		if (null == theInventoryProductDO.getIdPk()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"InventoryProductDO.idpk should not be null");
		}

	}

	//Implement this method to get the Key-Value pair of reference data attributes
	protected void populateReferenceDataAttributes(String requesterLanguage, InventoryProductDO theInventoryProductDO) {

		/*	// SAMPLEREFLOVRefkey
		if (!(null == theInventoryProductDO.getSAMPLEREFLOVRefkey() || theInventoryProductDO.getSAMPLEREFLOVRefkey().isEmpty())) {
			RefSAMPLEREFLOVDO theRefSAMPLEREFLOVDO = referenceTableHelper.getRefSAMPLEREFLOVValueByKey(
					requesterLanguage, theInventoryProductDO.getSAMPLEREFLOVRefkey(),
					yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null != theRefSAMPLEREFLOVDO) {
				theInventoryProductDO.setSAMPLEREFLOVRefValue(theRefSAMPLEREFLOVDO.getValue());
			}
		} */

	}

}
