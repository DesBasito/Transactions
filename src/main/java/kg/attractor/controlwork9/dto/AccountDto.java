package kg.attractor.controlwork9.dto;

import jakarta.persistence.*;
import kg.attractor.controlwork9.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Integer id;
    private Double balance;
    private String owner;
}
