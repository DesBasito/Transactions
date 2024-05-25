package kg.attractor.controlwork9.services;

import kg.attractor.controlwork9.dto.ProviderDto;
import kg.attractor.controlwork9.dto.UserDto;
import kg.attractor.controlwork9.dto.UserProviderDto;
import kg.attractor.controlwork9.models.Provider;
import kg.attractor.controlwork9.models.UserProvider;
import kg.attractor.controlwork9.repositories.ProviderRepository;
import kg.attractor.controlwork9.repositories.UserProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final UserProviderRepository userProviderRepository;

    public Page<ProviderDto> getAllProviders(int page){
        Pageable pageable = PageRequest.of(page,5);
        return providerRepository.findAll(pageable).map(this::getDto);
    }

    public UserProviderDto getUserProviderDto(String userProvider){
        UserProvider user = userProviderRepository.findByIdentifier(userProvider).orElse(null);
        return user == null ? null : getUserProviderDto(user);
    }
    private UserProviderDto getUserProviderDto(UserProvider userProvider){
        return  UserProviderDto.builder()
                .identifier(userProvider.getIdentifier())
                .provider(userProvider.getProvider().getName())
                .providerId(userProvider.getProvider().getId())
                .build();
    }

    private ProviderDto getDto(Provider provider){
        return ProviderDto.builder()
                .id(provider.getId())
                .name(provider.getName())
                .commission(100*provider.getCommission())
                .build();
    }

    public Provider getProviderById(Long providerId) {
        return providerRepository.findById(providerId).orElseThrow(NoSuchElementException::new);
    }

    public void save(Provider provider) {
        providerRepository.save(provider);
    }

    public ProviderDto getProviderDtoById(Long id) {
        return getDto(providerRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public UserProvider getUserProvider(String recipient) {
        return userProviderRepository.findByIdentifier(recipient).orElseThrow(NoSuchElementException::new);
    }
}
