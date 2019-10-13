package com.inventory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.mdm.component.util.ReferenceTableHelper;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.extern.dobj.InventorySummaryDO;

@Scope(value = "prototype")
@Component
public class InventorySummaryComponentRule {

	@Autowired
	protected CommonValidationUtil commonValidationUtil;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	/**
	*Pre execute persist validation method for InventorySummaryComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void prevalidateInventorySummaryCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute merge validation method for InventorySummaryComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void PrevalidateInventorySummaryCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute findbyId validation method for InventorySummaryComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateInventorySummaryCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventorySummaryComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventorySummaryCompPersist(InventorySummaryDO reqInventorySummaryDO,
			TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventorySummaryComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventorySummaryCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventorySummaryComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventorySummaryCompMerge(InventorySummaryDO reqInventorySummaryDO,
			InventorySummaryDO dbimageInventorySummaryDO, TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventorySummaryComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventorySummaryCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findbyId in InventorySummaryComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventorySummaryCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

}
