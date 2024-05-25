package kg.attractor.controlwork9.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "email")
    private UserModel owner;
}
