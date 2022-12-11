package ua.kulichenko.instazoo3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kulichenko.instazoo3.payload.request.LoginRequest;
import ua.kulichenko.instazoo3.payload.response.JWTTokenSuccessResponse;
import ua.kulichenko.instazoo3.payload.response.MessageResponse;
import ua.kulichenko.instazoo3.payload.request.SignupRequest;
import ua.kulichenko.instazoo3.security.JWTTokenProvider;
import ua.kulichenko.instazoo3.security.SecurityConstants;
import ua.kulichenko.instazoo3.services.UserService;
import ua.kulichenko.instazoo3.validations.ResponsErrorValidation;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponsErrorValidation responsErrorValidation;
    @Autowired
    private UserService userServise;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responsErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return  errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    //api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity <Object> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responsErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return  errors;

        userServise.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}
