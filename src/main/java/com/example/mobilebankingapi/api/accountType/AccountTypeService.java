package com.example.mobilebankingapi.api.accountType;

import java.util.List;

public interface AccountTypeService {
    //we have to follw dao and dto pattern
    List<AccountTypeDto> findAll();

}
