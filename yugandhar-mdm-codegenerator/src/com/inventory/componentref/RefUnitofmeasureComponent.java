package com.inventory.componentref;
/* Generated 28 Jul, 2019 5:37:44 PM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.common.util.IgnoreAttributesUtil;
import com.yugandhar.common.util.YugandharConfigurationProperties;
import com.yugandhar.mdm.config.app.properties.ConfigAppPropertiesComponent;
import com.yugandhar.mdm.config.langcode.ConfigLanguageCodeComponent;
import com.yugandhar.mdm.extern.dobj.ConfigAppPropertiesDO;
import com.yugandhar.mdm.extern.dobj.ConfigLanguageCodeDO;
import com.yugandhar.mdm.extern.dobj.RefUnitofmeasureDO;
import com.yugandhar.mdm.keygen.YugandharKeygenerator;

/**
 * Component object for domain model RefUnitofmeasureDO class
 * 
 * @author Yugandhar
 * @version 1.0
 * @since 1.0
 * @see Documentation
 */

@Scope(value = "prototype")
@Component
public class RefUnitofmeasureComponent {

	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);

	// entityManager instance injection
	@PersistenceContext
	private EntityManager entityManager;

	// get the repository instance
	@Autowired
	private RefUnitofmeasureRepository theRefUnitofmeasureRepository;

	@Autowired
	CommonValidationUtil commonValidationUtil;

	@Autowired
	RefUnitofmeasureComponentRule theRefUnitofmeasureComponentRule;

	@Autowired
	ConfigLanguageCodeComponent theConfigLanguageCodeComponent;

	@Autowired
	ConfigAppPropertiesComponent configAppPropertiesComponent;

	// default transaction response object
	TxnTransferObj respTxnTransferObj;

	// default constructor
	public RefUnitofmeasureComponent() {
	}

	/**
	 * This method creates a record in database. generates the idpk if not
	 * provided in the request and populate the updatedByUser, updatedByTxnId,
	 * createdTsString, updatedTsString attributes.
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with persisted instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             other mandatory attributes not present
	 *
	 */
	public TxnTransferObj persist(TxnTransferObj txnTransferObj) throws YugandharCommonException {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforePersist(txnTransferObj);
			theRefUnitofmeasureComponentRule.prevalidateRefUnitofmeasureCompPersit(txnTransferObj);
			// check if idpk is provided, else generate idpk with default UUID
			// generator
			RefUnitofmeasureDO reqRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
					.getRefUnitofmeasureDO();
			if (null == reqRefUnitofmeasureDO.getPrimaryKeyDO()
					|| null == reqRefUnitofmeasureDO.getPrimaryKeyDO().getIdPk()) {
				logger.debug("Persist Method - pk Id in request is null, generating new id");
				YugandharKeygenerator yugandharKeygenerator = new YugandharKeygenerator();
				reqRefUnitofmeasureDO.setIdPk(yugandharKeygenerator.generateKey());
			} else {
				reqRefUnitofmeasureDO.setIdPk(reqRefUnitofmeasureDO.getPrimaryKeyDO().getIdPk());
				RefUnitofmeasureDO dbimageRefUnitofmeasureDO = entityManager.find(RefUnitofmeasureDO.class,
						reqRefUnitofmeasureDO.getIdPk());
				if (null != dbimageRefUnitofmeasureDO) {
					throw commonValidationUtil.createErrorResponse(txnTransferObj, "101",
							"RefUnitofmeasureComponent.persist failed with Validation Exception");
					// Record already present for given Idpk
				}
			}
			theRefUnitofmeasureComponentRule.preExecuteRefUnitofmeasureCompPersist(reqRefUnitofmeasureDO,
					txnTransferObj);
			entityManager.persist(reqRefUnitofmeasureDO);
			entityManager.flush();
			reqRefUnitofmeasureDO.setPrimaryKeyDO(null);
			respTxnTransferObj.getTxnPayload().setRefUnitofmeasureDO(new RefUnitofmeasureDO(reqRefUnitofmeasureDO));
			theRefUnitofmeasureComponentRule.postExecuteRefUnitofmeasureCompPersit(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"RefUnitofmeasureComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"RefUnitofmeasureComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"RefUnitofmeasureComponent.persist failed unexpectedly");
			// Transaction Failed due to unknown error, please check statck
			// trace
		}
		return respTxnTransferObj;
	}

	/**
	 * This service updates the record in database. populate the updatedByUser,
	 * updatedByTxnId, updatedTsString attributes
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes primary key is not present
	 */

	public TxnTransferObj merge(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
		try {
			performCommonvalidationBeforeMerge(txnTransferObj);
			theRefUnitofmeasureComponentRule.PrevalidateRefUnitofmeasureCompMerge(txnTransferObj);
			RefUnitofmeasureDO reqRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
					.getRefUnitofmeasureDO();
			RefUnitofmeasureDO dbimageRefUnitofmeasureDO = entityManager.find(RefUnitofmeasureDO.class,
					reqRefUnitofmeasureDO.getIdPk());
			if (null == dbimageRefUnitofmeasureDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"RefUnitofmeasureComponent.merge failed with Validation Exception");
				// Record not found for given Idpk
			}

			IgnoreAttributesUtil ignoreAttributesUtil = new IgnoreAttributesUtil();
			String[] strIgnoreProperties = ignoreAttributesUtil.getAttributesToIgnore(reqRefUnitofmeasureDO);
			BeanUtils.copyProperties(reqRefUnitofmeasureDO, dbimageRefUnitofmeasureDO, strIgnoreProperties);
			entityManager.detach(dbimageRefUnitofmeasureDO);
			theRefUnitofmeasureComponentRule.preExecuteRefUnitofmeasureCompMerge(reqRefUnitofmeasureDO,
					dbimageRefUnitofmeasureDO, txnTransferObj);
			RefUnitofmeasureDO mergedRefUnitofmeasureDO = entityManager.merge(dbimageRefUnitofmeasureDO);
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setRefUnitofmeasureDO(new RefUnitofmeasureDO(mergedRefUnitofmeasureDO));
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theRefUnitofmeasureComponentRule.postExecuteRefUnitofmeasureCompMerge(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (OptimisticLockException oe) {
			logger.error("merge failed with OptimisticLockException", oe);
			oe.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "2", oe,
					"OptimisticLockException in RefUnitofmeasureComponent.merge");
			// OptimisticLockException- Row was updated or deleted by another
			// transaction
		} catch (javax.persistence.PersistenceException pe) {
			logger.error("persist failed", pe);
			pe.printStackTrace();
			Throwable theCause = pe.getCause();
			if (theCause instanceof ConstraintViolationException) {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "3", pe,
						"RefUnitofmeasureComponent.persist failed - Unique Key Violated");
			} else {
				throw commonValidationUtil.populateErrorResponse(txnTransferObj, "4", pe,
						"RefUnitofmeasureComponent.persist failed unexpectedly with PersistenceException");
				// Transaction Failed due to unknown error, please check statck
				// trace
			}
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"RefUnitofmeasureComponent.merge failed unexpectedly");
			// Transaction Failed due to unknown error, please check statck
			// trace
		}
		return respTxnTransferObj;
	}

	/**
	 * This method search the database record based on primary key.
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes primary key is not present
	 */
	public TxnTransferObj findById(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindById(txnTransferObj);
			theRefUnitofmeasureComponentRule.prevalidateRefUnitofmeasureCompFindById(txnTransferObj);
			RefUnitofmeasureDO reqRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
					.getRefUnitofmeasureDO();
			RefUnitofmeasureDO dbimageRefUnitofmeasureDO = entityManager.find(RefUnitofmeasureDO.class,
					reqRefUnitofmeasureDO.getIdPk());
			if (null == dbimageRefUnitofmeasureDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"RefUnitofmeasureComponent.findbyId failed with Validation Exception");
				// Record not found
			}
			entityManager.flush();
			respTxnTransferObj.getTxnPayload().setRefUnitofmeasureDO(new RefUnitofmeasureDO(dbimageRefUnitofmeasureDO));
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
			theRefUnitofmeasureComponentRule.postExecuteRefUnitofmeasureCompFindById(respTxnTransferObj);

		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findById failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"RefUnitofmeasureComponent.findById failed unexpectedly");
			// Transaction Failed due to unknown error, please check statck
			// trace
		}
		return respTxnTransferObj;

	}

	/**
	 * perform the common validation before persisting RefUnitofmeasureDO object in
	 * the database. populate createdTimestamp, updatedTimestamp, transaction
	 * reference Id and user
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforePersist(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"RefUnitofmeasureDO is needed in the request");
		}

		RefUnitofmeasureDO theRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
				.getRefUnitofmeasureDO();
		if (null == theRefUnitofmeasureDO.getKey() || theRefUnitofmeasureDO.getKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1006",
					"RefUnitofmeasureDO.key should not be null");
		}

		if (null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1007",
					"RefUnitofmeasureDO.configLanguageCodeKey should not be null");
		}

		if (null == theRefUnitofmeasureDO.getValue() || theRefUnitofmeasureDO.getValue().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1011",
					"RefUnitofmeasureDO.Value should not be null");
		}

		// configLanguageCodeKey
		if (!(null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty())) {
			ConfigLanguageCodeDO theConfigLanguageCodeDO = theConfigLanguageCodeComponent
					.getConfigLanguageCodeValueByKey(theRefUnitofmeasureDO.getConfigLanguageCodeKey(),
							yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null == theConfigLanguageCodeDO) {

				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1012",
						"RefUnitofmeasureDO.configLanguageCodeKey is not valid");
			}
		}

		theRefUnitofmeasureDO.setCreatedTs(new Date());
		theRefUnitofmeasureDO.setUpdatedTs(new Date());
		theRefUnitofmeasureDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theRefUnitofmeasureDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	 * perform the common validation before updating RefUnitofmeasureDO object in the
	 * database. populate updatedTimestamp, transaction reference Id and user
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforeMerge(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"RefUnitofmeasureDO is needed in the request");
		}
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getIdPk()
				|| txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getIdPk().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"RefUnitofmeasureDO.idpk should not be null");
		}

		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getVersion()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1010",
					"RefUnitofmeasureDO.version should not be null");
		}

		RefUnitofmeasureDO theRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
				.getRefUnitofmeasureDO();
		if (null == theRefUnitofmeasureDO.getKey() || theRefUnitofmeasureDO.getKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1006",
					"RefUnitofmeasureDO.key should not be null");
		}

		if (null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1007",
					"RefUnitofmeasureDO.configLanguageCodeKey should not be null");
		}

		if (null == theRefUnitofmeasureDO.getValue() || theRefUnitofmeasureDO.getValue().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1011",
					"RefUnitofmeasureDO.Value should not be null");
		}

		// configLanguageCodeKey
		if (!(null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty())) {
			ConfigLanguageCodeDO theConfigLanguageCodeDO = theConfigLanguageCodeComponent
					.getConfigLanguageCodeValueByKey(theRefUnitofmeasureDO.getConfigLanguageCodeKey(),
							yugandharConstants.FILTER_VALUE_ACTIVE);
			if (null == theConfigLanguageCodeDO) {

				throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1012",
						"RefUnitofmeasureDO.configLanguageCodeKey is not valid");
			}
		}

		theRefUnitofmeasureDO.setUpdatedTs(new Date());
		theRefUnitofmeasureDO.setUpdatedByTxnId(txnTransferObj.getTxnHeader().getTxnMessageId());
		theRefUnitofmeasureDO.setUpdatedByUser(txnTransferObj.getTxnHeader().getUserName());
	}

	/**
	 * perform the common validation that RefUnitofmeasureDO and idpk is not null
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforeFindById(TxnTransferObj txnTransferObj) {

		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);

		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"RefUnitofmeasureDO is needed in the request");
		}
		RefUnitofmeasureDO theRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
				.getRefUnitofmeasureDO();
		if (null == theRefUnitofmeasureDO.getIdPk()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1004",
					"RefUnitofmeasureDO.idpk should not be null");
		}

	}

	/**
	 * Returns all the records from the for RefUnitofmeasureDO entity based on
	 * language code and maximum of records as configuration in properties
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	public TxnTransferObj findAllRecordsByLanguageCode(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforefindAllRecordsByLanguageCode(txnTransferObj);
			Page<RefUnitofmeasureDO> pageRefUnitofmeasureDO = findAllRecordsByLanguageCodeFromRepository(
					txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getConfigLanguageCodeKey(),
					txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter(),
					txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice(),
					txnTransferObj.getTxnPayload().getPaginationPageSize(), txnTransferObj);

			if (pageRefUnitofmeasureDO.getTotalPages() == 0) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "103",
						"RefUnitofmeasure reference list does not have any records in the database");
			}

			if ((pageRefUnitofmeasureDO.getTotalPages() - 1) < txnTransferObj.getTxnPayload()
					.getPaginationIndexOfCurrentSlice()) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "1021",
						"Invalid page number in the request for RefUnitofmeasure, total number of pages is "
								+ pageRefUnitofmeasureDO.getTotalPages() + ". Note- Pages Index starts with 0");
			}

			// Copy pagination properties
			respTxnTransferObj.getTxnPayload().setPaginationIndexOfCurrentSlice(pageRefUnitofmeasureDO.getNumber());
			respTxnTransferObj.getTxnPayload()
					.setPaginationInfoElementsOnCurrentSlice(pageRefUnitofmeasureDO.getNumberOfElements());
			respTxnTransferObj.getTxnPayload()
					.setPaginationInfoTotalElements(pageRefUnitofmeasureDO.getTotalElements());
			respTxnTransferObj.getTxnPayload().setPaginationInfoTotalPages(pageRefUnitofmeasureDO.getTotalPages());
			respTxnTransferObj.getTxnPayload().setPaginationPageSize(pageRefUnitofmeasureDO.getSize());

			List<RefUnitofmeasureDO> dbimageRefUnitofmeasureDOlist = pageRefUnitofmeasureDO.getContent();
			// clone the object before sending response. This is important to
			// prevent editing/ future referencing of entity manager level 1
			// (L1) cached object
			List<RefUnitofmeasureDO> clonedRefUnitofmeasureDOList = null;
			if (null != dbimageRefUnitofmeasureDOlist && dbimageRefUnitofmeasureDOlist.size() > 0) {
				clonedRefUnitofmeasureDOList = new ArrayList<RefUnitofmeasureDO>();
				Iterator<RefUnitofmeasureDO> itr = dbimageRefUnitofmeasureDOlist.iterator();
				while (itr.hasNext()) {
					RefUnitofmeasureDO theRefUnitofmeasureDO = new RefUnitofmeasureDO(itr.next());
					clonedRefUnitofmeasureDOList.add(theRefUnitofmeasureDO);
				}
			}

			if (null == clonedRefUnitofmeasureDOList) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "103",
						"RefUnitofmeasure reference list does not have any records in the database");
				// Record not found
			} else if (clonedRefUnitofmeasureDOList.size() == 0) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "103",
						"RefUnitofmeasure reference list does not have any records in the database");
				// Record not found
			} else {
				respTxnTransferObj.getTxnPayload().setRefUnitofmeasureDOList(clonedRefUnitofmeasureDOList);
			}

			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findByLanguageCodeAndKey failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"RefUnitofmeasureComponent.findByBusinessKey failed unexpectedly");
		}

		return respTxnTransferObj;

	}

	@CacheResult(cacheName = "REFUNITOFMEASURE_BUSKEY")
	public Page<RefUnitofmeasureDO> findAllRecordsByLanguageCodeFromRepository(@CacheKey String configLanguageCodeKey,
			@CacheKey String filter, @CacheKey Integer requestedPageNumber, @CacheKey Integer requestedPageSize,
			TxnTransferObj txnTransferObj) {

		Sort localSort = new Sort(new Sort.Order(Sort.Direction.ASC, "idPk"));
		Pageable localPageable = new PageRequest(requestedPageNumber, requestedPageSize, localSort);

		Page<RefUnitofmeasureDO> pageRefUnitofmeasureDO = null;
		if (filter.toUpperCase().equals("ACTIVE")) {
			pageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository
					.findAllActiveByLanguageCode(configLanguageCodeKey, localPageable);
		} else if (filter.toUpperCase().equals("INACTIVE")) {
			pageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository
					.findAllInActiveByLanguageCode(configLanguageCodeKey, localPageable);
		} else if (filter.toUpperCase().equals("ALL")) {
			pageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository.findAllByLanguageCode(configLanguageCodeKey,
					localPageable);

		}

		return pageRefUnitofmeasureDO;
	}

	/**
	 * This method search the database table records based on
	 * business key (e.g.LanguageCode and Key)
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */

	public TxnTransferObj findByBusinessKey(TxnTransferObj txnTransferObj) {
		respTxnTransferObj = new TxnTransferObj();
		respTxnTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());

		try {
			performCommonvalidationBeforeFindByBusinessKey(txnTransferObj);
			RefUnitofmeasureDO reqRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
					.getRefUnitofmeasureDO();
			theRefUnitofmeasureComponentRule.preValidateRefUnitofmeasurefindByBusinessKey(txnTransferObj);
			theRefUnitofmeasureComponentRule.preExecuteRefUnitofmeasurefindByBusinessKey(txnTransferObj);

			RefUnitofmeasureDO dbimageRefUnitofmeasureDO = executeRepositoryQuery(
					reqRefUnitofmeasureDO.getConfigLanguageCodeKey(), reqRefUnitofmeasureDO.getKey(),
					reqRefUnitofmeasureDO.getInquiryFilter());

			if (null == dbimageRefUnitofmeasureDO) {
				throw commonValidationUtil.createErrorResponse(txnTransferObj, "102",
						"RefUnitofmeasureComponent.findByBusinessKey search result have no records");
				// Record not found
			} else {
				dbimageRefUnitofmeasureDO
						.setInquiryFilter(txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter());
				respTxnTransferObj.getTxnPayload()
						.setRefUnitofmeasureDO(new RefUnitofmeasureDO(dbimageRefUnitofmeasureDO));
			}

			theRefUnitofmeasureComponentRule.postExecuteRefUnitofmeasurefindByBusinessKey(respTxnTransferObj);
			respTxnTransferObj.setResponseCode(yugandharConstants.RESPONSE_CODE_SUCCESS);
		} catch (YugandharCommonException yce) {
			throw yce;
		} catch (RuntimeException re) {
			logger.error("findByLanguageCodeAndKey failed", re);
			re.printStackTrace();
			throw commonValidationUtil.populateErrorResponse(txnTransferObj, "1", re,
					"RefUnitofmeasureComponent.findByBusinessKey failed unexpectedly");
		}

		return respTxnTransferObj;
	}

	/**
	 * Execute the query using JPA Repository
	 * 
	 * @since 1.0
	 * @param String
	 *            configLanguageCodeKey
	 * @param String
	 *            key
	 * @param String
	 *            filter
	 * @return RefUnitofmeasureDO returns the populated RefUnitofmeasureDO object
	 */
	@CacheResult(cacheName = "REFUNITOFMEASURE_BUSKEY")
	public RefUnitofmeasureDO executeRepositoryQuery(String configLanguageCodeKey, String key, String filter) {
		RefUnitofmeasureDO dbimageRefUnitofmeasureDO = null;
		if (filter.toUpperCase().equals("ACTIVE")) {
			dbimageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository
					.findByBusinessKeyActive(configLanguageCodeKey, key);
		} else if (filter.toUpperCase().equals("INACTIVE")) {
			dbimageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository
					.findByBusinessKeyInactive(configLanguageCodeKey, key);
		} else if (filter.toUpperCase().equals("ALL")) {
			dbimageRefUnitofmeasureDO = this.theRefUnitofmeasureRepository.findByBusinessKeyAll(configLanguageCodeKey,
					key);

		}

		return dbimageRefUnitofmeasureDO;
	}

	/**
	 * perform the common validation that RefUnitofmeasureDO,
	 * RefUnitofmeasureDO.configLanguageCodeKey and RefUnitofmeasureDO.key is not null
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforeFindByBusinessKey(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"RefUnitofmeasureDO is needed in the request");
		}
		RefUnitofmeasureDO theRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
				.getRefUnitofmeasureDO();
		if (null == theRefUnitofmeasureDO.getKey() || theRefUnitofmeasureDO.getKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1006",
					"RefUnitofmeasureDO.key should not be null");
		}

		if (null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1007",
					"RefUnitofmeasureDO.configLanguageCodeKey should not be null");
		}

		// if inquiry filter is null then consider the inquiry is for ACTIVE
		// records
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter()
				|| txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter().isEmpty()) {
			txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().setInquiryFilter("ACTIVE");
		} else {
			commonValidationUtil.validateFilterValue(txnTransferObj,
					txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter());
		}
	}

	/**
	 * perform the common validation that RefUnitofmeasureDO and
	 * RefUnitofmeasureDO.configLanguageCodeKey is not null
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with database instance
	 * @throws YugandharCommonException
	 *             if RefUnitofmeasureDO object is not present in the request or
	 *             mandatory attributes business key is not present
	 */
	private void performCommonvalidationBeforefindAllRecordsByLanguageCode(TxnTransferObj txnTransferObj) {
		commonValidationUtil.validateHeaderForInternalTxn(txnTransferObj);
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1001",
					"RefUnitofmeasureDO is needed in the request");
		}
		RefUnitofmeasureDO theRefUnitofmeasureDO = (RefUnitofmeasureDO) txnTransferObj.getTxnPayload()
				.getRefUnitofmeasureDO();

		if (null == theRefUnitofmeasureDO.getConfigLanguageCodeKey()
				|| theRefUnitofmeasureDO.getConfigLanguageCodeKey().isEmpty()) {
			throw commonValidationUtil.populateValidationErrorResponse(txnTransferObj, "1007",
					"RefUnitofmeasureDO.configLanguageCodeKey should not be null");
		}

		// if inquiry filter is null then consider the inquiry is for ACTIVE
		// records
		if (null == txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter()
				|| txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter().isEmpty()) {
			txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().setInquiryFilter("ACTIVE");
		} else {
			commonValidationUtil.validateFilterValue(txnTransferObj,
					txnTransferObj.getTxnPayload().getRefUnitofmeasureDO().getInquiryFilter());
		}

		// set the current page index to zero (0) if page index is not provided
		// in the request or negative value is provided
		if (null == txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice()
				|| txnTransferObj.getTxnPayload().getPaginationIndexOfCurrentSlice() < 0) {
			txnTransferObj.getTxnPayload().setPaginationIndexOfCurrentSlice(0);
		}

		// set default page size as configured in application properties
		if (null == txnTransferObj.getTxnPayload().getPaginationPageSize()
				|| txnTransferObj.getTxnPayload().getPaginationPageSize() <= 0) {
			ConfigAppPropertiesDO theConfigAppPropertiesDO = configAppPropertiesComponent.executeRepositoryQuery(
					YugandharConfigurationProperties.com_yugandhar_pagination_referencelov_default_pagesize,
					yugandharConstants.FILTER_VALUE_ACTIVE);
			txnTransferObj.getTxnPayload().setPaginationPageSize(Integer.valueOf(theConfigAppPropertiesDO.getValue()));

		}

	}

}
