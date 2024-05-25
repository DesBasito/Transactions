package kg.attractor.controlwork9.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProviderDto {
    private String identifier;
    private String provider;
    private Long providerId;
}
