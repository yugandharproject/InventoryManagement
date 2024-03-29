package com.inventory.component;
/* Generated 28 Jul, 2019 12:31:58 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yugandhar.common.constant.yugandharConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.util.CommonValidationUtil;

/**
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see Documentation
*/

@Scope(value = "prototype")
@Service("com.inventory.component.InventoryOrderService")
public class InventoryOrderService {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	TxnTransferObj txnTransferObjResponse;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	private InventoryOrderComponent theInventoryOrderComponent;

	public InventoryOrderService() {
		txnTransferObjResponse = new TxnTransferObj();
	}

	/**
	*This service creates a record in database. generates the idpk if not provided in the request and 
	*populate the updatedByUser, updatedTxnRefId, createdTsString, updatedTsString attributes.
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with persisted instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or other mandatory attributes not present
	*
	*/
	@Transactional
	public TxnTransferObj add(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		TxnTransferObj respTxnTransferObj;
		try {
			respTxnTransferObj = theInventoryOrderComponent.persist(txnTransferObj);
		} catch (YugandharCommonException yce) {
			logger.error("InventoryOrderService.add failed", yce);
			throw yce;
		} catch (Exception e) {
			//write the logic to get error message based on error code. Error code is hard-coded here
			logger.error("InventoryOrderService.add failed", e);
			e.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", e,
					"InventoryOrderService.add failed unexpectedly");

		}
		return respTxnTransferObj;

	}

	/**This service updates the record in database. populate the updatedByUser, updatedTxnRefId, updatedTsString attributes
	*@since 1.0
	*@param  txnTransferObj  Transfer Object TxnTransferObj instance
	*@return  txnTransferObj Returns the Transfer Object TxnTransferObj instance populated with database instance 
	*@throws YugandharCommonException if InventoryOrderDO object is not present in the request or mandatory attributes primary key is not present
	*/
	@Transactional
	public TxnTransferObj merge(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		TxnTransferObj respTxnTransferObj;
		try {
			respTxnTransferObj = theInventoryOrderComponent.merge(txnTransferObj);
		} catch (YugandharCommonException yce) {
			logger.error("InventoryOrderService.merge failed", yce);
			throw yce;
		} catch (Exception e) {
			//write the logic to get error message based on error code. Error code is hard-coded here
			logger.error("InventoryOrderService.merge failed", e);
			e.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", e,
					"InventoryOrderService.merge failed unexpectedly");

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
	@Transactional(readOnly = true)
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		TxnTransferObj respTxnTransferObj;
		try {
			respTxnTransferObj = theInventoryOrderComponent.findById(txnTransferObj);
		} catch (YugandharCommonException yce) {
			logger.error("InventoryOrderService.findById failed", yce);
			throw yce;
		} catch (Exception e) {
			//write the logic to get error message based on error code. Error code is hard-coded here
			logger.error("InventoryOrderService.findById failed", e);
			e.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", e,
					"InventoryOrderService.findById failed unexpectedly");

		}
		return respTxnTransferObj;

	}

}
