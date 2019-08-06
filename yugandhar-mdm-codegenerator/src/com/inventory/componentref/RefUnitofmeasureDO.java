package com.yugandhar.mdm.extern.dobj;
/* Generated 28 Jul, 2019 5:37:43 PM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * DO class for mapped to database ref_unitofmeasure entity
*@author Yugandhar
*@version 1.0
*@since 1.0
*@see AbstractRefUnitofmeasureDO
*/

@Entity
@Table(name = "ref_unitofmeasure", catalog = "inventorydb", uniqueConstraints = @UniqueConstraint(columnNames = { "KEY",
		"CONFIG_LANGUAGE_CODE_KEY" }))
public class RefUnitofmeasureDO extends AbstractRefUnitofmeasureDO {

	/**
	 *  Any additional attributes in the OOTB entity needs to be added in this class
	 */
	private static final long serialVersionUID = 1L;

	public RefUnitofmeasureDO() {
		super();
	}

	public RefUnitofmeasureDO(RefUnitofmeasureDO theRefUnitofmeasureDO) {
		super(theRefUnitofmeasureDO);
	}
}
