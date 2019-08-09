package com.inventory.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.yugandhar.mdm.extern.dobj.LeAddressAssocDO;
import com.yugandhar.mdm.extern.dobj.LePersonDO;
import com.yugandhar.mdm.extern.dobj.LePhoneAssocDO;
import com.yugandhar.mdm.extern.dobj.LegalentityDO;
import com.yugandhar.mdm.extern.dobj.PersonnamesDO;
import com.yugandhar.mdm.extern.dobj.RefGenderDO;
import com.yugandhar.mdm.extern.dobj.RefPhoneTypeDO;
import com.yugandhar.mdm.extern.dobj.RefStateProvinceDO;
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
			TxnTransferObj reqTxnTransferObj = initTxnTransferObj("admin","admin","1", "updateLegalentityBase"); // update the deleted_ts of the based LE table, all other data will not be modified
			LegalentityDO theLegalentityDObase = new LegalentityDO(legalentityDO);
			theLegalentityDObase.setDeletedTs(new Date());
			reqTxnTransferObj.getTxnPayload().setLegalentityDO(theLegalentityDObase);
			TxnTransferObj respTxnTransferObj = invokeYugandharRequestProcessor(reqTxnTransferObj);
			populateCreatepersonDropdown(model);
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
	public String searchLegalEntityRequestDO_pre(Model model, HttpServletRequest request, @ModelAttribute LegalentityDO legalentityDO ) {
		model.addAttribute("searchLegalEntityRequestDO", new SearchLegalEntityRequestDO());
		return "admin/searchperson";
	}
	
	
	@PostMapping("/searchperson_do")
	public String searchLegalEntityRequestDO_do(Model model, HttpServletRequest request, @ModelAttribute SearchLegalEntityRequestDO searchLegalEntityRequestDO ) {
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
		PersonnamesDO thePersonnamesDO = new PersonnamesDO();
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
	
}
