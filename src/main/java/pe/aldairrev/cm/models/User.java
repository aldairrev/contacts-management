package pe.aldairrev.cm.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
}
