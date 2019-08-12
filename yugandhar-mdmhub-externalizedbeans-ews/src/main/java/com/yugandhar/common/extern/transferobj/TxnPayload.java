package com.yugandhar.common.extern.transferobj;

import java.util.List;

import com.yugandhar.common.transobj.AbstractTxnPayload;
import com.yugandhar.mdm.extern.dobj.InventoryOrderDO;
import com.yugandhar.mdm.extern.dobj.InventoryProductDO;
import com.yugandhar.mdm.extern.dobj.RefUnitofmeasureDO;
import com.yugandhar.mdm.extern.dobj.SearchInventoryProductDO;


public class TxnPayload extends AbstractTxnPayload {
	
	protected InventoryOrderDO inventoryOrderDO;
	protected List<InventoryOrderDO> inventoryOrderDOList;
	protected SearchInventoryProductDO searchInventoryProductDO;
	/**
	 * @return the demoDO
	 */
	public InventoryOrderDO getInventoryOrderDO() {
		return inventoryOrderDO;
	}
	/**
	 * @param demoDO the demoDO to set
	 */
	public void setInventoryOrderDO(InventoryOrderDO inventoryOrderDO) {
		this.inventoryOrderDO = inventoryOrderDO;
	}
	/**
	 * @return the demoDOList
	 */
	public List<InventoryOrderDO> getInventoryOrderDOList() {
		return inventoryOrderDOList;
	}
	/**
	 * @param demoDOList the demoDOList to set
	 */
	public void setInventoryOrderDOList(List<InventoryOrderDO> inventoryOrderDOList) {
		this.inventoryOrderDOList = inventoryOrderDOList;
	}
	
	
	protected InventoryProductDO inventoryProductDO;
	protected List<InventoryProductDO> inventoryProductDOList;
	/**
	 * @return the demoDO
	 */
	public InventoryProductDO getInventoryProductDO() {
		return inventoryProductDO;
	}
	/**
	 * @param demoDO the demoDO to set
	 */
	public void setInventoryProductDO(InventoryProductDO inventoryProductDO) {
		this.inventoryProductDO = inventoryProductDO;
	}
	/**
	 * @return the demoDOList
	 */
	public List<InventoryProductDO> getInventoryProductDOList() {
		return inventoryProductDOList;
	}
	/**
	 * @param demoDOList the demoDOList to set
	 */
	public void setInventoryProductDOList(List<InventoryProductDO> inventoryProductDOList) {
		this.inventoryProductDOList = inventoryProductDOList;
	}
	
	protected RefUnitofmeasureDO refUnitofmeasureDO;
	protected List<RefUnitofmeasureDO> refUnitofmeasureDOList;
	/**
	 * @return the demoDO
	 */
	public RefUnitofmeasureDO getRefUnitofmeasureDO() {
		return refUnitofmeasureDO;
	}
	/**
	 * @param demoDO the demoDO to set
	 */
	public void setRefUnitofmeasureDO(RefUnitofmeasureDO refUnitofmeasureDO) {
		this.refUnitofmeasureDO = refUnitofmeasureDO;
	}
	/**
	 * @return the demoDOList
	 */
	public List<RefUnitofmeasureDO> getRefUnitofmeasureDOList() {
		return refUnitofmeasureDOList;
	}
	/**
	 * @param demoDOList the demoDOList to set
	 */
	public void setRefUnitofmeasureDOList(List<RefUnitofmeasureDO> refUnitofmeasureDOList) {
		this.refUnitofmeasureDOList = refUnitofmeasureDOList;
	}
	/**
	 * @return the searchInventoryProductDO
	 */
	public SearchInventoryProductDO getSearchInventoryProductDO() {
		return searchInventoryProductDO;
	}
	/**
	 * @param searchInventoryProductDO the searchInventoryProductDO to set
	 */
	public void setSearchInventoryProductDO(SearchInventoryProductDO searchInventoryProductDO) {
		this.searchInventoryProductDO = searchInventoryProductDO;
	}

	
}
