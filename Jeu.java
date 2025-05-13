import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Jeu extends Application {

    @Override
    public void start(Stage stage) {
        // Création d'une fenêtre JavaFX avec WebView
        StackPane root = new StackPane();
        WebView webView = new WebView();
        webView.getEngine().load("main.html"); // Charge le fichier HTML
        root.getChildren().add(webView);

        // Configuration de la scène
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Jeu à la Wargroove");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class Personnage {
    private String nom;
    private String race;
    private String classe;
    private int pointsDeVie;
    private int force;
    private int dexterite;
    private int vitesse;
    private int initiative;
    private ArrayList<String> inventaire;
    private String armeEquipes;
    private String armureEquipes;

    // Constructeur
    public Personnage(String nom, String race, String classe) {
        this.nom = nom;
        this.race = race;
        this.classe = classe;
        // Initialisation selon la classe choisie
        switch (classe) {
            case "Guerrier":
                this.pointsDeVie = 20;
                break;
            case "Clerc":
                this.pointsDeVie = 16;
                break;
            case "Magicien":
                this.pointsDeVie = 12;
                break;
            case "Roublard":
                this.pointsDeVie = 16;
                break;
            default:
                this.pointsDeVie = 10; // Valeur par défaut
        }
        // Calcul des autres caractéristiques
        this.force = rollDice(4, 3);
        this.dexterite = rollDice(4, 3);
        this.vitesse = rollDice(4, 3);
        this.initiative = rollDice(4, 3);
        this.inventaire = new ArrayList<>();
    }

    // Méthode pour lancer un jet de dés
    private int rollDice(int faces, int bonus) {
        return (int) (Math.random() * faces) + 1 + bonus;
    }

    // Getters et setters pour les propriétés
    public String getNom() {
        return nom;
    }

    public void setArmeEquipes(String arme) {
        this.armeEquipes = arme;
    }

    // Autres méthodes comme attaque, déplacement, etc.
}

class Monstre {
    private String espece;
    private int pointsDeVie;
    private int force;
    private int dexterite;
    private int vitesse;
    private int classeArmure;
    private int initiative;

    // Constructeur
    public Monstre(String espece, int pointsDeVie, int force, int dexterite, int vitesse, int classeArmure) {
        this.espece = espece;
        this.pointsDeVie = pointsDeVie;
        this.force = force;
        this.dexterite = dexterite;
        this.vitesse = vitesse;
        this.classeArmure = classeArmure;
        this.initiative = rollDice(4, 3);
    }

    // Méthode pour lancer un jet de dés
    private int rollDice(int faces, int bonus) {
        return (int) (Math.random() * faces) + 1 + bonus;
    }

    // Getters et setters pour les propriétés
    public String getEspece() {
        return espece;
    }

    // Autres méthodes comme attaque, déplacement, etc.
}
