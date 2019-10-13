package com.inventory.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.extern.transferobj.TxnPayload;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.common.util.YugandharConfigurationProperties;
import com.yugandhar.mdm.config.app.properties.ConfigAppPropertiesComponent;
import com.yugandhar.mdm.extern.dobj.ConfigAppPropertiesDO;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;

@Scope(value = "prototype") 
@Service("com.inventory.component.fetchInventoryRunningLedgerService") 
public class FetchInventoryRunningLedgerService { 

private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER); 

TxnTransferObj txnTransferObjResponse;
InventoryRunningLedgerDO respInventoryRunningLedgerDO; 
TxnTransferObj respTxnTransferObj; 
TxnPayload respTxnPayload; 

@Autowired
CommonValidationUtil commonValidationUtil; 

@Autowired
ConfigAppPropertiesComponent configAppPropertiesComponent; 

@PersistenceContext 
private EntityManager entityManager; 

public FetchInventoryRunningLedgerService() { 
	txnTransferObjResponse = new TxnTransferObj(); 
	respInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
	respTxnTransferObj = new TxnTransferObj(); 
	respTxnPayload = new TxnPayload(); 
	} 

@Transactional 
public TxnTransferObj fetchInventoryRunningLedger(TxnTransferObj txnTransferObj) throws YugandharCommonException {
	TxnTransferObj transitTxnTransferObj = new TxnTransferObj(); 
	respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 
	transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader()); 
	
	
	try { 
		// Perform common validation 
		performCommonvalidationBeforeExecution(txnTransferObj); 
		Query searchQuery= buildSearchQuery(txnTransferObj);
		setPaginationProperties(searchQuery, txnTransferObj); 

		@SuppressWarnings("unchecked") 
		List<Object[]> searchResults = searchQuery. getResultList(); 

		Iterator itrResultIterator = searchResults.iterator(); 

		List<InventoryRunningLedgerDO> searchResultInventoryRunningLedgerDOList = new ArrayList<InventoryRunningLedgerDO>(); 

		//map the search results 
		while(itrResultIterator.hasNext()) { 
			InventoryRunningLedgerDO itrInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
			Object obj[] = (Object[]) itrResultIterator.next(); 
			itrInventoryRunningLedgerDO.setIdPk((String) obj[0]); 
			itrInventoryRunningLedgerDO.setTransactionType((String) obj[1]); 
			itrInventoryRunningLedgerDO.setProductname((String) obj[2]); 
			itrInventoryRunningLedgerDO.setDisplayname((String) obj[3]); 
			
			if(null != obj[4]) { 
				BigDecimal qty= (BigDecimal) obj[4]; 
				itrInventoryRunningLedgerDO. setTransactionQuantity(qty. intValue()); 
			} 

			itrInventoryRunningLedgerDO.setProductId((String) obj[5]); 
			itrInventoryRunningLedgerDO.setLegalentityIdpk((String) obj[6]); 
			itrInventoryRunningLedgerDO.setCreatedTs((Date) obj[7]); 
			itrInventoryRunningLedgerDO.setUpdatedByUser((String) obj[8]); 
			//itrInventoryRunningLedgerDO.setVersion((String) obj[9]); 
			//itrInventoryRunningLedgerDO.setDeletedTs((String) obj[10]); 
			//itrInventoryRunningLedgerDO.setUpdatedTs((String) obj[ll]); 
			itrInventoryRunningLedgerDO.setUpdatedByTxnId((String) obj[12]); 
			
			searchResultInventoryRunningLedgerDOList.add(itrInventoryRunningLedgerDO); 

		}


		respTxnPayload.setInventoryRunningLedgerDOList(searchResultInventoryRunningLedgerDOList); 
		// Final Response object 

		respTxnTransferObj.setTxnPayload(respTxnPayload); 

		} catch (YugandharCommonException yce) { 
				logger.error("Composite Service failed", yce); 
				throw yce; 

		} catch (Exception e) { 
				// write the logic to get error message based on error code. Error ‘ // code is hard-coded here 
				logger.error("Search Composite service FetchInventoryRunningLedgerService failed", e); 
				e.printStackTrace(); 
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "5", e, "FetchInventoryRunningLedgerService failed unexpectedly"); 
		} 

		respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS); 
		return respTxnTransferObj; 

	} 


private void setPaginationProperties(Query searchQuery, TxnTransferObj txnTransferObj) { 
	// set pagination properties 
	// set the current page index to zero (0) if page index is not 
	// provided in the request or negative value is provided 

	ConfigAppPropertiesDO theConfigAppPropertiesDO = configAppPropertiesComponent.executeRepositoryQuery( 
			YugandharConfigurationProperties.com_yugandhar_pagination_default_pagesize_search, 
			yugandharConstants.FILTER_VALUE_ACTIVE); 

	// set default page size as configured in application properties 
	if (null == txnTransferObj.getTxnPayload().getPaginationPageSize() 
			|| txnTransferObj.getTxnPayload().getPaginationPageSize() <= 0) {
		searchQuery.setMaxResults(Integer.valueOf(theConfigAppPropertiesDO.getValue())); 
	} else {
		searchQuery.setMaxResults(txnTransferObj.getTxnPayload().getPaginationPageSize()); 
		} 

	if (null == txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice() 
			|| txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice() < 0) { 
		searchQuery. setFirstResult(0);
		respTxnPayload.setPaginationIndexOfCurrentSlice(0); 
		} else { 

			searchQuery.setFirstResult( 
			txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice() * searchQuery. getMaxResults()); 
			respTxnPayload.setPaginationIndexOfCurrentSlice( 
			txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice()); 
		}  
	respTxnPayload.setPaginationPageSize(searchQuery.getMaxResults()); 
} 

		private Query buildSearchQuery(TxnTransferObj txnTransferObj) { 
		InventoryRunningLedgerDO reqInventoryRunningLedgerDO = txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO(); 
		// parameters 
		String inquiryFilter = reqInventoryRunningLedgerDO.getInquiryFilter(); 

		// String buffer for SQL 
		StringBuffer queryJoinString = new StringBuffer();
		StringBuffer queryCriteriaString = new StringBuffer(); 

		queryJoinString.append(
		" SELECT inventory_running_ledger.ID_PK, " + 
		" inventory_running_ledger.TRANSACTION_TYPE, " + 
		" inventory_product.Product_Name productname, " + 
		" legalentity.DISPLAY_NAME displayname, " + 
		" inventory_running_ledger.TRANSACTION_QUANTITY, " + 
		" inventory_running_ledger.PRODUCT_ID, " +  
		" inventory_running_ledger.LEGALENTITY_IDPK, " +
		" inventory_running_ledger.CREATED_TS, " +
		" inventory_running_ledger.UPDATED_BY_USER, " +
		" inventory_running_ledger.VERSION, " +
		" inventory_running_ledger.DELETED_TS, " +
		" inventory_running_ledger.UPDATED_TS, " +
		" inventory_running_ledger.UPDATED_BY_TXN_ID " +
		" FROM inventory_running_ledger, legalentity, inventory_product " + 
		" WHERE inventory_running_ledger.LEGALENTITY_IDPK = legalentity.ID_PK " + 
		" AND inventory_running_ledger.PRODUCT_ID = inventory_product.ID_PK ");
		
		
		if (inquiryFilter.equals(yugandharConstants.FILTER_VALUE_ACTIVE)) { 
		
		queryCriteriaString.append(" and (inventory_running_ledger.DELETED_TS is null or inventory_running4ledger.DELETED_TS > current_timestamp) "); 
		
		} else if (inquiryFilter.equals(yugandharConstants.FILTER_VALUE_INACTIVE)) { 
			queryCriteriaString.append(" and (inventory_running_ledger.DELETED_TS IS NOT NULL AND inventory_running_ledger.DELETED_TS < current_timestamp) "); 
			
		} else { 
			queryCriteriaString.append(" and 1:1 "); 
		} 
		
		queryJoinString.append(" ORDER BY created_ts desc"); 
		logger.info("Maintain Inventory service fetch query is -" + queryJoinString.toString()); 
		
		// get Native query instance 
		// Query searchQuery= 
		// entityManager. createNativeQuery(queryJoinString. toString()); 
		
		Query searchQuery= entityManager.createNativeQuery(queryJoinString. toString()); 
		//Query searchQuery= entityManager. createNativeQuery(queryJoinString. toString(), InventoryRunningLedgerDo. class); 
		
		return searchQuery; 
		
		} 
		
		
		private boolean isNullOrEmpty(String strToCheck) { 
			if (null== strToCheck || strToCheck.trim().isEmpty()) { 
				return true; 
				} else { 
					return false; 
					} 
			} 
		
		
/** ' * perform the common validation that InventoryRunningLedgerDO 
 * 
 * @since 1.0 
 * @param txnTransferObj Transfer Object TxnTransferObj instance 
 * @throws YugandharCommonException 
 * 			if InventoryRunningLedgerDO object is not-present in the request or 
 * 			mandatory attributes business key is not present 
*/ 
		private void performCommonvalidationBeforeExecution(TxnTransferObj txnTransferObj) { 
		
		if (isNullOrEmpty(txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO().getInquiryFilter())) { 
		
		txnTransferObj. getTxnPayload(). getInventoryRunningLedgerDO() .setInquiryFilter(yugandharConstants.FILTER_VALUE_ACTIVE); 
		
		} else { 
		
		commonValidationUtil. validateFilterValue(txnTransferObj, txnTransferObj.getTxnPayload().getInventoryRunningLedgerDO(). getInquiryFilter()); 
		
		}}}
