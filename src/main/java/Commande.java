// Classe de commande créée par le service

import java.time.LocalDateTime;

public record Commande(String identifiantClient, double total, LocalDateTime dateCreation) {
}