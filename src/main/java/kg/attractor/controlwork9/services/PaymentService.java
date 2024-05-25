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

    public void paymentCheck(String sender, String recipient, Double balance){
        UserModel senderModel = userService.getUserModelByEmail(sender);
        AccountDto senderAccount = accountService.getAccount(senderModel);
        if (senderAccount.getBalance()<balance){
            throw new IllegalArgumentException("transaction not available");
        }
        Account account1 = new Account();
        account1.setId(senderAccount.getId());
        account1.setBalance(senderAccount.getBalance()-balance);
        account1.setOwner(senderModel);

        UserModel recipientModel = userService.getUserModelByEmail(recipient);
        AccountDto recipientAccount = accountService.getAccount(recipientModel);
        Account account = new Account();
        account.setId(recipientAccount.getId());
        account.setBalance(recipientAccount.getBalance()+balance);
        account.setOwner(recipientModel);

        accountService.saveAccount(account1);
        accountService.saveAccount(account);
        saveTransfer(senderModel,recipientModel,balance);
    }

    public void fillWallet(String recipient, Double balance) {
        if (balance<0){
            throw new IllegalArgumentException("transaction not available");
        }
        UserModel recipientModel = userService.getUserModelByEmail(recipient);
        AccountDto recipientAccount = accountService.getAccount(recipientModel);
        Account account = new Account();
        account.setId(recipientAccount.getId());
        account.setBalance(recipientAccount.getBalance()+balance);
        account.setOwner(recipientModel);

        accountService.saveAccount(account);
        saveTransfer(null,recipientModel,balance);
    }

    private void saveTransfer(UserModel sender, UserModel recipient, Double balance){
        Transfers transfers = new Transfers();
        transfers.setSumOfTransfer(balance);
        transfers.setSender(sender);
        transfers.setRecipient(recipient);
        transfers.setTransactionDate(LocalDateTime.now());
        transferService.saveTransfer(transfers);
    }


    public void payProvider(String sender,String recipient, Double balance) {
        UserModel senderModel = userService.getUserModelByEmail(sender);
        AccountDto senderAccount = accountService.getAccount(senderModel);
        if (senderAccount.getBalance()<6 || balance>senderAccount.getBalance()){
            throw new IllegalArgumentException("you have only "+senderAccount.getBalance()+"$ and услуга 5$");
        }
        Account account1 = new Account();
        account1.setId(senderAccount.getId());
        account1.setBalance(senderAccount.getBalance()-(balance+5D));
        account1.setOwner(senderModel);


        Provider provider = new Provider();
        UserProviderDto userProviderDto = providerService.getUserProviderDto(recipient);
        UserProvider userProvider = providerService.getUserProvider(recipient);
        Provider provider1 = providerService.getProviderById(userProviderDto.getProviderId());
        provider.setId(provider1.getId());
        provider.setName(provider1.getName());
        provider.setBalance(provider1.getBalance()+5D);
        providerService.save(provider);

        accountService.saveAccount(account1);
        saveTransferProvider(senderModel,userProvider,balance+5D);
    }

    private void saveTransferProvider(UserModel sender, UserProvider recipient, Double balance){
        Transfers transfers = new Transfers();
        transfers.setSumOfTransfer(balance);
        transfers.setSender(sender);
        transfers.setRecipientProvider(recipient);
        transfers.setTransactionDate(LocalDateTime.now());
        transferService.saveTransfer(transfers);
    }

}
