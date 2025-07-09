package com.eagle.banking.service;

import com.eagle.banking.advice.ServiceException;
import com.eagle.banking.model.dto.BankAccountResponse;
import com.eagle.banking.model.dto.CreateBankAccountRequest;
import com.eagle.banking.model.dto.UpdateBankAccountRequest;
import com.eagle.banking.model.entity.AccountEntity;
import com.eagle.banking.model.mapper.AccountsMapper;
import com.eagle.banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class AccountsService {

  private final AccountRepository accountRepository;

  private final AccountsMapper accountMapper = Mappers.getMapper(AccountsMapper.class);

  @Transactional
  public BankAccountResponse createAccount(CreateBankAccountRequest request) throws ServiceException {

    BankAccountResponse response = null;

    try {
      AccountEntity entity = accountRepository.save(accountMapper.toAccountEntity(request));
      response = accountMapper.toBankAccountResponse(entity);

    } catch (DataAccessException e) {
      throw new ServiceException("Error creating account: " + e.getMessage(), e);
    }

    return response;

  }

  public BankAccountResponse getAccountById(String accountId) throws ServiceException {
    return accountRepository.findById(accountId)
        .map(accountMapper::toBankAccountResponse)
        .orElse(null);
  }

  @Transactional
  public BankAccountResponse updateAccount(String accountId, UpdateBankAccountRequest account) throws ServiceException {
    BankAccountResponse response = null;

    try {
      AccountEntity entity = accountRepository.findById(accountId)
          .orElseThrow(() -> new ServiceException("Account not found with ID:%s".formatted(accountId)));
      entity.setName(account.getName());
      entity.setAccountType(account.getAccountType());
      entity = accountRepository.save(entity);
      response = accountMapper.toBankAccountResponse(entity);

    } catch (DataAccessException e) {
      throw new ServiceException("Error updating account: " + e.getMessage(), e);
    }
    return response;
  }

}
