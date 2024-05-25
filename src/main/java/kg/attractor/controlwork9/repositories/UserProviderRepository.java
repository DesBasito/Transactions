package kg.attractor.controlwork9.repositories;

import kg.attractor.controlwork9.models.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProviderRepository extends JpaRepository<UserProvider, String> {
    Optional<UserProvider> findByIdentifier(String identifier);
}