import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PanierTest {

    @Test
    void ajouterArticleDeitAugmenterLeNombreDeArticles() {
        // Arranger
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Stylo bleu", 1.50);
        // Agir
        panier.ajouterArticle(article, 2);
        // Affirmer
        assertEquals(1, panier.nombreArticles());
    }

    @Test
    void calculerTotalDoitRetournerLaSommeDessousTotaux() {
        // Arranger
        Panier panier = new Panier();
        Article article1 = new Article("REF-001", "Stylo bleu", 1.50);
        Article article2 = new Article("REF-002", "Cahier", 1.50);
        // Agir
        panier.ajouterArticle(article1, 3);
        panier.ajouterArticle(article2, 3);
        // Affirmer — 2 articles à 1.50 € × 3 = 9.00 €
        assertEquals(9.00, panier.calculerTotal(), 0.001);
    }

    @Test
    void panierVideDoitRetournerEstVideEgalTrue() {
        // Arranger
        Panier panier = new Panier();
        // Affirmer — panier est vide
        assertTrue(panier.estVide());
    }

    @Test
    void panierAvecArticlesNeDoitPasEtreVide() {
        // Arranger
        Panier panier = new Panier();
        Article article1 = new Article("REF-001", "Stylo bleu", 1.50);
        // Agir
        panier.ajouterArticle(article1, 3);
        // Affirmer — panier est vide
        assertFalse(panier.estVide());
    }

    // Cas invalides
    @Test
    void articleNulDoitLeverException() {
        Panier panier = new Panier();
        assertThrows(IllegalArgumentException.class,
                () -> panier.ajouterArticle(null, 1));
    }

    @Test
    void quantiteNulleDoitLeverException() {
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Stylo", 1.50);
        assertThrows(IllegalArgumentException.class,
                () -> panier.ajouterArticle(article, 0));
    }

    @Test
    void quantiteNegativeDoitLeverException() {
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Stylo", 1.50);
        assertThrows(IllegalArgumentException.class,
                () -> panier.ajouterArticle(article, -3));
    }

    @Test
    void codeReductionVideDoitLeverException() {
        Panier panier = new Panier();
        assertThrows(IllegalArgumentException.class,
                () -> panier.appliquerCodeReduction(""));
    }

    @Test
    void codeReductionNulDoitLeverException() {
        Panier panier = new Panier();
        assertThrows(IllegalArgumentException.class,
                () -> panier.appliquerCodeReduction(null));
    }

    // Cas limites
    @Test
    void quantiteUneDoitEtreAcceptee() {
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Stylo", 9.99);
        panier.ajouterArticle(article, 1);
        assertEquals(9.99, panier.calculerTotal(), 0.001);
    }

    @Test
    void articleGratuitDoitEtreAccepte() {
        Panier panier = new Panier();
        Article articleGratuit = new Article("OFFERT-01", "Stylo offert", 0.0);
        panier.ajouterArticle(articleGratuit, 1);
        assertEquals(0.0, panier.calculerTotal(), 0.001);
    }

    @Test
    void prixEleveDoitFonctionner() {
        Panier panier = new Panier();
        Article article = new Article("REF-999", "Article premium", 999.99);
        panier.ajouterArticle(article, 1);
        assertEquals(999.99, panier.calculerTotal(), 0.001);
    }

    @Test
    void panierAvecUnSeulArticleDoitFonctionner() {
        Panier panier = new Panier();
        Article article = new Article("REF-001", "Stylo", 1.50);
        panier.ajouterArticle(article, 1);
        assertEquals(1, panier.nombreArticles());
    }

    @Test
    void plusieursArticlesDifferentsDansPanier() {
        Panier panier = new Panier();
        Article article1 = new Article("REF-001", "Stylo", 1.00);
        Article article2 = new Article("REF-002", "Cahier", 2.00);
        Article article3 = new Article("REF-003", "Règle", 3.00);
        panier.ajouterArticle(article1, 1);
        panier.ajouterArticle(article2, 1);
        panier.ajouterArticle(article3, 1);
        assertEquals(6.00, panier.calculerTotal(), 0.001);
    }
}
