package kg.attractor.controlwork9.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double balance;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "provider")
    List<UserProvider> userProviders;
}
