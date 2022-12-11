package ua.kulichenko.instazoo3.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kulichenko.instazoo3.dto.UserinstaDTO;
import ua.kulichenko.instazoo3.entity.Userinsta;
import ua.kulichenko.instazoo3.entity.enums.ERole;
import ua.kulichenko.instazoo3.exeption.UserExistException;
import ua.kulichenko.instazoo3.payload.request.SignupRequest;
import ua.kulichenko.instazoo3.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Userinsta createUser (SignupRequest userIn) {
        Userinsta userinsta = new Userinsta();
        userinsta.setEmail(userIn.getEmail());
        userinsta.setName(userIn.getFirstname());
        userinsta.setLastname(userIn.getLastname());
        userinsta.setUsername(userIn.getUsername());
        userinsta.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        userinsta.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(userinsta);
        }catch (Exception e){
           LOG.error("Error during registration. {}", e.getMessage());
           throw new UserExistException("The user " + userinsta.getUsername() + " already exist. Please check credentials");
        }


    }
    public Userinsta updateUser(UserinstaDTO userinstaDTO, Principal principal){
        Userinsta userinsta = getUserinstaByPrincipal(principal);
        userinsta.setName(userinstaDTO.getFirstname());
        userinsta.setLastname(userinstaDTO.getLastname()); //!!! userinstaDTO -> userinsta
        userinsta.setBio(userinstaDTO.getBio());

        return userRepository.save(userinsta);
    }

    public Userinsta getCurrentUser(Principal principal) {
        return getUserinstaByPrincipal(principal);
    }

    private Userinsta getUserinstaByPrincipal (Principal principal) {
        String username = principal.getName();
        return userRepository.findUserinstaByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

    public Userinsta getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
