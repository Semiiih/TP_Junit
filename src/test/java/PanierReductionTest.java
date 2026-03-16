import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class PanierReductionTest {

    @ParameterizedTest
    @CsvSource({
            " , 100.0", // pas de code de réduction
            "REDUC10, 90.0", // -10%
            "REDUC20, 80.0" // -20%
    })
    void calculerTotalDoitAppliquerLaBonneReduction(String code, double totalAttendu) {
        // Arranger
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Classeur", 10.0);
        panier.ajouterArticle(article, 10); // sous-total = 100.0
        // Agir
        if (code != null && !code.isBlank()) {
            panier.appliquerCodeReduction(code.trim());
        }
        // Affirmer
        assertEquals(totalAttendu, panier.calculerTotal(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            " , 3, 5.0, 45.0", // pas de code, 3 articles × 3 qté × 5.0 = 45.0
            "REDUC10, 3, 5.0, 40.5", // -10% sur 45.0
            "REDUC20, 3, 5.0, 36.0" // -20% sur 45.0
    })
    void calculerTotalAvecPlusieursArticlesDoitAppliquerLaBonneReduction(
            String code, int quantite, double prix, double totalAttendu) {
        // Arranger
        Panier panier = new Panier();
        panier.ajouterArticle(new Article("REF-001", "Stylo", prix), quantite);
        panier.ajouterArticle(new Article("REF-002", "Cahier", prix), quantite);
        panier.ajouterArticle(new Article("REF-003", "Classeur", prix), quantite);
        // Agir
        if (code != null && !code.isBlank()) {
            panier.appliquerCodeReduction(code.trim());
        }
        // Affirmer
        assertEquals(totalAttendu, panier.calculerTotal(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            ", Stylo, 1, 5.0", // référence nulle → IllegalArgumentException
            "REF-001, Stylo, 0, 5.0", // quantite zéro → IllegalArgumentException
            "REF-001, Stylo, -3, 5.0", // quantite négative → IllegalArgumentException
    })
    void ajouterArticleInvalideDoitLeverIllegalArgumentException(
            String reference, String nom, int quantite, double prix) {
        Panier panier = new Panier();
        assertThrows(IllegalArgumentException.class, () -> {
            Article article = new Article(reference, nom, prix);
            panier.ajouterArticle(article, quantite);
        });
    }

}
