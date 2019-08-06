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
public class InventoryProductComponentRule {

	@Autowired
	protected CommonValidationUtil commonValidationUtil;

	@Autowired
	ReferenceTableHelper referenceTableHelper;

	/**
	*Pre execute persist validation method for InventoryProductComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void prevalidateInventoryProductCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute merge validation method for InventoryProductComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	* @throws YugandharCommonException 
	*/
	public void PrevalidateInventoryProductCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub
	}

	/**
	*Pre execute findbyId validation method for InventoryProductComp to validate mandatory attributes etc
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void prevalidateInventoryProductCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryProductComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryProductCompPersist(InventoryProductDO reqInventoryProductDO,
			TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for persist in InventoryProductComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryProductCompPersit(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryProductComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void preExecuteInventoryProductCompMerge(InventoryProductDO reqInventoryProductDO,
			InventoryProductDO dbimageInventoryProductDO, TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for merge in InventoryProductComp
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryProductCompMerge(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

	/**
	*Pre execute rule for findbyId in InventoryProductComp 
	* This method is modularized in respective rule class
	*@throws YugandharCommonException 
	*/
	public void postExecuteInventoryProductCompFindById(TxnTransferObj txnTransferObj) {
		// TODO Auto-generated method stub

	}

}
