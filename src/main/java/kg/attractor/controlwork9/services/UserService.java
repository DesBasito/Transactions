package kg.attractor.controlwork9.services;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.controlwork9.dto.UserCreationDto;
import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.exceptions.AlreadyExistsException;
import kg.attractor.controlwork9.exceptions.UserNotFoundException;
import kg.attractor.controlwork9.models.Authority;
import kg.attractor.controlwork9.models.UserModel;
import kg.attractor.controlwork9.repositories.AuthorityRepository;
import kg.attractor.controlwork9.repositories.UserModelRepository;
import kg.attractor.controlwork9.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserModelRepository userModelRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UserDto getUser(String email) {
        UserModel userModel = userModelRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String accType = getAccTypeByUserEmail(email);
        return getUserDto(userModel, accType);
    }

    private String getAccTypeByUserEmail(String email) {
        UserModel user = userModelRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getRole().getRole();
    }


    public UserDto getUserByEmail(String email) {
        String accType = getAccTypeByUserEmail(email);
        return getUserDto(userModelRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user with email: " + email + " does not exists")), accType);
    }

    public void createUser(UserCreationDto dto) {
        if (userModelRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException("User with email:" + dto.getEmail() + " already exists.");
        }
        Authority authority = authorityRepository.findAll().getFirst();
        UserModel userModel = UserModel.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .role(authority)
                .resetPasswordToken(null)
                .build();
        userModelRepository.save(userModel);
    }


    public UserModel getUserModelByEmail(String email) {
        return userModelRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User by email " + email + " not found"));
    }


    private UserDto getUserDto(UserModel userModel, String accType) {
        return UserDto.builder()
                .name(userModel.getName())
                .surname(userModel.getSurname())
                .role(accType)
                .email(userModel.getEmail())
                .build();
    }


    public void updateResetPasswordToken(String token, String email) {
        UserModel user = userModelRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find any user with the email " + email));
        user.setResetPasswordToken(token);
        userModelRepository.saveAndFlush(user);
    }

    public UserModel getByResetPasswordToken(String token) {
        return userModelRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updatePassword(UserModel user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userModelRepository.saveAndFlush(user);
    }

    public void makeResetPasswdLink(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);

        String resetPasswordLnk = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        emailService.sendEmail(email,resetPasswordLnk);
    }

}
