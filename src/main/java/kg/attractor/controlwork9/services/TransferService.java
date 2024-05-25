package kg.attractor.controlwork9.services;

import kg.attractor.controlwork9.dto.TransferDto;
import kg.attractor.controlwork9.models.Transfers;
import kg.attractor.controlwork9.repositories.TransfersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransfersRepository transfersRepository;

    public List<TransferDto> getAllTransfersForProfile(String email) {
        List<Transfers> transfers = new ArrayList<>();
        List<Transfers> transfersSender = transfersRepository.findTransfersBySender_Email(email);
        List<Transfers> transfersRecipients = transfersRepository.findTransfersByRecipient_Email(email);
        transfers.addAll(transfersSender);
        transfers.addAll(transfersRecipients);
        return transfers.stream().map(this::getDto).collect(Collectors.toList());
    }

    private TransferDto getDto(Transfers transfers) {
        return TransferDto.builder()
                .id(transfers.getId())
                .sumOfTransfer(transfers.getSumOfTransfer())
                .sender(transfers.getSender() == null ? null : transfers.getSender().getUniqueId())
                .recipient(transfers.getRecipient() == null ? null : transfers.getRecipient().getUniqueId())
                .provider(transfers.getRecipientProvider() == null ? null : transfers.getRecipientProvider().getIdentifier()+"("+transfers.getRecipientProvider().getProvider().getName()+")")
                .transactionDate(transfers.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    public void saveTransfer(Transfers transfers) {
        transfersRepository.save(transfers);
    }
}
