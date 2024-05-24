package kg.attractor.controlwork9.repositories;

import kg.attractor.controlwork9.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(String role);
}