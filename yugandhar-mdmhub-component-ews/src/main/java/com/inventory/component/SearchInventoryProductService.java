package com.inventory.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

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
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.extern.dobj.SearchInventoryProductDO;

/**
 * @author Yugandhar
 * @version 1.0
 * @since 1.0
 * 
 */

@Scope(value = "prototype")
@Service("com.inventory.component.searchInventoryProductService")
public class SearchInventoryProductService {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	TxnTransferObj txnTransferObjResponse;
	SearchInventoryProductDO respSearchInventoryProductDO;
	TxnTransferObj respTxnTransferObj;
	TxnPayload respTxnPayload;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	InventoryProductComponent inventoryProductComponent;

	@Autowired
	ConfigAppPropertiesComponent configAppPropertiesComponent;

	@PersistenceContext
	private EntityManager entityManager;

	public SearchInventoryProductService() {
		txnTransferObjResponse = new TxnTransferObj();
		respSearchInventoryProductDO = new SearchInventoryProductDO();
		respTxnTransferObj = new TxnTransferObj();
		respTxnPayload = new TxnPayload();
	}

	@Transactional
	public TxnTransferObj process(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		TxnTransferObj transitTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		transitTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			// Perform common validation
			performCommonvalidationBeforeExecution(txnTransferObj);

			Query searchQuery = buildSearchQuery(txnTransferObj);

			setPaginationProperties(searchQuery, txnTransferObj);

			@SuppressWarnings("unchecked")
			List<InventoryProductDO> searchResultInventoryProductDOList = searchQuery.getResultList();

			String inquiryLevel = txnTransferObj.getTxnPayload().getSearchInventoryProductDO().getInquiryLevel();

			respTxnPayload.setInventoryProductDOList(searchResultInventoryProductDOList);

			// Final Response object

			respTxnTransferObj.setTxnPayload(respTxnPayload);

		} catch (YugandharCommonException yce) {
			logger.error("Composite Service failed", yce);
			throw yce;
		} catch (Exception e) {
			// write the logic to get error message based on error code. Error
			// code is hard-coded here
			logger.error("Search Composite service failed", e);
			e.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "5", e,
					"SearchInventoryProductService failed unexpectedly");

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
			searchQuery.setFirstResult(0);
			respTxnPayload.setPaginationIndexOfCurrentSlice(0);
		} else {
			searchQuery.setFirstResult(
					txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice() * searchQuery.getMaxResults());
			respTxnPayload.setPaginationIndexOfCurrentSlice(
					txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice());
		}

		respTxnPayload.setPaginationPageSize(searchQuery.getMaxResults());

	}

	private Query buildSearchQuery(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
		SearchInventoryProductDO reqSearchInventoryProductDO = txnTransferObj.getTxnPayload()
				.getSearchInventoryProductDO();

		// parameters
		String InventoryProductIdPk = reqSearchInventoryProductDO.getInventoryProductIdPk();
		String productName = reqSearchInventoryProductDO.getProductName();
		String productId = reqSearchInventoryProductDO.getProductId();
		String productType = reqSearchInventoryProductDO.getProductType();
		String productCategory = reqSearchInventoryProductDO.getProductCategory();
		String inquiryFilter = reqSearchInventoryProductDO.getInquiryFilter();

		// String buffer for SQL
		StringBuffer queryJoinString = new StringBuffer();
		StringBuffer queryCriteriaString = new StringBuffer();

		// Parameter map
		HashMap<String, String> paramMap = new HashMap<String, String>();

		queryJoinString.append(
				"select distinct inventory_product.id_pk, inventory_product.VERSION, inventory_product.CREATED_TS, inventory_product.DELETED_TS, inventory_product.UPDATED_TS, inventory_product.UPDATED_BY_USER, inventory_product.UPDATED_BY_TXN_ID, "
				+ " inventory_product.PRODUCT_ID, inventory_product.Supplier_ID, inventory_product.Product_Name,inventory_product.Product_Type,inventory_product.Product_Category,inventory_product.hsn_sac_code,"
				+ " inventory_product.Product_Status,inventory_product.Product_Expiry_Date,inventory_product.Product_Arrival_Date,inventory_product.Quantity_Recevied,"
				+ " inventory_product.Quantity_Inhand, inventory_product.Quantity_UnitOfMeasure, inventory_product.Bill_number from inventory_product ");
		if (inquiryFilter.equals(yugandharConstants.FILTER_VALUE_ACTIVE)) {
			queryCriteriaString
					.append(" where (inventory_product.DELETED_TS is null or inventory_product.DELETED_TS > current_timestamp) ");

		} else if (inquiryFilter.equals(yugandharConstants.FILTER_VALUE_INACTIVE)) {
			queryCriteriaString.append(
					" where (inventory_product.DELETED_TS IS NOT NULL AND inventory_product.DELETED_TS < current_timestamp) ");
		} else {
			queryCriteriaString.append(" where 1=1 ");
		}

		if (!isNullOrEmpty(InventoryProductIdPk)) {
			queryCriteriaString.append(" and UPPER(inventory_product.id_pk) like :InventoryProductIdPk ");
			paramMap.put("InventoryProductIdPk", InventoryProductIdPk.toUpperCase());
		}
		
		if (!isNullOrEmpty(productName)) {
			queryCriteriaString.append(" and UPPER(inventory_product.Product_Name) like :productName ");
			paramMap.put("productName", productName.toUpperCase());
		}
		
		if (!isNullOrEmpty(productId)) {
			queryCriteriaString.append(" and UPPER(inventory_product.product_Id) like :productId ");
			paramMap.put("productId", productId.toUpperCase());
		}
		
		if (!isNullOrEmpty(productType)) {
			queryCriteriaString.append(" and UPPER(inventory_product.Product_Type) like :productType ");
			paramMap.put("productType", productType.toUpperCase());
		}
		
		if (!isNullOrEmpty(productCategory)) {
			queryCriteriaString.append(" and UPPER(inventory_product.Product_Category) like :productCategory ");
			paramMap.put("productCategory", productCategory.toUpperCase());
		}

		queryJoinString.append(queryCriteriaString);
		logger.info("SearchInventoryProductService search Query is -" + queryJoinString.toString());
		// get Native query instance
		// Query searchQuery =
		// entityManager.createNativeQuery(queryJoinString.toString());
		Query searchQuery = entityManager.createNativeQuery(queryJoinString.toString(), InventoryProductDO.class);

		// set the paramaters of the query from hashmap
		for (Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> mapEntry = iterator.next();
			logger.debug("SearchInventoryProductService parameter Name:" +mapEntry.getKey() + " Value:" + mapEntry.getValue());
			searchQuery.setParameter(mapEntry.getKey(), mapEntry.getValue());
		}

		return searchQuery;
	}

	private boolean isNullOrEmpty(String strToCheck) {

		if (null == strToCheck || strToCheck.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * perform the common validation that SearchInventoryProductDO have atleast product name, category, idPk, supplier id
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if SearchInventoryProductDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforeExecution(TxnTransferObj txnTransferObj) {

		if (null == txnTransferObj.getTxnPayload().getSearchInventoryProductDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"SearchInventoryProductDO is needed in the request");
		}

		SearchInventoryProductDO theSearchInventoryProductDO = (SearchInventoryProductDO) txnTransferObj
				.getTxnPayload().getSearchInventoryProductDO();


		if (isNullOrEmpty(theSearchInventoryProductDO.getProductName())
				&& isNullOrEmpty(theSearchInventoryProductDO.getProductId())
				&& isNullOrEmpty(theSearchInventoryProductDO.getProductCategory())
				&& isNullOrEmpty(theSearchInventoryProductDO.getProductType())) {

				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "10070",
						"One of the attributes SearchInventoryProductDO.InventoryProductIdPk, productName, productId, productType or productCategory is required");
			}

		if (isNullOrEmpty(txnTransferObj.getTxnPayload().getSearchInventoryProductDO().getInquiryFilter())) {
			txnTransferObj.getTxnPayload().getSearchInventoryProductDO()
					.setInquiryFilter(yugandharConstants.FILTER_VALUE_ACTIVE);
		} else {

			commonValidationUtil.validateFilterValue(txnTransferObj,
					txnTransferObj.getTxnPayload().getSearchInventoryProductDO().getInquiryFilter());
		}

	}
}
