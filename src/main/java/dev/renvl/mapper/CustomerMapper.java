package dev.renvl.mapper;

import dev.renvl.model.Customer;
import org.apache.ibatis.annotations.*;


@Mapper
public interface CustomerMapper {

    @Insert("INSERT INTO customer(customer_id) VALUES (#{customerId})")
    void insert(@Param("customerId") int customerId);

    @Result(property = "customerId", column = "customer_id", id = true)
    @Select("SELECT * FROM customer WHERE customer_id = #{customerId}")
    Customer getCustomer(@Param("customerId") String customerId);

}
