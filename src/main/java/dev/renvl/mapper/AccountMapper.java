package dev.renvl.mapper;

import dev.renvl.model.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account(country, customer_id) VALUES (#{country}, #{customerId})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId", keyColumn = "account_id")
    void insert(Account account);

    @Result(property = "accountId", column = "account_id", id = true)
    @Result(property = "customerId", column = "customer_id")
    @Select("SELECT * FROM account WHERE account_id = #{accountId}")
    Account findById(@Param("accountId") Integer accountId);

}
