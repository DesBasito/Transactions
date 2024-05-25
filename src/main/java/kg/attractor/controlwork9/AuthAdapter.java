package kg.attractor.controlwork9;

import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.exceptions.UserNotFoundException;
import kg.attractor.controlwork9.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter {
    private final UserService service;
    public UserDto getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotFoundException("user not authorized");
        }
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new IllegalArgumentException("user not authorized");
        }
        String name = authentication.getName();
        return service.getUserByUniqueId(name);
    }

}
