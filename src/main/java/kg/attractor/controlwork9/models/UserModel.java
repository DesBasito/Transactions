package kg.attractor.controlwork9.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    private String email;

    @Lob
    private String name;

    @Lob
    private String surname;

    @Lob
    private String password;

    @Column
    private Boolean enabled;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Authority role;
}
