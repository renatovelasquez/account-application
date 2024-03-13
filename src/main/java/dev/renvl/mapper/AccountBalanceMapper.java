package dev.renvl.mapper;

import dev.renvl.model.AccountBalance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance(available_amount, currency, account_id) " +
            "VALUES (#{availableAmount}, #{currency}, #{accountId});")
    @Options(useGeneratedKeys = true, keyProperty = "accountBalanceId", keyColumn = "account_balance_id")
    void insert(AccountBalance accountBalance);

    @Result(property = "accountBalanceId", column = "account_balance_id", id = true)
    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId}")
    List<AccountBalance> findByAccountId(@Param("accountId") Integer accountId);

}
