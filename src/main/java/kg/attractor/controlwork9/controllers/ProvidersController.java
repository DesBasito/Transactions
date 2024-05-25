package kg.attractor.controlwork9.controllers;

import kg.attractor.controlwork9.dto.PaymentDto;
import kg.attractor.controlwork9.dto.ProviderDto;
import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.dto.UserProviderDto;
import kg.attractor.controlwork9.services.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/provider")
public class ProvidersController {
    private final ProviderService providerService;

    @GetMapping("/{id}")
    public String getMainPage(@RequestParam(name = "recipient", defaultValue = "") String recipientId, Model model, @PathVariable Long id) {
        if (!recipientId.isEmpty() && !recipientId.isBlank()) {
            UserProviderDto user = providerService.getUserProviderDto(recipientId);
            if (user!=null){
                if (Objects.equals(id, user.getProviderId())) {
                    model.addAttribute("recipient", user);
                }
            }
        }
        PaymentDto paymentDto = new PaymentDto();
        model.addAttribute("paymentDto", paymentDto);
        ProviderDto provider = providerService.getProviderDtoById(id);
        model.addAttribute("provider", provider);
        return "main/providersPay";
    }
}
