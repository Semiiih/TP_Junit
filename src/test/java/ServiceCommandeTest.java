import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceCommandeTest {

    private DepotStock stockDisponible = reference -> 100;
    private ServiceCommande service;
    private Panier panier;
    private Article articleTest;

    @BeforeEach
    void initialiser() {
        service = new ServiceCommande(stockDisponible);
        panier = new Panier();
        articleTest = new Article("REF-001", "Cahier", 3.50);
    }

    @Test
    void commandeValideDoitRetournerUneCommande() {
        panier.ajouterArticle(articleTest, 2);
        Commande commande = service.passerCommande(panier, "CLIENT-42");
        assertNotNull(commande);
        assertEquals(7.0, commande.total(), 0.001);
    }

    @Test
    void panierVideDoitLeverIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> service.passerCommande(panier, "CLIENT-42"));
    }

    @Test
    void identifiantClientNulDoitLeverException() {
        panier.ajouterArticle(articleTest, 1);
        assertThrows(IllegalArgumentException.class,
                () -> service.passerCommande(panier, null));
    }

    @Test
    void identifiantClientVideDoitLeverException() {
        panier.ajouterArticle(articleTest, 1);
        assertThrows(IllegalArgumentException.class,
                () -> service.passerCommande(panier, ""));
    }

    @Test
    void stockInsuffisantDoitLeverStockInsuffisantException() {
        DepotStock stockVide = reference -> 0;
        ServiceCommande serviceStockVide = new ServiceCommande(stockVide);
        panier.ajouterArticle(articleTest, 5);
        assertThrows(StockInsuffisantException.class,
                () -> serviceStockVide.passerCommande(panier, "CLIENT-42"));
    }

    @Test
    void totalCommandeDoitCorrespondreAuTotalDuPanier() {
        panier.ajouterArticle(articleTest, 3);
        Commande commande = service.passerCommande(panier, "CLIENT-42");
        assertEquals(panier.calculerTotal(), commande.total(), 0.001);
    }
}
