package com.inventory.component;
/* Generated 27 Jul, 2019 11:49:54 PM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yugandhar.mdm.extern.dobj.InventoryProductDO;

public interface InventoryProductRepository extends JpaRepository<InventoryProductDO, Long> {
	/*
	//Get All records by some id
	@Query("select dobj from InventoryProductDO dobj where <SomeId>= ?1")
	List<InventoryProductDO> findAllBy<SomeId>(String <SomeId>);
	
	//Get All ACTIVE records by some id
	@Query("select dobj from InventoryProductDO dobj where  <SomeId>= ?1 and (dobj.deletedTs is null or dobj.deletedTs > CURRENT_TIMESTAMP)")
	List<InventoryProductDO> findAllActiveBy<SomeId>(String <SomeId>);
	
	//Get All INACTIVE records by some id
	@Query("select dobj from InventoryProductDO dobj where  <SomeId>= ?1 and  dobj.deletedTs < CURRENT_TIMESTAMP")
	List<InventoryProductDO> findAllInActiveBy<SomeId>(String <SomeId>);
	*/
}
