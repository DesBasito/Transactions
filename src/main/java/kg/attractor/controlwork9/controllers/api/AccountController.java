package kg.attractor.controlwork9.controllers.api;

import kg.attractor.controlwork9.dto.AccountDto;
import kg.attractor.controlwork9.models.UserModel;
import kg.attractor.controlwork9.services.AccountService;
import kg.attractor.controlwork9.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("restUser")
@RequestMapping("api/balance")
@RequiredArgsConstructor
public class AccountController {
    private  final AccountService accountService;
    private final UserService userService;

    @GetMapping("/{uniqueId}")
    public ResponseEntity<Double> getBalance( @PathVariable String uniqueId){
        UserModel userModel = userService.getUserModelByUniqueId(uniqueId);
        AccountDto accountDto = accountService.getAccount(userModel);
        return ResponseEntity.ok(accountDto.getBalance());
    }
}
