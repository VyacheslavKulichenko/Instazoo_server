package ua.kulichenko.instazoo3.services;


import org.springframework.security.core.GrantedAuthority;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kulichenko.instazoo3.entity.Userinsta;
import ua.kulichenko.instazoo3.repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserinstaDetailsServise implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserinstaDetailsServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Userinsta userinsta = userRepository.findUserinstaByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return build(userinsta);
    }
    public Userinsta loadUserById(Long id){
        return userRepository.findUserinstaById(id).orElse(null);
    }

    public static Userinsta build(Userinsta user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new Userinsta(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}
