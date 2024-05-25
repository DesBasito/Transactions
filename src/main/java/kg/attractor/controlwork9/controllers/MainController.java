package kg.attractor.controlwork9.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.controlwork9.AuthAdapter;
import kg.attractor.controlwork9.dto.*;
import kg.attractor.controlwork9.models.Provider;
import kg.attractor.controlwork9.models.Transfers;
import kg.attractor.controlwork9.models.UserModel;
import kg.attractor.controlwork9.services.AccountService;
import kg.attractor.controlwork9.services.ProviderService;
import kg.attractor.controlwork9.services.TransferService;
import kg.attractor.controlwork9.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final UserService userService;
    private final AccountService accountService;
    private final TransferService transferService;
    private final ProviderService providerService;
    private final AuthAdapter adapter;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping()
    public String getMainPage(@RequestParam(name = "page", defaultValue = "0") int page,@RequestParam(name = "recipient", defaultValue = "") String recipientId, Model model) {
        if (!recipientId.isEmpty() && !recipientId.isBlank()) {
            UserDto user = userService.getUserByUniqueId(recipientId);
            model.addAttribute("recipient", user);
        }
        PaymentDto paymentDto = new PaymentDto();
        model.addAttribute("paymentDto", paymentDto);
        Page<ProviderDto> providers = providerService.getAllProviders(page);
        model.addAttribute("providers", providers);
        return "main/main";
    }

    @GetMapping("/profile")
    public String applicantInfo(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        UserDto user = adapter.getAuthUser();
        model.addAttribute("user", user);
        List<TransferDto> transfers = transferService.getAllTransfersForProfile(user.getEmail());
        AccountDto account = accountService.getAccount(userService.getUserModelByEmail(user.getEmail()));
        model.addAttribute("page", page);
        model.addAttribute("account", account);
        model.addAttribute("transfers", transfers);
        return "users/profile";
    }


    @GetMapping("/register")
    public String create(Model model) {
        UserCreationDto userCreationDto = new UserCreationDto();
        model.addAttribute("userCreationDto", userCreationDto);
        return "login/register";
    }


    @PostMapping("/register")
    public String create(@Valid UserCreationDto userCreationDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userCreationDto", userCreationDto);
            return "login/register";
        }
        UserModel user = userService.createUser(userCreationDto);

        try {
            request.login(user.getUniqueId(), user.getPassword());
        } catch (ServletException e) {
            log.error("Error while login ", e);
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @GetMapping("forgot_password")
    public String showForgetPasswordForm() {
        return "login/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException | UnsupportedEncodingException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException ex) {
            model.addAttribute("error", "Error while sending email");
        }
        return "login/forgot_password_form";
    }

    @GetMapping("reset_password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        try {
            PasswordDto passwordDto = PasswordDto.builder().token(token).build();
            model.addAttribute("passwordDto", passwordDto);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Invalid token");
        }
        return "login/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(@Valid PasswordDto passwordDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordDto", passwordDto);
            return "login/reset_password_form";
        }
        try {
            UserModel user = userService.getByResetPasswordToken(passwordDto.getToken());
            userService.updatePassword(user, passwordDto.getPassword());
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Invalid Token");
        }
        return "message";
    }
}
