package kg.attractor.controlwork9.repositories;

import kg.attractor.controlwork9.models.Account;
import kg.attractor.controlwork9.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByOwner(UserModel userModel);
}