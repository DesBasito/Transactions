package kg.attractor.controlwork9.dto;

import jakarta.persistence.*;
import kg.attractor.controlwork9.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private Long id;
    private Double sumOfTransfer;
    private String sender;
    private String recipient;
    private String transactionDate;
}
