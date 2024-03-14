package dev.renvl.mapper;

import dev.renvl.model.Currency;
import dev.renvl.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transaction(amount, currency, direction, description, account_id) " +
            "VALUES (#{amount}, #{currency}, #{direction}, #{description}, #{accountId})")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId", keyColumn = "transaction_id")
    void insert(Transaction transaction);

    @Results({
            @Result(property = "transactionId", column = "transaction_id", id = true),
            @Result(property = "amount", column = "amount"),
            @Result(property = "currency", column = "currency", javaType = Currency.class),
            @Result(property = "direction", column = "direction"),
            @Result(property = "description", column = "description"),
            @Result(property = "accountId", column = "account_id")
    })
    @Select("SELECT * FROM transaction WHERE account_id = #{accountId}")
    List<Transaction> findByAccountId(@Param("accountId") Integer accountId);

}
