package com.inventory.component;
/* Generated 12 Oct, 2019 12:26:35 AM by Hibernate Tools 5.3.0.Beta2 using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yugandhar.mdm.extern.dobj.InventorySummaryDO;

public interface InventorySummaryRepository extends JpaRepository<InventorySummaryDO, Long> {

		//Get inactive records by some id 
		@Query("select dobj from InventorySummaryDO dobj where dobj.legalentityIdpk= ?1 and dobj.productId= ?2 and dobj.deletedTs < CURRENT_TIMESTAMP") 
		InventorySummaryDO findByBusinessKeyInactive(String legalentityIdpk, String productId); 

		//Get active record by some id 
		@Query("select dobj from InventorySummaryDO dobj where dobj.legalentityIdpk= ?1 and dobj.productId= ?2 and (dobj.deletedTs is null or dobj.deletedTs > CURRENT_TIMESTAMP)") 
		InventorySummaryDO findByBusinessKeyActive(String legalentityIdpk, String productId); 

		//Get records by some id 
		@Query("select dobj from InventorySummaryDO dobj where dobj.legalentityIdpk= ?1 and dobj.productId= ?2") 
		InventorySummaryDO findByBusinessKeyAll(String legalentityIdpk, String productId); 

		} 