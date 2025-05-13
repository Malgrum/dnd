import java.util.*;

class De {
    private Random rand = new Random();

    public int lancer(String des) {
        String[] parts = des.toLowerCase().split("d");
        int nombre = Integer.parseInt(parts[0]);
        int faces = Integer.parseInt(parts[1]);
        int total = 0;
        for (int i = 0; i < nombre; i++) {
            total += rand.nextInt(faces) + 1;
        }
        return total;
    }
}

enum Race {
    Humain, Nain, Elfe, Halfelin
}

enum Classe {
    Guerrier, Clerc, Magicien, Roublard
}

class Equipement {
    String nom;
    String type; // "arme" ou "armure"
    String degats; // ex: "1d8" pour arme
    int portee; // en cases pour arme
    int classeArmure; // pour armure

    public Equipement(String nom, String type, String degats, int portee, int classeArmure) {
        this.nom = nom;
        this.type = type;
        this.degats = degats;
        this.portee = portee;
        this.classeArmure = classeArmure;
    }
}

class Personnage {
    String nom;
    Race race;
    Classe classe;
    int pointsDeVie;
    int pointsDeVieMax;
    int force, dexterite, vitesse, initiative;
    List<Equipement> inventaire = new ArrayList<>();
    Equipement armeEquipee = null;
    Equipement armureEquipee = null;
    int posX, posY;
    int actionsRestantes = 3;

    De de = new De();

    public Personnage(String nom, Race race, Classe classe) {
        this.nom = nom; this.race = race; this.classe = classe;
        switch(classe) {
            case Clerc: pointsDeVieMax=16; break;
            case Guerrier: pointsDeVieMax=20; break;
            case Magicien: pointsDeVieMax=12; break;
            case Roublard: pointsDeVieMax=16; break;
        }
        pointsDeVie = pointsDeVieMax;
        force = 3 + de.lancer("4d4");
        dexterite = 3 + de.lancer("4d4");
        vitesse = 3 + de.lancer("4d4");
        // ajustements races
        switch(race) {
            case Elfe: dexterite +=6; break;
            case Halfelin: dexterite +=4; vitesse +=2; break;
            case Humain: force +=2; dexterite +=2; vitesse +=2; break;
            case Nain: force +=6; break;
        }
        initiative = de.lancer("1d20") + vitesse;

        // équipements par classe
        if (classe == Classe.Clerc) {
            ajouterEquipement(new Equipement("Masse d'armes","arme","1d6",1,0));
            ajouterEquipement(new Equipement("Armure d'écailles","armure",null,0,9));
            ajouterEquipement(new Equipement("Arbalète légère","arme","1d8",16,0));
        } else if (classe == Classe.Guerrier) {
            ajouterEquipement(new Equipement("Épée longue","arme","1d8",1,0));
            ajouterEquipement(new Equipement("Cotte de mailles","armure",null,0,11));
            ajouterEquipement(new Equipement("Arbalète légère","arme","1d8",16,0));
        } else if (classe == Classe.Magicien) {
            ajouterEquipement(new Equipement("Bâton","arme","1d6",1,0));
            ajouterEquipement(new Equipement("Fronde","arme","1d4",6,0));
        } else if (classe == Classe.Roublard) {
            ajouterEquipement(new Equipement("Rapière","arme","1d8",1,0));
            ajouterEquipement(new Equipement("Arc court","arme","1d6",16,0));
        }
        equiperArme();
        equiperArmure();
    }

    void ajouterEquipement(Equipement e) {
        inventaire.add(e);
    }

    void equiperArme() {
        for(Equipement e : inventaire) if(e.type.equals("arme")) {
            armeEquipee = e; break;
        }
    }

    void equiperArmure() {
        for(Equipement e : inventaire) if(e.type.equals("armure")) {
            armureEquipee = e; break;
        }
        if(armureEquipee == null) armureEquipee = new Equipement("aucune","armure",null,0,0);
    }

    String getAbbreviation() {
        return nom.length()>=3 ? nom.substring(0,3) : String.format("%-3s",nom);
    }

    @Override
    public String toString() {
        return String.format("%-3s %-10s (%s %s, %d/%d)", getAbbreviation(), nom, race, classe, pointsDeVie, pointsDeVieMax);
    }

    void afficherDetails() {
        System.out.println(nom);
        System.out.printf("  Vie : %d/%d\n", pointsDeVie, pointsDeVieMax);
        System.out.printf("  Armure: %s\n", armureEquipee.nom);
        System.out.printf("  Arme: %s (degat: %s, portee: %d)\n", armeEquipee.nom, armeEquipee.degats, armeEquipee.portee);
        System.out.print("  Inventaire: ");
        for(int i=0; i<inventaire.size(); i++) {
            Equipement e = inventaire.get(i);
            System.out.print("[" + (i+1) + "] " + e.nom + (i < inventaire.size()-1 ? ", " : "\n"));
        }
        System.out.printf("  Force: %d\n  Dextérité: %d\n  Vitesse: %d\n", force, dexterite, vitesse);
    }
}

class Monstre {
    String espece;
    int numero;
    int pointsDeVie;
    int pointsDeVieMax;
    int force;
    int dexterite;
    int classeArmure;
    int initiative;
    int posX, posY;

    De de = new De();

    public Monstre(String espece, int numero) {
        this.espece = espece;
        this.numero = numero;
        this.pointsDeVieMax = 10 + numero * 5;
        this.pointsDeVie = pointsDeVieMax;
        this.force = 5;  // Ex simplifié
        this.dexterite = 5;
        this.classeArmure = 10;
        this.initiative = de.lancer("1d20") + dexterite;
    }

    String getAbbreviation() {
        String abbr = espece.length()>=3 ? espece.substring(0,3) : espece;
        if(numero > 1) abbr += numero;
        return String.format("%-3s", abbr);
    }

    @Override
    public String toString() {
        return String.format("%-3s %-10s (%d/%d) ", getAbbreviation(), espece, pointsDeVie, pointsDeVieMax);
    }
}

class Donjon {
    static final int LARGEUR = 25;
    static final int HAUTEUR = 18;
    char[][] carte = new char[HAUTEUR][LARGEUR];
    Personnage[][] posPersonnages = new Personnage[HAUTEUR][LARGEUR];
    Monstre[][] posMonstres = new Monstre[HAUTEUR][LARGEUR];

    List<Personnage> personnages;
    List<Monstre> monstres;

    int tourCourant =1;

    public Donjon(List<Personnage> joueurs, List<Monstre> monstres) {
        this.personnages = joueurs;
        this.monstres = monstres;
        initCarte();
        placerEntites();
    }

    void initCarte() {
        for(int i=0; i<HAUTEUR; i++)
            for(int j=0; j<LARGEUR; j++)
                carte[i][j] = '.';

        // quelques obstacles fixe par défaut (exemples)
        carte[3][4] = 'X';
        carte[3][5] = '^';
        carte[6][18] = '*';
        carte[13][11] = '*';
        // ajoute autres obstacles selon besoin
    }

    void placerEntites() {
        // placer personnages sur la carte (simple, quelques positions fixes)
        if(personnages.size() > 0){ personnages.get(0).posX=13; personnages.get(0).posY=11; posPersonnages[11][13] = personnages.get(0);}
        if(personnages.size() > 1){ personnages.get(1).posX=13; personnages.get(1).posY=14; posPersonnages[14][13] = personnages.get(1);}
        if(personnages.size() > 2){ personnages.get(2).posX=15; personnages.get(2).posY=16; posPersonnages[16][15] = personnages.get(2);}
        // ... adapter selon nbr joueurs

        // placer monstres sur la carte, positions fixes exemple
        if(monstres.size()>0) {monstres.get(0).posX= 4; monstres.get(0).posY=3; posMonstres[3][4]=monstres.get(0);}
        if(monstres.size()>1) {monstres.get(1).posX= 8; monstres.get(1).posY=3; posMonstres[3][8]=monstres.get(1);}
        // ... adapter selon nbr monstres
    }

    void afficherDonjon(Personnage joueurActif) {
        System.out.println("********************************************************************************");
        System.out.println("Donjon 2:");
        System.out.printf("                            %s (%s %s)%n", joueurActif.nom, joueurActif.race, joueurActif.classe);
        System.out.println("********************************************************************************");
        System.out.println("Tour " + tourCourant + ":");

        // afficher liste personnages + monstres: exemple fixe ordre
        for(Personnage p: personnages) {
            System.out.printf(" %-4s %s%n", p.getAbbreviation(), p.toString());
        }
        for(Monstre m: monstres) {
            System.out.printf(" %-4s %s%n", m.getAbbreviation(), m.toString());
        }

        // header colonnes
        System.out.print("    ");
        for(char c='A'; c < 'A'+LARGEUR; c++)
            System.out.printf(" %c ", c);
        System.out.println();
        System.out.print("   *");
        for(int i=0; i<LARGEUR; i++) System.out.print("---");
        System.out.println("*");

        // afficher carte ligne par ligne
        for(int y=0; y<HAUTEUR; y++) {
            System.out.printf("%2d |", (y+1));
            for(int x=0; x<LARGEUR; x++) {
                if(posPersonnages[y][x] != null) {
                    System.out.print(posPersonnages[y][x]==joueurActif ? "->" : " "+ posPersonnages[y][x].getAbbreviation());
                } else if(posMonstres[y][x] != null) {
                    System.out.print(" "+ posMonstres[y][x].getAbbreviation());
                } else {
                    System.out.print("  "+carte[y][x]);
                }
            }
            System.out.println(" |");
        }
        System.out.print("   *");
        for(int i=0; i<LARGEUR; i++) System.out.print("---");
        System.out.println("*");

        System.out.println(" * Equipement | [ ] Obstacle |");

        joueurActif.afficherDetails();

        System.out.printf("%n%s il vous reste %d actions que souhaitez vous faire ?%n", joueurActif.nom, joueurActif.actionsRestantes);
        System.out.println("  - laisser le maître du jeu commenter l'action précédente (mj <texte>)");
        System.out.println("  - commenter action précédente (com <texte>)");
        System.out.println("  - attaquer (att <Case>, ex: att D4)");
        System.out.println("  - se déplacer (dep <Case>)");
        System.out.println("  - s'équiper (equ <numero equipement>)");
    }

    Personnage prochainJoueur() {
        if(personnages.isEmpty()) return null;
        Personnage premier = personnages.remove(0);
        personnages.add(premier);
        tourCourant++;
        premier.actionsRestantes=3;
        return premier;
    }

    // D'autres méthodes de jeu ici (attaquer, deplacer, equiper etc)
}


public class Jeu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Personnage> joueurs = new ArrayList<>();

        System.out.println("Création des personnages :");
        for(int i=0;i<3;i++) {
            System.out.printf("Nom du personnage %d : ", i+1);
            String nom = sc.nextLine();
            System.out.print("Race (Humain, Nain, Elfe, Halfelin) : ");
            Race race = Race.valueOf(sc.nextLine());
            System.out.print("Classe (Guerrier, Clerc, Magicien, Roublard) : ");
            Classe classe = Classe.valueOf(sc.nextLine());
            joueurs.add(new Personnage(nom, race, classe));
        }

        // Création exemple monstres
        List<Monstre> monstres = new ArrayList<>();
        monstres.add(new Monstre("Demogorgon", 1));
        monstres.add(new Monstre("Dragon bleu", 1));

        Donjon donjon = new Donjon(joueurs, monstres);

        Personnage joueurActif = joueurs.get(0);

        while(true) {
            donjon.afficherDonjon(joueurActif);

            String input = sc.nextLine().trim();
            if(input.startsWith("mj ")) {
                System.out.println("Maître du jeu: "+input.substring(3));
            } else if(input.startsWith("com ")) {
                System.out.println(joueurActif.nom+" commente: "+input.substring(4));
            } else if(input.startsWith("att ")) {
                System.out.println("Attaque sur la case "+input.substring(4));
                joueurActif.actionsRestantes--;
            } else if(input.startsWith("dep ")) {
                System.out.println("Déplacement vers la case "+input.substring(4));
                joueurActif.actionsRestantes--;
            } else if(input.startsWith("equ ")) {
                try {
                    int num = Integer.parseInt(input.substring(4).trim());
                    if(num>0 && num <= joueurActif.inventaire.size()) {
                        Equipement e = joueurActif.inventaire.get(num-1);
                        if(e.type.equals("arme")) joueurActif.armeEquipee = e;
                        else joueurActif.armureEquipee = e;
                        System.out.println("Equipé: "+e.nom);
                        joueurActif.actionsRestantes--;
                    } else {
                        System.out.println("Numéro invalide");
                    }
                } catch(Exception ex) {
                    System.out.println("Commande invalide");
                }
            } else {
                System.out.println("Commande incorrecte");
            }

            if(joueurActif.actionsRestantes <= 0) {
                joueurActif = donjon.prochainJoueur();
            }
        }
    }
}
