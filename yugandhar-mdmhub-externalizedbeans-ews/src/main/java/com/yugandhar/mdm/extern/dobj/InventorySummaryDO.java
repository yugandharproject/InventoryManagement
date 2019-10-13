package com.yugandhar.mdm.extern.dobj;
/* Generated 12 Oct, 2019 12:26:35 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.yugandhar.mdm.abstractdobj.AbstractInventorySummaryDO;

/**
 * DO class for mapped to database inventory_summary entity
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see AbstractInventorySummaryDO
*/

@Entity
@Table(name = "inventory_summary", catalog = "inventorydb")
public class InventorySummaryDO extends AbstractInventorySummaryDO {

	/**
	 *  Any additional attributes in the OOTB entity needs to be added in this class
	 */
	private static final long serialVersionUID = 1L;

	public InventorySummaryDO() {
		super();
	}

	public InventorySummaryDO(InventorySummaryDO theInventorySummaryDO) {
		super(theInventorySummaryDO);
	}
}
