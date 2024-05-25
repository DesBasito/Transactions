package kg.attractor.controlwork9.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Transfers")
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum_of_transfer")
    private Double sumOfTransfer;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserModel sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserModel recipient;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
}
