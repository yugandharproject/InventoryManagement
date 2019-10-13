package com.inventory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.mdm.component.util.ReferenceTableHelper;
import com.yugandhar.common.util.CommonValidationUtil;
import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;

@Scope(value = "prototype")
@Component
public class InventoryRunningLedgerComponentRule {

	@Autowired
	protected CommonValidationUtil commonValidationUtil;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	/**
	*Pre execute persist validation method for InventoryRunningLedgerComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void prevalidateInventoryRunningLedgerCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute merge validation method for InventoryRunningLedgerComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void PrevalidateInventoryRunningLedgerCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute findbyId validation method for InventoryRunningLedgerComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateInventoryRunningLedgerCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryRunningLedgerComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryRunningLedgerCompPersist(InventoryRunningLedgerDO reqInventoryRunningLedgerDO,
			TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryRunningLedgerComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryRunningLedgerCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryRunningLedgerComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryRunningLedgerCompMerge(InventoryRunningLedgerDO reqInventoryRunningLedgerDO,
			InventoryRunningLedgerDO dbimageInventoryRunningLedgerDO, TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryRunningLedgerComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryRunningLedgerCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findbyId in InventoryRunningLedgerComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryRunningLedgerCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

}
