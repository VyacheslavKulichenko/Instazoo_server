package ua.kulichenko.instazoo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import ua.kulichenko.instazoo3.entity.Userinsta;

import java.util.Optional;

@Repository
public  interface UserRepository extends JpaRepository<Userinsta, Long> {

    Optional<Userinsta> findUserinstaByUsername(String username);

    Optional<Userinsta> findUserinstaByEmail(String email);

    Optional<Userinsta> findUserinstaById (Long id);
}
