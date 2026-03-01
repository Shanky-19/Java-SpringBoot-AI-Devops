package com.sanket.spring_mvc.web.dto;

import com.sanket.spring_mvc.web.annotations.EmployeeRoleValidationAnnotation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotNull(message = "Required field in Employee : name")
    @NotBlank(message = "Name of employee cannot be blank")
    @Size(min = 3, max = 10, message = "Number of characters should be in range : [3,10]")
    private String name;

    @Email
    private String email;

    @Max(value = 150, message = "Age cannot be greater than 150")
    @Min(value = 18, message = "Age cannot be lesser than 18")
    private Integer age;

    @NotNull(message = "Role of employee cannot be null")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of employee can be ADMIN or USER")
    @EmployeeRoleValidationAnnotation
    private String role; // ADMIN, USER

    @Positive
    @Digits(integer = 8, fraction = 2, message = "The salary should be in the form XXXXXXXX.YY")
    @DecimalMax(value = "99999999.99")
    @DecimalMin(value = "1000.00")
    private Double salary;

    @PastOrPresent(message = "Date of Joining cannot be in future for existing employee")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;

}
