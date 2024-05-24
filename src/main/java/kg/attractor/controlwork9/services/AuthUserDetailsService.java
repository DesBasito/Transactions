package kg.attractor.controlwork9.services;

import kg.attractor.controlwork9.exceptions.UserNotFoundException;
import kg.attractor.controlwork9.models.UserModel;
import kg.attractor.controlwork9.repositories.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserModelRepository userModelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userModelRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        return new User(user.getEmail(),user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().getRole())));
    }
}
