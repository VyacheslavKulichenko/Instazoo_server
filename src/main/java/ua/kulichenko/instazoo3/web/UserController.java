package ua.kulichenko.instazoo3.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kulichenko.instazoo3.dto.UserinstaDTO;
import ua.kulichenko.instazoo3.entity.Userinsta;
import ua.kulichenko.instazoo3.facade.UserFacade;
import ua.kulichenko.instazoo3.payload.response.MessageResponse;
import ua.kulichenko.instazoo3.services.UserService;
import ua.kulichenko.instazoo3.validations.ResponsErrorValidation;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponsErrorValidation responsErrorValidation;

    @GetMapping("/")
    public ResponseEntity<UserinstaDTO> getCurrentUser(Principal principal) {
        Userinsta userinsta = userService.getCurrentUser(principal);
        UserinstaDTO userinstaDTO = userFacade.userToUserDTO(userinsta);

        return new ResponseEntity<>(userinstaDTO, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserinstaDTO> getUserProfile(@PathVariable("userId") String userId) {
        Userinsta userinsta = userService.getUserById(Long.parseLong(userId));
        UserinstaDTO userinstaDTO = userFacade.userToUserDTO(userinsta);

        return new ResponseEntity<>(userinstaDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserinstaDTO userinstaDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responsErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return  errors;
        Userinsta userinsta = userService.updateUser(userinstaDTO, principal);
        UserinstaDTO userUpdated = userFacade.userToUserDTO(userinsta);
        return  new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
