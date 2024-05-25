package kg.attractor.controlwork9.services;

import kg.attractor.controlwork9.dto.AccountDto;
import kg.attractor.controlwork9.models.Account;
import kg.attractor.controlwork9.models.UserModel;
import kg.attractor.controlwork9.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDto getAccount(UserModel owner){
        return getDto(accountRepository.findAccountByOwner(owner).orElseThrow(()->new NoSuchElementException("Account by owner "+ owner.getEmail() +" not found")));
    }

    private AccountDto getDto(Account account){
        return  AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .owner(account.getOwner().getName()+" "+account.getOwner().getSurname())
                .build();
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
