package kg.attractor.controlwork9.controllers;

import jakarta.validation.Valid;
import kg.attractor.controlwork9.AuthAdapter;
import kg.attractor.controlwork9.dto.PaymentDto;
import kg.attractor.controlwork9.dto.ProviderDto;
import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.dto.UserProviderDto;
import kg.attractor.controlwork9.services.PaymentService;
import kg.attractor.controlwork9.services.ProviderService;
import kg.attractor.controlwork9.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    private final ProviderService providerService;
    private final AuthAdapter adapter;

    @PostMapping()
    public String transactionLogic(@Valid PaymentDto paymentDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            UserDto userDto = userService.getUserByEmail(paymentDto.getRecipient());
            model.addAttribute("recipient", userDto);
            model.addAttribute("paymentDto",paymentDto);
            return "main/main";
        }
        String sender = adapter.getAuthUser().getEmail();
        paymentService.paymentCheck(sender,paymentDto.getRecipient(),paymentDto.getBalance());
        return "redirect:/profile";
    }

    @PostMapping("/anon")
    public String fillWallet(@RequestParam(name = "page", defaultValue = "0") int page, @Valid PaymentDto paymentDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            UserDto userDto = userService.getUserByEmail(paymentDto.getRecipient());
            model.addAttribute("recipient", userDto);
            model.addAttribute("paymentDto",paymentDto);
            Page<ProviderDto> providers = providerService.getAllProviders(page);
            model.addAttribute("providers", providers);
            return "main/main";
        }
        paymentService.fillWallet(paymentDto.getRecipient(),paymentDto.getBalance());
        return "redirect:/";
    }

    @PostMapping("/provider/{id}")
    public String payProvider(@Valid PaymentDto paymentDto, BindingResult bindingResult, Model model, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            UserProviderDto user = providerService.getUserProviderDto(paymentDto.getRecipient());
            model.addAttribute("recipient", user);
            model.addAttribute("paymentDto",paymentDto);
            model.addAttribute("id",id);
            ProviderDto provider = providerService.getProviderDtoById(id);
            model.addAttribute("provider",provider);
            return "main/providersPay";
        }
        String sender = adapter.getAuthUser().getEmail();
        paymentService.payProvider(sender,paymentDto.getRecipient(),paymentDto.getBalance());
        return "redirect:/profile";
    }
}
