package com.itcast.dao;

import com.itcast.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer> {

    @Query("from Customer")
    List<Customer> findCustomerAll();

    @Query("from Customer where custName = ?1")
    List<Customer> findCustomerByCustName(String custName);

    @Query("update Customer set custName = ?1 where custId = ?2")
    @Modifying
    void updateBycustId(String custNama,Long custId);

    List<Customer> findByCustNameLikeOrCustAddressIsNullOrCustLevelIsNull(String custName);

    List<Customer> findByCustNameNotLikeOrCustIndustryIsNotNull(String custName);

    Customer findByCustName(String custName);
}
