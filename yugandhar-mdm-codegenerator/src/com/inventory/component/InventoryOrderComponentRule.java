package com.inventory.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yugandhar.common.exception.YugandharCommonException;
import com.yugandhar.common.transobj.TxnTransferObj;
import com.yugandhar.msp.component.util.ReferenceTableHelper;
import com.yugandhar.common.util.CommonValidationUtil;

@Scope(value = "prototype")
@Component
public class InventoryOrderComponentRule {

	@Autowired
	protected CommonValidationUtil commonValidationUtil;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	/**
	*Pre execute persist validation method for InventoryOrderComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void prevalidateInventoryOrderCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute merge validation method for InventoryOrderComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void PrevalidateInventoryOrderCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute findbyId validation method for InventoryOrderComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateInventoryOrderCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryOrderComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryOrderCompPersist(InventoryOrderDO reqInventoryOrderDO,
			TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryOrderComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryOrderCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryOrderComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryOrderCompMerge(InventoryOrderDO reqInventoryOrderDO,
			InventoryOrderDO dbimageInventoryOrderDO, TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryOrderComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryOrderCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findbyId in InventoryOrderComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryOrderCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

}
