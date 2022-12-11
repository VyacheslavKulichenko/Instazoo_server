package ua.kulichenko.instazoo3.facade;

import org.springframework.stereotype.Component;
import ua.kulichenko.instazoo3.dto.UserinstaDTO;
import ua.kulichenko.instazoo3.entity.Userinsta;

@Component
public class UserFacade {
    public UserinstaDTO userToUserDTO(Userinsta userinsta) {
        UserinstaDTO userinstaDTO = new UserinstaDTO();
        userinstaDTO.setId(userinsta.getId());
        userinstaDTO.setFirstname(userinsta.getName());
        userinstaDTO.setLastname(userinsta.getLastname());
        userinstaDTO.setUsername(userinsta.getUsername());
        userinstaDTO.setBio(userinsta.getBio());
        return userinstaDTO;
    }

}
