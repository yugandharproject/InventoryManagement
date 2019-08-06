package com.inventory.componentref;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.msp.component.util.ReferenceTableHelper;
import com.yugandhar.common.util.CommonValidationUtil;

@Scope(value = "prototype")
@Component
public class RefUnitofmeasureComponentRule {

	@Autowired
	protected CommonValidationUtil commonValidationUtil;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	/**
	*Pre execute persist validation method for RefUnitofmeasureComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateRefUnitofmeasureCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute merge validation method for RefUnitofmeasureComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void PrevalidateRefUnitofmeasureCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute findbyId validation method for RefUnitofmeasureComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateRefUnitofmeasureCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute findByBusinessKey validation method for RefUnitofmeasureComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preValidateRefUnitofmeasurefindByBusinessKey(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in RefUnitofmeasureComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteRefUnitofmeasureCompPersist(RefUnitofmeasureDO reqRefUnitofmeasureDO,
			TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in RefUnitofmeasureComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteRefUnitofmeasureCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in RefUnitofmeasureComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteRefUnitofmeasureCompMerge(RefUnitofmeasureDO reqRefUnitofmeasureDO,
			RefUnitofmeasureDO dbimageRefUnitofmeasureDO, TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in RefUnitofmeasureComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteRefUnitofmeasureCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findbyId in RefUnitofmeasureComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteRefUnitofmeasureCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findByBusinessKey in RefUnitofmeasureComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteRefUnitofmeasurefindByBusinessKey(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findByBusinessKey in RefUnitofmeasureComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteRefUnitofmeasurefindByBusinessKey(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

}
