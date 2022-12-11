package ua.kulichenko.instazoo3.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserinstaDTO {
private Long id;
@NotEmpty
private String firstname;
@NotEmpty
private String lastname;

@NotEmpty
private String username;

private String bio;

}
