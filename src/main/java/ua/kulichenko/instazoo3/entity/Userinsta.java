package ua.kulichenko.instazoo3.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.kulichenko.instazoo3.entity.enums.ERole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Entity
public class Userinsta implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Column(unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "text")
    private  String bio;

    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id")) //name??????
    private Set<ERole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userinsta", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();



    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public Userinsta(){

    }

    public Userinsta(Long id,
                     String username,
                     String email,
                     String password,
                     Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @PrePersist
    protected void onCreateDate() {
        this.createdDate = LocalDateTime.now();
    }



    /**
     * Security
     */
    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
