package com.eagle.banking.model.mapper;

import com.eagle.banking.model.dto.BankAccountResponse;
import com.eagle.banking.model.dto.CreateBankAccountRequest;
import com.eagle.banking.model.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

  AccountEntity toAccountEntity(BankAccountResponse dto);

  AccountEntity toAccountEntity(CreateBankAccountRequest request);

  BankAccountResponse toBankAccountResponse(AccountEntity entity);

}
