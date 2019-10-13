package com.inventory.component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.extern.transferobj.TxnPayload;
import com.yugandhar.common.transobj.TxnHeader;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.config.app.properties.ConfigAppPropertiesComponent;
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;
import com.yugandhar.mdm.extern.dobj.InventorySummaryDO;

@Scope(value="prototype")
@Service("com.inventory.component.maintainInventoryService")
public class MaintainInventoryService { 

private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER); 

TxnTransferObj txnTransferObjResponse; 
TxnTransferObj respTxnTransferObj; 
TxnPayload respTxnPayload; 

@Autowired
CommonValidationUtil commonValidationUtil; 

@Autowired
ConfigAppPropertiesComponent configAppPropertiesComponent; 

@PersistenceContext
private EntityManager entityManager; 

@Autowired 
InventoryProductService theInventoryProductService; 

@Autowired
InventoryRunningLedgerService theInventoryRunningLedgerService; 

@Autowired 
FetchInventoryRunningLedgerService theFetchInventoryRunningLedgerService; 

@Autowired
InventorySummaryService theInventorySummaryService;


public MaintainInventoryService() { 

txnTransferObjResponse = new TxnTransferObj(); 
respTxnTransferObj = new TxnTransferObj(); 
respTxnPayload = new TxnPayload(); 
}

@Transactional
public TxnTransferObj process(TxnTransferObj txnTransferObj) throws YugandharCommonException { 

respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 

try { 
	// Perform common validation 
	performCommonvalidationBeforeExecution(txnTransferObj); 
	InventoryRunningLedgerDO reqInventoryRunningLedgerDO = txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO(); 
	maintainInventoryProductDO(reqInventoryRunningLedgerDO, txnTransferObj ); 
	
	if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_RETURN) || 
			reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_ISSUE)){ 

		maintainInventorySummary(reqInventoryRunningLedgerDO, txnTransferObj); 

} 

	saveInventoryRunningLedgerDO(reqInventoryRunningLedgerDO, txnTransferObj); 
	//retrive the ledger and send in response 
	TxnPayload theTxnPayload = new TxnPayload(); 

	TxnTransferObj transitTxnTransferObj = new TxnTransferObj(); 
	transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 
	InventoryRunningLedgerDO theInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
	theInventoryRunningLedgerDO.setInquiryFilter("ACTIVE"); 
	transitTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(theInventoryRunningLedgerDO); 

	respTxnTransferObj = theFetchInventoryRunningLedgerService.fetchInventoryRunningLedger(transitTxnTransferObj); 

} catch (YugandharCommonException yce) { 

logger.error("Composite Service failed", yce); 
throw yce; 

} catch (Exception e) { 

// write the logic to get error message based on error code.Error // code is hard-coded here 

logger.error("Search Composite service MaintainInventoryService failed", e); 
e.printStackTrace(); 

throw commonValidationUtil.populateErrorResponse(txnTransferObj, "5", e, "MaintainInventoryService failed unexpectedly"); 

}

respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS); 
return respTxnTransferObj; 

}

private TxnTransferObj maintainInventorySummary(InventoryRunningLedgerDO reqInventoryRunningLedgerDO, 
		TxnTransferObj txnTransferObj) { 

TxnTransferObj transitTxnTransferObj = new TxnTransferObj(); 
transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 

//find the inventory summary record based on legalentityId and Product Id 
//if the record is present then update else create a new summary record in database.
InventorySummaryDO inventorySummaryDO = new InventorySummaryDO(); 
inventorySummaryDO.setLegalentityIdpk(reqInventoryRunningLedgerDO.getLegalentityIdpk()); 
inventorySummaryDO.setProductId(reqInventoryRunningLedgerDO.getProductId()); 
transitTxnTransferObj.getTxnPayload().setInventorySummaryDO(inventorySummaryDO); 

TxnTransferObj respTxnTransferObj = null; 

try { 
	respTxnTransferObj = theInventorySummaryService.findByBusinessKey(transitTxnTransferObj); 
	} catch (YugandharCommonException yce) { 
		// if response code is "Record not found" then 
		//if transaction type is ISSUE a product then create new summary entry in database 
		//if transaction type is RETURN then return error as a product not issued to person cannot be returned 

	if(yce.getErrorCode().equals("102") && 
	reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_RETURN)){ 
	yce.setAdditionalErrorMessage("Not sufficient quantity balance to return.Brahmachari can only return items he/she is issued."); 
	logger.error("MaintainInventoryService.maintainInventorySummary failed: ' + "
			+ "Not sufficient quantity balance to return.Brahmachari can only return items he/she is issued.", yce); 
	throw yce; 
	} else if(yce.getErrorCode().equals("102") && 
			reqInventoryRunningLedgerDO.getTransactionType().equals (yugandharConstants.INV_MAINTAINCE_TXN_TYPE_ISSUE)){ 
			InventorySummaryDO theInventorySummaryDO = new InventorySummaryDO(); 
			theInventorySummaryDO.setLegalentityIdpk (reqInventoryRunningLedgerDO.getLegalentityIdpk()); 
			theInventorySummaryDO.setProductId(reqInventoryRunningLedgerDO.getProductId()); 
			theInventorySummaryDO.setQuantityRunningBalance (reqInventoryRunningLedgerDO.getTransactionQuantity()); 
			theInventorySummaryDO.setQuantityYearToDate (reqInventoryRunningLedgerDO.getTransactionQuantity()); 
			transitTxnTransferObj.getTxnPayload().setInventorySummaryDO (theInventorySummaryDO); 
			///// 
			return theInventorySummaryService.add(transitTxnTransferObj); 
		} else { 
			throw yce; 
		} 

	} catch (Exception e) { 
		//write the logic to get error message based on error code.Error code is hard-coded here 
		logger.error("MaintainInventoryService.maintainInventorySummary failed", e); 
		e.printStackTrace(); 
		throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", e, "MaintainInventoryService.maintainInventorySummary failed unexpectedly"); 
	} 

	//if inventory summary record present in database then update the same.
		Integer dbQuantityRunningBalance = respTxnTransferObj.getTxnPayload ().getInventorySummaryDO().getQuantityRunningBalance(); 
		Integer dbQuantityYearToDate = respTxnTransferObj.getTxnPayload ().getInventorySummaryDO().getQuantityYearToDate(); 

		if(reqInventoryRunningLedgerDO.getTransactionType().equals (yugandharConstants.INV_MAINTAINCE_TXN_TYPE_ISSUE)){ 

			dbQuantityRunningBalance = dbQuantityRunningBalance + reqInventoryRunningLedgerDO.getTransactionQuantity(); 
			dbQuantityYearToDate = dbQuantityYearToDate + reqInventoryRunningLedgerDO.getTransactionQuantity(); 

		} else if(reqInventoryRunningLedgerDO.getTransactionType().equals (yugandharConstants .INV_MAINTAINCE_TXN_TYPE_RETURN) ) { 

			dbQuantityRunningBalance = dbQuantityRunningBalance - reqInventoryRunningLedgerDO.getTransactionQuantity(); 
			dbQuantityYearToDate = dbQuantityYearToDate - reqInventoryRunningLedgerDO.getTransactionQuantity(); 
			
			if(dbQuantityRunningBalance < 0 || dbQuantityYearToDate < 0) {
				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1", "Not sufficient quantity balance to return.Brahmachari can only return items he/she is issued ");  
			}
		}
		
		InventorySummaryDO theInventorySummaryDO = respTxnTransferObj.getTxnPayload().getInventorySummaryDO();
		
		 theInventorySummaryDO.setQuantityRunningBalance(dbQuantityRunningBalance); 
		 theInventorySummaryDO.setQuantityYearToDate(dbQuantityYearToDate); 
		 transitTxnTransferObj.getTxnPayload().setInventorySummaryDO(theInventorySummaryDO); 
		 return theInventorySummaryService.merge(transitTxnTransferObj); 
	}


private void maintainInventoryProductDO(InventoryRunningLedgerDO reqInventoryRunningLedgerDO, TxnTransferObj txnTransferObj) { 

TxnTransferObj transitTxnTransferObj = new TxnTransferObj(); 
transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 

InventoryProductDO inventoryProductDO = new InventoryProductDO(); 
inventoryProductDO.setIdPk(reqInventoryRunningLedgerDO.getProductId()); 
transitTxnTransferObj.getTxnPayload().setInventoryProductDO(inventoryProductDO); 

TxnTransferObj respTxnTransferObj = theInventoryProductService.findById(transitTxnTransferObj); 

// check type of transaction 
if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_ISSUE) ){ 

//perform issue with below logic 
//deduct the transaction quantity from total quantity: Inventory_product.Quant1ty_Inhand - reqInventoryRunningLedgerDO.transactionQuantity 

Integer currentQuantityInhand = respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityInhand(); 

if(currentQuantityInhand < reqInventoryRunningLedgerDO.getTransactionQuantity()) {
	throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1", 
			"Not sufficient quantity balance to Issue. Quantity in Hand:" + currentQuantityInhand + " Quantity to be issued: " + reqInventoryRunningLedgerDO.getTransactionQuantity());
}

currentQuantityInhand = currentQuantityInhand - reqInventoryRunningLedgerDO.getTransactionQuantity(); 
respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityInhand(currentQuantityInhand); 
transitTxnTransferObj.getTxnPayload().setInventoryProductDO (respTxnTransferObj.getTxnPayload().getInventoryProductDO()); 

} else if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_RETURN) ){
// perform issue with below logic 
//add the transaction quantity from total quantity: inventory_product.Quantity_Inhand + reqInventoryRunningLedgerDO.transactionQuantity 

Integer currentQuantityInhand = respTxnTransferObj.getTxnPayload ().getInventoryProductDO().getQuantityInhand(); 
currentQuantityInhand = currentQuantityInhand + reqInventoryRunningLedgerDO.getTransactionQuantity(); 
respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityInhand (currentQuantityInhand); 
transitTxnTransferObj.getTxnPayload().setInventoryProductDO (respTxnTransferObj.getTxnPayload().getInventoryProductDO()); 

} else if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_LOST) ){
// perform issue with below logic 
//deduct the transaction quantity from total quantity: inventory_product.Quantity_Inhand - reqInventoryRunningLedgerDO.transactionQuantity
	if(respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityRecevied() < reqInventoryRunningLedgerDO.getTransactionQuantity()) {
		throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1", 
				"Quantity to be marked as LOST should be less then Quantity Recieved in inventory. "
				+ "Quantity Recieved so far:" + respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityRecevied() + 
				" Quantity to be marked as LOST: " + reqInventoryRunningLedgerDO.getTransactionQuantity());
	}
	Integer currentQuantityInhand = respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityInhand(); 
	currentQuantityInhand = currentQuantityInhand - reqInventoryRunningLedgerDO.getTransactionQuantity(); 
	respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityInhand(currentQuantityInhand); 
	transitTxnTransferObj.getTxnPayload().setInventoryProductDO(respTxnTransferObj.getTxnPayload().getInventoryProductDO()); 
	
} else if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_DAMAGED) ){
// perform issue with below logic 
//deduct the transaction quantity from total quantity: inventory_product.Quantity_ Inhand reqInventoryRunningLedgerDO.transactionQuantity 
	if(respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityRecevied() < reqInventoryRunningLedgerDO.getTransactionQuantity()) {
		throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1", 
				"Quantity to be marked as DAMAGED should be less then Quantity Recieved in inventory. "
				+ "Quantity Recieved so far:" + respTxnTransferObj.getTxnPayload().getInventoryProductDO().getQuantityRecevied() + 
				" Quantity to be marked as DAMAGED: " + reqInventoryRunningLedgerDO.getTransactionQuantity());
	}
Integer currentQuantityInhand= respTxnTransferObj.getTxnPayload ().getInventoryProductDO().getQuantityInhand(); 
currentQuantityInhand = currentQuantityInhand - reqInventoryRunningLedgerDO.getTransactionQuantity(); 
respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityInhand(currentQuantityInhand); 
transitTxnTransferObj.getTxnPayload().setInventoryProductDO(respTxnTransferObj.getTxnPayload().getInventoryProductDO()); 

} else if(reqInventoryRunningLedgerDO.getTransactionType().equals(yugandharConstants.INV_MAINTAINCE_TXN_TYPE_PROCURE) ){

// perform issue with below logic 
//add the transaction quantity to total quantity: inventory_product.Quantity_Recevied + reqInventoryRunningLedgerDO.transactionQuantity 
//add the transaction quantity to total quantity: inventory;product.Quantity_Inhand + reqInventoryRunningLedgerDO.transactionQuantity 

Integer currentQuantityRecieved = respTxnTransferObj.getTxnPayload ().getInventoryProductDO().getQuantityRecevied(); 
currentQuantityRecieved = currentQuantityRecieved + reqInventoryRunningLedgerDO.getTransactionQuantity(); 
respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityRecevied (currentQuantityRecieved); 
Integer currentQuantityInhand = respTxnTransferObj.getTxnPayload ().getInventoryProductDO().getQuantityInhand(); 
currentQuantityInhand = currentQuantityInhand + reqInventoryRunningLedgerDO.getTransactionQuantity(); 
respTxnTransferObj.getTxnPayload().getInventoryProductDO().setQuantityInhand (currentQuantityInhand); 
transitTxnTransferObj.getTxnPayload().setInventoryProductDO(respTxnTransferObj.getTxnPayload().getInventoryProductDO()); 

} 

respTxnTransferObj = theInventoryProductService.merge(transitTxnTransferObj); 
}

private TxnTransferObj saveInventoryRunningLedgerDO(InventoryRunningLedgerDO reqInventoryRunningLedgerDO, TxnTransferObj txnTransferObj) { 
	TxnTransferObj transitTxnTransferObj = new TxnTransferObj(); 
	TxnTransferObj respLederListTxnTransferObj = new TxnTransferObj(); 
	transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 
	//theInventoryRunningLedgerSerVice 
	transitTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(reqInventoryRunningLedgerDO); 
	TxnTransferObj respTxnTransferObj = theInventoryRunningLedgerService.add(transitTxnTransferObj); 

return respTxnTransferObj; 
}

/**
* perform the common validation that InventoryRunningLedgerDO is present
* @since 1.0 
* @param txnTransferObj Transfer Object TxnTransferObj instance 
* @throws YugandharCommonException * 
it InventoryRunningLedgerDO object is not present in the request or ' mandatory attributes business key is not present *

 **/

private void performCommonvalidationBeforeExecution(TxnTransferObj txnTransferObj) { 

//InventoryRunningLedgerDO 
	if(null==txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO()) { 
		throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001", "object InventoryRunningLedgerDO is required");
}}

public TxnTransferObj initTxnTransferObj(String userId, String role, String requestorLanguage, String transactionServiceName) {
	TxnTransferObj txnTransferObj = new TxnTransferObj();
	TxnPayload txnPayload = new TxnPayload();
	TxnHeader txnHeader = new TxnHeader();
	txnHeader.setUserName(userId); // currently defaulted to admin
	txnHeader.setUserRole(role);// currently defaulted to admin
	txnHeader.setRequesterLanguage(requestorLanguage);// currently defaulted to english :1
	txnHeader.setTransactionServiceName(transactionServiceName);
	txnTransferObj.setTxnHeader(txnHeader);
	txnTransferObj.setTxnPayload(txnPayload);
	return txnTransferObj;
}

}





