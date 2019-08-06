package com.yugandhar.mdm.extern.dobj;
/* Generated 27 Jul, 2019 11:49:54 PM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.yugandhar.mdm.abstractdobj.AbstractInventoryOrderDO;

/**
 * DO class for mapped to database inventory_order entity
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see AbstractInventoryOrderDO
*/

@Entity
@Table(name = "inventory_order", catalog = "inventorydb")
public class InventoryOrderDO extends AbstractInventoryOrderDO {

	/**
	 *  Any additional attributes in the OOTB entity needs to be added in this class
	 */
	private static final long serialVersionUID = 1L;

	public InventoryOrderDO() {
		super();
	}

	public InventoryOrderDO(InventoryOrderDO theInventoryOrderDO) {
		super(theInventoryOrderDO);
	}
}