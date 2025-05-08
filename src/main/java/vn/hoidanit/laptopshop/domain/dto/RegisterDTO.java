package vn.hoidanit.laptopshop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.hoidanit.laptopshop.service.validator.RegisterChecked;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RegisterChecked
public class RegisterDTO {
    
    @NotNull
    @NotEmpty(message = "First name không được để trống")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Last name không được để trống")
    private String lastName;

    private String email;
    private String password;
    private String confirmPassword;
}
