package dev.renvl.mapper;

import dev.renvl.model.AccountBalance;
import dev.renvl.model.Currency;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountBalanceMapper {

    @Insert("INSERT INTO account_balance(available_amount, currency, account_id) " +
            "VALUES (#{availableAmount}, #{currency}, #{accountId});")
    @Options(useGeneratedKeys = true, keyProperty = "accountBalanceId", keyColumn = "account_balance_id")
    void insert(AccountBalance accountBalance);

    @Update("UPDATE account_balance SET available_amount = #{availableAmount} WHERE account_balance_id = #{accountBalanceId}")
    void updateAvailableAmount(AccountBalance accountBalance);

    @Results({
            @Result(property = "accountBalanceId", column = "account_balance_id", id = true),
            @Result(property = "availableAmount", column = "available_amount"),
            @Result(property = "currency", column = "currency", javaType = Currency.class),
            @Result(property = "accountId", column = "account_id")
    })
    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId}")
    List<AccountBalance> findByAccountId(@Param("accountId") Integer accountId);

    @Results({
            @Result(property = "accountBalanceId", column = "account_balance_id", id = true),
            @Result(property = "availableAmount", column = "available_amount"),
            @Result(property = "currency", column = "currency", javaType = Currency.class),
            @Result(property = "accountId", column = "account_id")
    })
    @Select("SELECT * FROM account_balance WHERE account_id = #{accountId} AND currency = #{currency}")
    List<AccountBalance> findByAccountIdAndCurrency(@Param("accountId") Integer accountId, @Param("currency") String currency);
}
