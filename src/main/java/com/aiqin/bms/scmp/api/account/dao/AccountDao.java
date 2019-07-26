package com.aiqin.bms.scmp.api.account.dao;

import com.aiqin.bms.scmp.api.account.domain.Account;
import com.aiqin.bms.scmp.api.account.domain.request.AccountRequest;

import java.util.List;

public interface AccountDao {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Integer listCount(AccountRequest request);

    List<Account> list(AccountRequest request);

    Account selectOne(Account request);

    Account selectLastOne();
}