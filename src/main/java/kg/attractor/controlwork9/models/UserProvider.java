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
@Table(name = "Users_provider")
public class UserProvider {
    @Id
    private String identifier;

    @Column
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "recipientProvider")
    List<Transfers> providerRecipient;

}
