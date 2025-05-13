import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Personnage {
    String nom;
    String race;
    String classe;
    int pointsDeVie;
    int force;
    int dexterite;
    int vitesse;
    int initiative;
    String arme;
    String armure;

    public Personnage(String nom, String race, String classe) {
        this.nom = nom;
        this.race = race;
        this.classe = classe;
        this.initiative = lancerDes("1d20");

        switch (classe) {
            case "Clerc":
                this.pointsDeVie = 16;
                this.arme = "Masse d'armes";
                this.armure = "Armure d'écailles";
                break;
            case "Guerrier":
                this.pointsDeVie = 20;
                this.arme = "Épée longue";
                this.armure = "Cotte de mailles";
                break;
            case "Magicien":
                this.pointsDeVie = 12;
                this.arme = "Bâton";
                this.armure = "Aucune";
                break;
            case "Roublard":
                this.pointsDeVie = 16;
                this.arme = "Rapière";
                this.armure = "Aucune";
                break;
        }

        this.force = 3 + lancerDes("4d4");
        this.dexterite = 3 + lancerDes("4d4");
        this.vitesse = 3 + lancerDes("4d4");

        if (race.equals("Elfe")) {
            this.dexterite += 6;
        } else if (race.equals("Halfelin")) {
            this.dexterite += 4;
            this.vitesse += 2;
        } else if (race.equals("Humain")) {
            this.force += 2;
            this.dexterite += 2;
            this.vitesse += 2;
        } else if (race.equals("Nain")) {
            this.force += 6;
        }
    }

    public int lancerDes(String des) {
        String[] parts = des.split("d");
        int nombreDeDes = Integer.parseInt(parts[0]);
        int nombreDeFaces = Integer.parseInt(parts[1]);
        Random rand = new Random();
        int total = 0;
        for (int i = 0; i < nombreDeDes; i++) {
            total += rand.nextInt(nombreDeFaces) + 1;
        }
        return total;
    }
}

class Monstre {
    String espece;
    int numero;
    int pointsDeVie;
    int force;
    int dexterite;
    int classeArmure;
    int initiative;

    public Monstre(String espece, int numero) {
        this.espece = espece;
        this.numero = numero;
        this.pointsDeVie = 10 + numero * 2; // Exemple de points de vie
        this.force = 0; // Corps à corps
        this.dexterite = 0; // Corps à corps
        this.classeArmure = 10; // Exemple
        this.initiative = lancerDes("1d20");
    }

    public int lancerDes(String des) {
        String[] parts = des.split("d");
        int nombreDeDes = Integer.parseInt(parts[0]);
        int nombreDeFaces = Integer.parseInt(parts[1]);
        Random rand = new Random();
        int total = 0;
        for (int i = 0; i < nombreDeDes; i++) {
            total += rand.nextInt(nombreDeFaces) + 1;
        }
        return total;
    }
}

class Donjon {
    List<Personnage> personnages;
    List<Monstre> monstres;

    public Donjon() {
        personnages = new ArrayList<>();
        monstres = new ArrayList<>();
    }

    public void ajouterPersonnage(Personnage p) {
        personnages.add(p);
    }

    public void ajouterMonstre(Monstre m) {
        monstres.add(m);
    }

    public void demarrer() {
        // Logique de démarrage du donjon
        System.out.println("Le donjon commence !");
        // Boucle de jeu ici
    }
}

public class Jeu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Donjon