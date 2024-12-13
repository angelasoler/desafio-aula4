package acc.br.student_registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "ZIP Code is required")
    private String cep;

    @NotBlank(message = "Course is required")
    private String course;
}
