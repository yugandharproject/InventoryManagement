package com.yugandhar.mdm.corecomponentref;
/* Generated Sep 8, 2017 3:19:57 PM by Hibernate Tools 5.2.1.Final using Yugandhar custom templates. 
Generated and to be used in accordance with Yugandhar Licensing Terms. */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yugandhar.mdm.extern.dobj.RefStateProvinceDO;

public interface RefStateProvinceRepository extends JpaRepository<RefStateProvinceDO, Long> {

	//Get All records by Language Code
	@Query("select dobj from RefStateProvinceDO dobj where configLanguageCodeKey= ?1  and countryIsoRefkey= ?2")
	Page<RefStateProvinceDO> findAllByLanguageCodeAndCountry(String configLanguageCodeKey, String countryIsoRefkey, Pageable pageable);

	//Get All ACTIVE records by Language Code
	@Query("select dobj from RefStateProvinceDO dobj where  configLanguageCodeKey= ?1 and countryIsoRefkey= ?2 and (dobj.deletedTs is null or dobj.deletedTs > CURRENT_TIMESTAMP)")
	Page<RefStateProvinceDO> findAllActiveByLanguageCodeAndCountry(String configLanguageCodeKey, String countryIsoRefkey, Pageable pageable);

	//Get All INACTIVE records by Language Code
	@Query("select dobj from RefStateProvinceDO dobj where  configLanguageCodeKey= ?1  and countryIsoRefkey= ?2 and  dobj.deletedTs < CURRENT_TIMESTAMP")
	Page<RefStateProvinceDO> findAllInActiveByLanguageCodeAndCountry(String configLanguageCodeKey, String countryIsoRefkey, Pageable pageable);

	//find record based on business key
	@Query("select dobj from RefStateProvinceDO dobj where dobj.configLanguageCodeKey = ?1 and dobj.key= ?2 and (dobj.deletedTs is null or dobj.deletedTs > CURRENT_TIMESTAMP) ")
	RefStateProvinceDO findByBusinessKeyActive(String configLanguageCodeKey, String key);

	//find INACTIVE record based on business key
	@Query("select dobj from RefStateProvinceDO dobj where dobj.configLanguageCodeKey = ?1 and dobj.key= ?2 and dobj.deletedTs < CURRENT_TIMESTAMP ")
	RefStateProvinceDO findByBusinessKeyInactive(String configLanguageCodeKey, String key);

	//find ACTIVE record based on business key
	@Query("select dobj from RefStateProvinceDO dobj where dobj.configLanguageCodeKey = ?1 and dobj.key= ?2")
	RefStateProvinceDO findByBusinessKeyAll(String configLanguageCodeKey, String key);

}
