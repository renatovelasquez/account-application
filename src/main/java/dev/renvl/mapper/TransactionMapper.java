package dev.renvl.mapper;

import dev.renvl.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transaction(amount, currency, direction, description, account_id) " +
            "VALUES (#{amount}, #{currency}, #{direction}, #{description}, #{account_id})")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId", keyColumn = "transaction_id")
    Transaction insert(Transaction transaction);

    @Result(property = "transactionId", column = "transaction_id", id = true)
    @Select("SELECT * FROM transaction WHERE account_id = #{accountId}")
    List<Transaction> findByAccountId(@Param("accountId") Integer accountId);

}
