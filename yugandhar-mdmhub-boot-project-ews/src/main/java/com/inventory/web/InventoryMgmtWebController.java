package com.inventory.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yugandhar.common.constant.yugandharConstants;
import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.extern.transferobj.TxnPayload;
import com.yugandhar.common.transobj.TxnHeader;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;
import com.yugandhar.mdm.extern.dobj.LeAddressAssocDO;
import com.yugandhar.mdm.extern.dobj.LePersonDO;
import com.yugandhar.mdm.extern.dobj.LePhoneAssocDO;
import com.yugandhar.mdm.extern.dobj.LegalentityDO;
import com.yugandhar.mdm.extern.dobj.PersonnamesDO;
import com.yugandhar.mdm.extern.dobj.RefGenderDO;
import com.yugandhar.mdm.extern.dobj.RefPhoneTypeDO;
import com.yugandhar.mdm.extern.dobj.RefStateProvinceDO;
import com.yugandhar.mdm.extern.dobj.RefUnitofmeasureDO;
import com.yugandhar.mdm.extern.dobj.SearchInventoryProductDO;
import com.yugandhar.mdm.extern.dobj.SearchLegalEntityRequestDO;
import com.yugandhar.mdm.misc.dobj.CommonValidationResponse;
import com.yugandhar.rest.controller.RequestProcessor;

@Scope(value = "prototype")
@Controller
@RequestMapping(value="/admin")
public class InventoryMgmtWebController {
	
	private static final Logger logger = LoggerFactory.getLogger(yugandharConstants.YUGANDHAR_COMMON_LOGGER);
	
	@Autowired
	RequestProcessor requestProcessor;
	
	@Autowired
	CommonValidationUtil commonValidationUtil;
	CommonValidationResponse commonValidationResponse;

	@GetMapping("/")
	public String showLandingPage(Model model, HttpServletRequest request) {
		return "admin/home";
	}
	
	@GetMapping("/createperson")
	public String createperson_pre(Model model, HttpServletRequest request, @ModelAttribute LegalentityDO legalentityDO ) {
		populateCreatepersonDropdown(model);
		model.addAttribute("legalentityDO", initCreatePerson());
		
		return "admin/createperson";
	}

	
	@PostMapping("/createperson_do")
	public String createperson_do(Model model, HttpServletRequest request, 
			@ModelAttribute LegalentityDO legalentityDO ) {
		
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "createLegalEntity");
		legalentityDO.setEntityObjectTypeRefkey("1"); // LE tye person
		PersonnamesDO thePersonnamesDO = legalentityDO.getLePersonDO().getPersonnamesDOList().get(0);
		thePersonnamesDO.setPersonnameTypeRefkey("1"); // name usage type defaulted to registered Name
		legalentityDO.setDisplayName(thePersonnamesDO.getNameOne());
		reqTxnTransferObj.getTxnPayload().setLegalentityDO(legalentityDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		populateCreatepersonDropdown(model);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			addInfoMessage(model, UIMessages.SUCCESS_PERSON_CREATED);
			model.addAttribute("legalentityDO", respTxnTransferObj.getTxnPayload().getLegalentityDO());
		}
		return "admin/createperson";
	}
	
	
	@PostMapping("/editdeleteperson")
	public String editdeleteperson_pre(Model model, HttpServletRequest request,
			@RequestParam(value="editperson", required=false) String legalEntityIdPkToEdit , @RequestParam(value="deleteperson", required=false) Integer deletePersonElementat) {

		LegalentityDO legalentityDO = new LegalentityDO();
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "retrieveLegalEntityByLegalEntityId");
		legalentityDO.setIdPk(legalEntityIdPkToEdit);
		legalentityDO.setInquiryLevel("103");
		reqTxnTransferObj.getTxnPayload().setLegalentityDO(legalentityDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		populateCreatepersonDropdown(model);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			model.addAttribute("legalentityDO", respTxnTransferObj.getTxnPayload().getLegalentityDO());
		}
		
		return "admin/editperson";
	}
	
	
	@PostMapping("/editdeleteperson_do")
	public String editdeleteperson_do(Model model, HttpServletRequest request, @ModelAttribute() LegalentityDO legalentityDO ,
			@RequestParam(value="action", required=false) String strRequestedAction) {
		if(strRequestedAction.equals("editsave")) {
			
			TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "updateLegalEntity");
			reqTxnTransferObj.getTxnPayload().setLegalentityDO(legalentityDO);
			TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);

			populateCreatepersonDropdown(model);
			if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
				addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
				model.addAttribute("legalentityDO", legalentityDO);
			} else {
				addInfoMessage(model, UIMessages.SUCCESS_PERSON_UPDATED);
				model.addAttribute("legalentityDO", respTxnTransferObj.getTxnPayload().getLegalentityDO());
			}
			
		} else if(strRequestedAction.equals("delete")) {
			TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "updateLegalentityBase"); // update the deleted_ts of the base table, all other data will not be modified
			LegalentityDO theLegalentityDObase = new LegalentityDO(legalentityDO);
			theLegalentityDObase.setDeletedTs(new Date());
			reqTxnTransferObj.getTxnPayload().setLegalentityDO(theLegalentityDObase);
			TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
			populateCreatepersonDropdown(model);
			if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
				addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
				model.addAttribute("legalentityDO", legalentityDO);
			} else {
				addInfoMessage(model, UIMessages.SUCCESS_PERSON_DELETED);
				model.addAttribute("legalentityDO", legalentityDO);
				model.addAttribute("readonlyform", "true");
			}
			
			
			
		}
		
		
		return "admin/editperson";
	}
	
	@GetMapping("/searchperson")
	public String searchperson(Model model, HttpServletRequest request, @ModelAttribute LegalentityDO legalentityDO ) {
		model.addAttribute("searchLegalEntityRequestDO", new SearchLegalEntityRequestDO());
		return "admin/searchperson";
	}
	
	
	@PostMapping("/searchperson_do")
	public String searchperson_do(Model model, HttpServletRequest request, @ModelAttribute SearchLegalEntityRequestDO searchLegalEntityRequestDO ) {
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "searchLegalEntityByLEAttributes");
		searchLegalEntityRequestDO.setInquiryLevel("103");
		reqTxnTransferObj.getTxnPayload().setSearchLegalEntityRequestDO(searchLegalEntityRequestDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);

		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
			return "admin/searchperson";
		} else {
			model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload());
		}
		return "admin/searchpersonresults";
	}
	
	@GetMapping("/createproduct")
	public String createprodut_pre(Model model, HttpServletRequest request, @ModelAttribute InventoryProductDO inventoryProductDO ) {
		populateCreateProductDropdown(model);
		model.addAttribute("inventoryProductDO", new InventoryProductDO());
		return "admin/createproduct";
	}
	
	@PostMapping("/createproduct_do")
	public String createproduct_do(Model model, HttpServletRequest request, 
			@ModelAttribute InventoryProductDO inventoryProductDO ) {
		populateCreateProductDropdown(model);
		//if quantity recieved is not set then set it to zero
		if(null == inventoryProductDO.getQuantityRecevied()) {
			inventoryProductDO.setQuantityRecevied(0);
		}
		//if quantity in hand is not set then set it to same as Quantity recieved
		if(null == inventoryProductDO.getQuantityInhand()) {
			inventoryProductDO.setQuantityInhand(inventoryProductDO.getQuantityRecevied());
		}
		
		
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "createInventoryProductBase");
		reqTxnTransferObj.getTxnPayload().setInventoryProductDO(inventoryProductDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			addInfoMessage(model, UIMessages.SUCCESS_PRODUCT_CREATED);
			model.addAttribute("inventoryProductDO", respTxnTransferObj.getTxnPayload().getInventoryProductDO());
		}
		return "admin/createproduct";
	}
		


	@GetMapping("/searchproduct")
	public String searchproduct_pre(Model model, HttpServletRequest request, @ModelAttribute SearchInventoryProductDO searchInventoryProductDO ) {
		model.addAttribute("searchInventoryProductDO", new SearchInventoryProductDO());
		return "admin/searchproduct"; //searchproduct.html
	}

	
	@PostMapping("/searchproduct_do")
	public String searchproduct_do(Model model, HttpServletRequest request, @ModelAttribute SearchInventoryProductDO searchInventoryProductDO ) {
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "searchInventoryProduct");
		searchInventoryProductDO.setInquiryFilter("ACTIVE");
		reqTxnTransferObj.getTxnPayload().setSearchInventoryProductDO(searchInventoryProductDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);

		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
			return "admin/searchperson";
		} else {
			model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload());
		}
		return "admin/searchproductresults";
	}
	
	
	@PostMapping("/editdeleteproduct")
	public String editdeleteproduct_pre(Model model, HttpServletRequest request,
			@RequestParam(value="editproduct", required=false) String productIdPkToEdit ,
			@RequestParam(value="deleteproduct", required=false) Integer deleteProductElementat) {

		InventoryProductDO inventoryProductDO = new InventoryProductDO();
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "retrieveInventoryProductBase");
		inventoryProductDO.setIdPk(productIdPkToEdit);
		reqTxnTransferObj.getTxnPayload().setInventoryProductDO(inventoryProductDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		populateCreateProductDropdown(model);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			model.addAttribute("inventoryProductDO", respTxnTransferObj.getTxnPayload().getInventoryProductDO());
		}
		
		return "admin/editproduct";
	}
	
	@PostMapping("/editdeleteproduct_do")
	public String editdeleteperson_do(Model model, HttpServletRequest request, @ModelAttribute() InventoryProductDO inventoryProductDO ,
			@RequestParam(value="action", required=false) String strRequestedAction) {
		if(strRequestedAction.equals("editsave")) {
			
			TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "updateInventoryProductBase");
			reqTxnTransferObj.getTxnPayload().setInventoryProductDO(inventoryProductDO);
			TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);

			populateCreateProductDropdown(model);
			if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
				addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
				model.addAttribute("inventoryProductDO", inventoryProductDO);
			} else {
				addInfoMessage(model, UIMessages.SUCCESS_PRODUCT_UPDATED);
				model.addAttribute("inventoryProductDO", respTxnTransferObj.getTxnPayload().getInventoryProductDO());
			}
			
		} else if(strRequestedAction.equals("delete")) {
			TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "updateInventoryProductBase"); // update the deleted_ts of the base table, all other data will not be modified
			InventoryProductDO theInventoryProductDObase = new InventoryProductDO(inventoryProductDO);
			theInventoryProductDObase.setDeletedTs(new Date());
			reqTxnTransferObj.getTxnPayload().setInventoryProductDO(theInventoryProductDObase);
			TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
			populateCreateProductDropdown(model);
			if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
				addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
				model.addAttribute("inventoryProductDO", inventoryProductDO);
			} else {
				addInfoMessage(model, UIMessages.SUCCESS_PRODUCT_DELETED);
				model.addAttribute("inventoryProductDO", inventoryProductDO);
				model.addAttribute("readonlyform", "true");
			}
		}
		return "admin/editproduct";
	}
	
	public LegalentityDO initCreatePerson() {
		LegalentityDO legalentityDO = new LegalentityDO();
		//create blank addresses
		List<LeAddressAssocDO> leAddressAssocDOList = new ArrayList<LeAddressAssocDO>();
		LeAddressAssocDO theLeAddressAssocDO_pimary = new LeAddressAssocDO(); 
		theLeAddressAssocDO_pimary.setAddressTypeRefkey("1"); //All Seasons regular address
		theLeAddressAssocDO_pimary.setAddressSubtypeRefkey("1"); //Primary
		leAddressAssocDOList.add(theLeAddressAssocDO_pimary);
		
		//person and person name
		LePersonDO theLePersonDO = new LePersonDO();
		ArrayList<PersonnamesDO> thePersonnamesDOList = new ArrayList<PersonnamesDO>();
		theLePersonDO.setPersonnamesDOList(thePersonnamesDOList);
		legalentityDO.setLePersonDO(theLePersonDO);
		
		//create blank phone numbers
		List<LePhoneAssocDO> lePhoneAssocDOList = new ArrayList<LePhoneAssocDO>();
		LePhoneAssocDO theLePhoneAssocDO_mobile = new LePhoneAssocDO();
		LePhoneAssocDO theLePhoneAssocDO_alternate = new LePhoneAssocDO();
		LePhoneAssocDO theLePhoneAssocDO_email = new LePhoneAssocDO();
		theLePhoneAssocDO_mobile.setPhoneTypeRefkey("1");
		theLePhoneAssocDO_alternate.setPhoneTypeRefkey("2");
		theLePhoneAssocDO_email.setPhoneTypeRefkey("3");
		lePhoneAssocDOList.add(theLePhoneAssocDO_mobile);
		lePhoneAssocDOList.add(theLePhoneAssocDO_alternate);
		lePhoneAssocDOList.add(theLePhoneAssocDO_email);
		
		legalentityDO.setLePhoneAssocDOList(lePhoneAssocDOList);
		legalentityDO.setLeAddressAssocDOList(leAddressAssocDOList);
		return legalentityDO;
	}
	
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
	
	
	/**
	 * This method invokes yugandhar request processor 
	 * 
	 * @since 1.0
	 * @param txnTransferObj
	 *            Transfer Object TxnTransferObj instance
	 * @return txnTransferObj Returns the Transfer Object TxnTransferObj
	 *         instance populated with persisted instance
	 * @throws YugandharCommonException
	 *             if transaction fails due to any reason
	 *
	 */
	
	public TxnTransferObj invokeYugandharRequestProcessor(TxnTransferObj txnTransferObj) {
		try {
			return requestProcessor.processMessage(txnTransferObj);
		} catch (Exception e) {

			logger.error("Transaction failed", e);
			TxnTransferObj txnErrTransferObj = new TxnTransferObj();
			txnErrTransferObj.setTxnHeader(txnTransferObj.getTxnHeader());
			if (e instanceof YugandharCommonException) {
				YugandharCommonException yce = (YugandharCommonException) e;
				txnErrTransferObj.getTxnPayload().setErrorResponseObj(
						commonValidationUtil.createCommonValidationResponseFromYugException(txnErrTransferObj, yce));

			} else {
				txnErrTransferObj.getTxnPayload().setErrorResponseObj(
						commonValidationUtil.createCommonValidationResponseFromException(txnErrTransferObj, "1", e));

			}
			return txnErrTransferObj;

		}

	}
	
	public void addInfoMessage(Model model, String message) {
		UIMessageWrapper theUIMessageWrapper = new UIMessageWrapper();
		theUIMessageWrapper.getSuccessMessageList().add(message);
		model.addAttribute("uimessagewrapper", theUIMessageWrapper);
	}
	
	public void addErrorMessage(Model model, CommonValidationResponse theCommonValidationResponse) {
		UIMessageWrapper theUIMessageWrapper = new UIMessageWrapper();
		theUIMessageWrapper.getErrorMessageList().add(theCommonValidationResponse.getErrorMessage() );
		theUIMessageWrapper.getErrorMessageList().add(theCommonValidationResponse.getAdditionalErrorMessage() );
		model.addAttribute("uimessagewrapper", theUIMessageWrapper);
	}
	
	public void addErrorMessage(Model model, String message) {
		UIMessageWrapper theUIMessageWrapper= new UIMessageWrapper();
		theUIMessageWrapper.getErrorMessageList().add(message);
		model.addAttribute("uimessagewrapper", theUIMessageWrapper);
	}
	
	private void populateCreatepersonDropdown(Model model) {
		TxnTransferObj reqTxnTransferObj;
		TxnTransferObj respTxnTransferObj;
		TxnPayload respTxnPayload = new TxnPayload();
		// Gender, Phone Number Contact Details, Country, State-province
		reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "findAllRefGenderByLanguageCodeBase");
		RefGenderDO refGenderDO = new RefGenderDO();
		refGenderDO.setConfigLanguageCodeKey("1");
		reqTxnTransferObj.getTxnPayload().setRefGenderDO(refGenderDO);
		respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			respTxnPayload.setRefGenderDOList( respTxnTransferObj.getTxnPayload().getRefGenderDOList());
		}
		
		// phone
		
		reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "findAllRefPhoneTypeByLanguageCodeBase");
		RefPhoneTypeDO refPhoneTypeDO = new RefPhoneTypeDO();
		refPhoneTypeDO.setConfigLanguageCodeKey("1");
		reqTxnTransferObj.getTxnPayload().setRefPhoneTypeDO(refPhoneTypeDO);
		respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			respTxnPayload.setRefPhoneTypeDOList( respTxnTransferObj.getTxnPayload().getRefPhoneTypeDOList());
		}
		
		// state-province
		reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "findAllRefStateProvinceByLanguageCodeBase");
		RefStateProvinceDO refStateProvinceDO = new RefStateProvinceDO();
		refStateProvinceDO.setConfigLanguageCodeKey("1");
		refStateProvinceDO.setCountryIsoRefkey("356"); // country defaulted to India
		reqTxnTransferObj.getTxnPayload().setRefStateProvinceDO(refStateProvinceDO);
		respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			respTxnPayload.setRefStateProvinceDOList( respTxnTransferObj.getTxnPayload().getRefStateProvinceDOList());
		}
		
		// Country
		/*reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "findAllRefCountryIsoByLanguageCodeBase");
		RefCountryIsoDO refCountryIsoDO = new RefCountryIsoDO();
		refCountryIsoDO.setConfigLanguageCodeKey("1");
		reqTxnTransferObj.getTxnPayload().setRefCountryIsoDO(refCountryIsoDO);
		respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){			
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
			respTxnPayload.setRefCountryIsoDOList( respTxnTransferObj.getTxnPayload().getRefCountryIsoDOList());
		}*/
		//set model response
		model.addAttribute("txnPayload", respTxnPayload);
	}
	
	
	
	private void populateCreateProductDropdown(Model model) {
		TxnTransferObj reqTxnTransferObj;
		TxnTransferObj respTxnTransferObj;
		TxnPayload respTxnPayload = new TxnPayload();
		// state-province
		reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "findAllRefUnitofmeasureByLanguageCodeBase");
		RefUnitofmeasureDO refUnitofmeasureDO = new RefUnitofmeasureDO();
		refUnitofmeasureDO.setConfigLanguageCodeKey("1");
		reqTxnTransferObj.getTxnPayload().setRefUnitofmeasureDO(refUnitofmeasureDO);
		respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){
		addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		} else {
		respTxnPayload.setRefUnitofmeasureDOList( respTxnTransferObj.getTxnPayload().getRefUnitofmeasureDOList());
		}		
		model.addAttribute("txnPayload", respTxnPayload);
		
	}
	
	
	//Issue a Product 
	@GetMapping("/maintaininvt") 
	public String maintaininvt_pre(Model model, HttpServletRequest request, @ModelAttribute 
	LegalentityDO legalentityDO, @ModelAttribute SearchInventoryProductDO searchInventoryProductDO ) { 

	TxnPayload theTxnPayload= new TxnPayload(); 
	theTxnPayload.setSearchInventoryProductDO(new SearchInventoryProductDO()); 
	theTxnPayload.setSearchLegalEntityRequestDO(new SearchLegalEntityRequestDO()); 

	InventoryRunningLedgerDO theInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 

	theInventoryRunningLedgerDO.setInquiryFilter("ACTIVE"); 
	TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "fetchInventoryRunningLedger"); 
	reqTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO (theInventoryRunningLedgerDO); 

	TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 

	if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){ 

		addErrorMessage(model, respTxnTransferObj.getTxnPayload().getErrorResponseObj()); 
		TxnPayload tempTxnPayload= new TxnPayload(); 
		model.addAttribute("txnPayload", tempTxnPayload); 
		return "admin/maintaininvt"; 

	} else { 
		theTxnPayload.setInventoryRunningLedgerDOList(respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDOList()); 
		model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
		} 
	theInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
	theInventoryRunningLedgerDO.setTransactionType("ISSUE");
	theTxnPayload.setInventoryRunningLedgerDO(theInventoryRunningLedgerDO);
	model.addAttribute("txnPayload", theTxnPayload); 
	model.addAttribute("ledgerTablepageNumber", 4); 
	return "admin/maintaininvt"; 
	}
	
	
	
	@PostMapping("/maintaininvt_do") 
	public String maintaininvt_do(Model model, HttpServletRequest request, @ModelAttribute TxnPayload txnPayload, 
			@RequestParam(value="searchproduct", required=false) String searchproductflag, 
			@RequestParam(value="searchperson", required=false) String searchpersonflag, 
			@RequestParam(value="btnselectproduct", required=false) String btnselectproductIdpk, 
			@RequestParam(value="btnselectperson", required=false) String btnselectpersonIdpk, 
			@RequestParam(value="action", required=false) String straction) { 

	if(null != searchproductflag && searchproductflag.equals("Y")) {
	//search the product 
	LegalentityDO reqLegalentityDO = txnPayload.getLegalentityDO(); 
	model.addAttribute("searchproductresult", "Y"); 
	TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "searchInventoryProduct");
	txnPayload.getSearchInventoryProductDO().setInquiryFilter("ACTIVE"); 
	reqTxnTransferObj.getTxnPayload().setSearchInventoryProductDO(txnPayload.getSearchInventoryProductDO()); 
	TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 

	if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)) 
	{ 
		addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj()); 
		return "admin/maintaininvt"; 
	} else { 

			respTxnTransferObj.getTxnPayload().setLegalentityDO(reqLegalentityDO); 
			respTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(txnPayload.getInventoryRunningLedgerDO()); 
			model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
			} 

	} else if(null != btnselectproductIdpk) {
		LegalentityDO reqLegalentityDO = txnPayload.getLegalentityDO(); 
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "retrieveInventoryProductBase"); 
		InventoryProductDO theInventoryProductDO = new InventoryProductDO(); 
		theInventoryProductDO.setIdPk(btnselectproductIdpk); 
		reqTxnTransferObj.getTxnPayload().setInventoryProductDO(theInventoryProductDO); 
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);

	if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)) {
		addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		return "admin/maintaininvt"; 
	} else { 
		respTxnTransferObj.getTxnPayload().setLegalentityDO(reqLegalentityDO); 
		respTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(txnPayload.getInventoryRunningLedgerDO()); 
		model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
	}

	} else if(null != searchpersonflag && searchpersonflag.equals("Y")) {
		InventoryProductDO reqInventoryProductDO = txnPayload.getInventoryProductDO(); 
		//search the person
		model.addAttribute("searchpersonresult", "Y"); 
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "searchLegalEntityByLEAttributes"); 
		txnPayload.getSearchLegalEntityRequestDO().setInquiryFilter("ACTIVE"); 
		txnPayload.getSearchLegalEntityRequestDO().setInquiryLevel("103"); 
		reqTxnTransferObj.getTxnPayload().setSearchLegalEntityRequestDO( txnPayload.getSearchLegalEntityRequestDO()); 
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){ 
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj()); 
			return "admin/maintaininvt"; 
		} else { 
			respTxnTransferObj.getTxnPayload().setInventoryProductDO(reqInventoryProductDO); 
			respTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(txnPayload.getInventoryRunningLedgerDO());
			model.addAttribute("txnPayload",respTxnTransferObj.getTxnPayload());
		}

	} else if(null != btnselectpersonIdpk) {
		InventoryProductDO reqInventoryProductDO  = txnPayload.getInventoryProductDO(); 
		LegalentityDO legalentityDO = new LegalentityDO(); 
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "retrieveLegalEntityByLegalEntityId"); 
		legalentityDO.setIdPk(btnselectpersonIdpk); 
		legalentityDO.setInquiryLevel("103"); 
		reqTxnTransferObj.getTxnPayload().setLegalentityDO(legalentityDO); 
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
			if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){
					addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
					return "admin/maintaininvt"; 

			} else { 
				respTxnTransferObj.getTxnPayload().setInventoryProductDO(reqInventoryProductDO); 
				respTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(txnPayload.getInventoryRunningLedgerDO());
				 model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
			}
	} else if(null != straction && straction.equals("save")) { 

	InventoryRunningLedgerDO reqInventoryRunningLedgerDO= txnPayload.getInventoryRunningLedgerDO(); 
	reqInventoryRunningLedgerDO.setLegalentityIdpk(txnPayload.getLegalentityDO().getIdPk()); 
	reqInventoryRunningLedgerDO.setProductId(txnPayload. getInventoryProductDO().getIdPk()); 
	TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin", "1", "maintainInventoryService"); 
	reqTxnTransferObj. getTxnPayload().setInventoryRunningLedgerDO(reqInventoryRunningLedgerDO);
	
	TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 
	if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){ 
		addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj());
		return "admin/maintaininvt"; 
	} else {
		addInfoMessage(model, UIMessages.TXN_SUCCESSFULL);
		respTxnTransferObj.getTxnPayload().setSearchInventoryProductDO(new SearchInventoryProductDO()); 
		respTxnTransferObj. getTxnPayload().setSearchLegalEntityRequestDO( new SearchLegalEntityRequestDO()); 
		model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
	}
	}
	return "admin/maintaininvt"; 

	} 
	
	//Display Running Ledger 
		@GetMapping("/runningledger") 
		public String runningledger_pre(Model model, HttpServletRequest request, @ModelAttribute LegalentityDO legalentityDO, 
		@ModelAttribute SearchInventoryProductDO searchInventoryProductDO){ 
			TxnPayload theTxnPayload= new TxnPayload(); 

		InventoryRunningLedgerDO theInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
		theInventoryRunningLedgerDO.setInquiryFilter("ACTIVE"); 
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1" , "fetchInventoryRunningLedger"); 
		reqTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(theInventoryRunningLedgerDO);
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){ 
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj()); 
			return "admin/maintaininvt"; 
		} else { 
			theTxnPayload.setInventoryRunningLedgerDOList(respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDOList()); 
		} 
		model.addAttribute("txnPayload", theTxnPayload);
		model.addAttribute("ledgerTablepageNumber", 1); 
		model.addAttribute("pagesize", 2); 
		return "admin/runningledger"; 
		}

		
		@PostMapping("/runningledger_do") 
		public String runningledger_do(Model model, HttpServletRequest request, 
			@ModelAttribute LegalentityDO legalentityDO, 
		@ModelAttribute SearchInventoryProductDO searchInventoryProductDO, 
		@RequestParam(value="btnledgerpage", required=false) Integer ledgerTablepageNumber, 
		@RequestParam(value="pagesize", required=false) Integer pagesize) { 
		TxnPayload theTxnPayload= new TxnPayload(); 
		InventoryRunningLedgerDO theInventoryRunningLedgerDO = new InventoryRunningLedgerDO(); 
		theInventoryRunningLedgerDO.setInquiryFilter("ACTIVE"); 
		TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "fetchInventoryRunningLedger"); 
		reqTxnTransferObj.getTxnPayload().setPaginationIndexOfCurrentSlice(ledgerTablepageNumber-1);
		reqTxnTransferObj.getTxnPayload().setPaginationPageSize(2);
		
		reqTxnTransferObj.getTxnPayload().setInventoryRunningLedgerDO(theInventoryRunningLedgerDO); 
		TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj); 
		
		
		
		if(respTxnTransferObj.getResponseCode().equals(yugandharConstants.RESPONSE_CODE_FAIL)){ 
			addErrorMessage(model,respTxnTransferObj.getTxnPayload().getErrorResponseObj()); 
			return "admin/maintaininvt"; 
		} else { 
		theTxnPayload.setInventoryRunningLedgerDOList( respTxnTransferObj.getTxnPayload().getInventoryRunningLedgerDOList()); 
		model.addAttribute("txnPayload", respTxnTransferObj.getTxnPayload()); 
		}
		
		model.addAttribute("txnPayload", theTxnPayload); 
		model.addAttribute("ledgerTablepageNumber", ledgerTablepageNumber); 
		model.addAttribute("pagesize", pagesize); 
		return "admin/runningledger"; 
		}
	

}