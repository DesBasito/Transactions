package kg.attractor.controlwork9.repositories;

import kg.attractor.controlwork9.models.Transfers;
import kg.attractor.controlwork9.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransfersRepository extends JpaRepository<Transfers, Long> {
    List<Transfers> findTransfersBySender_EmailAndRecipient_Email(String sender_email, String recipient_email);

    List<Transfers> findTransfersBySender_Email(String senderEmail);
    List<Transfers> findTransfersByRecipient_Email(String recipientEmail);
}