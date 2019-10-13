package com.inventory.component;
/* Generated 12 Oct, 2019 12:26:35 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yugandhar.mdm.extern.dobj.InventoryRunningLedgerDO;

public interface InventoryRunningLedgerRepository extends JpaRepository<InventoryRunningLedgerDO, Long> {
	/*
	//Get All records by some id
	@Query("select dobj from InventoryRunningLedgerDO dobj where <SomeId>= ?1")
	List<InventoryRunningLedgerDO> findAllBy<SomeId>(String <SomeId>);
	
	//Get All ACTIVE records by some id
	@Query("select dobj from InventoryRunningLedgerDO dobj where  <SomeId>= ?1 and (dobj.deletedTs is null or dobj.deletedTs > CURRENT_TIMESTAMP)")
	List<InventoryRunningLedgerDO> findAllActiveBy<SomeId>(String <SomeId>);
	
	//Get All INACTIVE records by some id
	@Query("select dobj from InventoryRunningLedgerDO dobj where  <SomeId>= ?1 and  dobj.deletedTs < CURRENT_TIMESTAMP")
	List<InventoryRunningLedgerDO> findAllInActiveBy<SomeId>(String <SomeId>);
	*/
}
