package kg.attractor.controlwork9.services;

import kg.attractor.controlwork9.dto.AccountDto;
import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.dto.UserProviderDto;
import kg.attractor.controlwork9.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TransferService transferService;
    private final AccountService accountService;
    private final ProviderService providerService;
    private final UserService userService;

    public void paymentCheck(String sender, String recipient, Double balance) {
        UserModel senderModel = userService.getUserModelByEmail(sender);
        AccountDto senderAccount = accountService.getAccount(senderModel);
        if (senderAccount.getBalance() < balance) {
            throw new IllegalArgumentException("transaction not available");
        }
        Account account1 = new Account();
        account1.setId(senderAccount.getId());
        account1.setBalance(senderAccount.getBalance() - balance);
        account1.setOwner(senderModel);

        UserModel recipientModel = userService.getUserModelByEmail(recipient);
        AccountDto recipientAccount = accountService.getAccount(recipientModel);
        Account account = new Account();
        account.setId(recipientAccount.getId());
        account.setBalance(recipientAccount.getBalance() + balance);
        account.setOwner(recipientModel);

        accountService.saveAccount(account1);
        accountService.saveAccount(account);
        saveTransfer(senderModel, recipientModel, balance);
    }

    public void fillWallet(String recipient, Double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("transaction not available");
        }
        UserModel recipientModel = userService.getUserModelByEmail(recipient);
        AccountDto recipientAccount = accountService.getAccount(recipientModel);
        Account account = new Account();
        account.setId(recipientAccount.getId());
        account.setBalance(recipientAccount.getBalance() + balance);
        account.setOwner(recipientModel);

        accountService.saveAccount(account);
        saveTransfer(null, recipientModel, balance);
        log.info("filled wallet by id: " + recipientAccount.getId() + " by terminal");
    }

    private void saveTransfer(UserModel sender, UserModel recipient, Double balance) {
        Transfers transfers = new Transfers();
        transfers.setSumOfTransfer(balance);
        transfers.setSender(sender);
        transfers.setRecipient(recipient);
        transfers.setTransactionDate(LocalDateTime.now());
        transferService.saveTransfer(transfers);
    }


    public void payProvider(String sender, String recipient, Double balance) {
        UserModel senderModel = userService.getUserModelByEmail(sender);
        Provider provider = new Provider();
        UserProvider userProvider1 = new UserProvider();
        AccountDto senderAccount = accountService.getAccount(senderModel);
        if (senderAccount.getBalance() < balance + senderAccount.getBalance() || balance > senderAccount.getBalance()) {
            throw new IllegalArgumentException("you have only " + senderAccount.getBalance() + "$ and услуга ");
        }
        UserProviderDto userProviderDto = providerService.getUserProviderDto(recipient);
        UserProvider userProvider = providerService.getUserProvider(recipient);
        Provider provider1 = providerService.getProviderById(userProviderDto.getProviderId());

        userProvider1.setIdentifier(userProvider.getIdentifier());
        userProvider1.setProvider(userProvider.getProvider());
        userProvider1.setBalance(userProvider.getBalance() + balance);

        Account account1 = new Account();
        account1.setId(senderAccount.getId());
        account1.setBalance(senderAccount.getBalance() - (balance + (balance * provider1.getCommission())));
        account1.setOwner(senderModel);


        provider.setId(provider1.getId());
        provider.setName(provider1.getName());
        provider.setBalance(provider1.getBalance() + (balance * provider1.getCommission()));
        provider.setCommission(provider1.getCommission());
        providerService.save(provider);
        providerService.saveUserProvider(userProvider1);
        accountService.saveAccount(account1);
        saveTransferProvider(senderModel, userProvider, balance + (balance * provider1.getCommission()));
    }

    private void saveTransferProvider(UserModel sender, UserProvider recipient, Double balance) {
        Transfers transfers = new Transfers();
        transfers.setSumOfTransfer(balance);
        transfers.setSender(sender);
        transfers.setRecipientProvider(recipient);
        transfers.setTransactionDate(LocalDateTime.now());
        transferService.saveTransfer(transfers);
    }

}
