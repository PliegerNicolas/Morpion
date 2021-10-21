import java.util.Arrays;
import java.util.Scanner;

public class Morpion {
    public static void main(String[] args) {

        // Variables

        boolean joueurActif = true; // Initialisation du joueur actif
        int tourEnCours = 0;
        // Placements occupés par les joueurs (0 => X et 1 => Ø)
        Integer[][] placementsJoueurs = {{},{}};
        // Tableau de charactères => Array multi-dimensionnel à 2 dimensions
        char[][] plateau = {
                {'|', '1', '|', '2', '|','3', '|'}, // 1 ; 2 ; 3
                {'-', '-', '-', '-', '-','-', '-'},
                {'|', '4', '|', '5', '|','6','|'}, // 4 ; 5 ; 6
                {'-', '-', '-', '-', '-','-', '-'},
                {'|', '7', '|', '8', '|','9', '|'} // 7 ; 8 ; 9
        };
        // Conditions de victoire
        Integer[][] conditionsDeVictoire = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {1, 5, 9},
                {3, 5, 7}
        };

        // Execution

        afficherReglesDeJeu();
        do {
            // Incrémenter nombre de tours
            tourEnCours++;
            System.out.println("\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n" + "Tour numéro : " + tourEnCours +"\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n");
            lancerTourDeJeu(plateau, placementsJoueurs, joueurActif);
            joueurActif = !joueurActif;
        } while(!verifierVictoire(placementsJoueurs, conditionsDeVictoire) && tourEnCours < 9);
        if(tourEnCours >= 9) {
            System.out.print("Plateau rempli. ");
        }
        System.out.println("Fin de partie.");
    }



    // Methodes



    // Méthode affichant le plateau de jeu.
    public static void afficherPlateau(char[][] plateau) {
        for(char[] ligne : plateau) {
            // Exécuter pour chaque array (renommé ligne) appartenant à plateau
            System.out.println(ligne);
        }
        System.out.println();
    }

    // Procédé d'un tour de Jeu
    public static void lancerTourDeJeu(char[][] plateau, Integer[][] placementJoueurs, Boolean joueurActif) {
        afficherPlateau(plateau);
        Scanner scan = new Scanner(System.in); // Initialisation quant à l'écriture dans la console.
        // Demander valeur valide à l'utilisateur.
        int position;
        do {
            // Afficher action attendue par le joueur.
            System.out.println(afficherNomJoueur(joueurActif) + ", sélectionnez votre case en écrivant dans la console le numéro valide affilié.");
            position = scan.nextInt();
        } while(position < 1 || position > 9 || !verifierPosition(placementJoueurs, position)); // Vérifier positions prises et valeurs de positions invalides.
        stockerPositionsJoueurs(placementJoueurs, position, joueurActif); //Mémoriser les positions sélectionnés par les joueurs.
        inscrireSymbole(plateau, position, joueurActif); // Inscrire le symbole dans la case sélectionnée précédemment.
    }

    //Stocker la position sélectionnée par les joueurs au cours de la partie.
    public static void stockerPositionsJoueurs(Integer[][] placementJoueurs, int position, Boolean joueurActif) {
        int joueurActifPourArray = (joueurActif) ? 0 : 1;
        Integer[] arr = placementJoueurs[joueurActifPourArray];
        arr = arrayPush(arr.length, arr, position);
        placementJoueurs[joueurActifPourArray] = arr;
    }

    public static boolean verifierVictoire(Integer[][] placementJoueurs, Integer[][] conditionsDeVictoire) {
        //Positions sélectionnées par les joueurs
        Integer[] positionJoueurX = placementJoueurs[0];
        Integer[] positionJoueurO = placementJoueurs[1];

        // Tests de vérification
        for (Integer[] condition : conditionsDeVictoire) {
            if(Arrays.asList(positionJoueurX).containsAll(Arrays.asList(condition))) {
                System.out.println("Victoire du joueur X.");
                return true;
            } else if(Arrays.asList(positionJoueurO).containsAll(Arrays.asList(condition))) {
                System.out.println("Victoire du joueur 0.");
                return true;
            }
        }
        return false;
    }

    //Vérifier que position pas déjà occupée par un joueur
    public static boolean verifierPosition(Integer[][] placementJoueurs, int position) {
        for(Integer[] positionsJoueurs : placementJoueurs) {
            for(Integer pos : positionsJoueurs) {
                if (pos == position) {
                    return false;
                }
            }
        }

        return true;
    }

    // Inscrire le symbole du joueur actif.
    public static void inscrireSymbole(char[][] plateau, int position, Boolean joueurActif) {

        char symbole = (joueurActif) ? 'X' : 'Ø';

        switch(position) {
            case 1:
                plateau[0][1] = symbole;
                break;
            case 2:
                plateau[0][3] = symbole;
                break;
            case 3:
                plateau[0][5] = symbole;
                break;
            case 4:
                plateau[2][1] = symbole;
                break;
            case 5:
                plateau[2][3] = symbole;
                break;
            case 6:
                plateau[2][5] = symbole;
                break;
            case 7:
                plateau[4][1] = symbole;
                break;
            case 8:
                plateau[4][3] = symbole;
                break;
            case 9:
                plateau[4][5] = symbole;
                break;
        }
    }

    public static void afficherReglesDeJeu() {
        // Afficher les règles du jeu.
        System.out.println("Régles de jeu du Morpion :\nCe jeu se joue à deux. " +
                "Pour gagner, il faut aligner trois symboles identiques sur le plateau \navant son " +
                "adversaire (horizontalement, verticalement ou diagonalement). Chaque joueur dispose\nde " +
                "son propre symbole, la croix (X) pour le joueur \"1\" et le rond (O) pour le joueur \"2\"." +
                "\n\nLes valeurs affichés sur le tableau sont les identifiants des positions disponibles " +
                "au placement de son symbole.\n");
    }

    public static String afficherNomJoueur(Boolean joueurActif) {
        return (joueurActif) ? "Joueur [X]" : "Joueur [Ø]";
    }

    // Fonction pour push un élément dans un array (ajouter un élément à la fin de l'array).
    public static Integer[] arrayPush(int nbElements, Integer[] array, int valeurAPush)
    {
        int i;

        // Ajuster la taille pour le nouvel array.
        Integer[] newArray = new Integer[nbElements + 1];

        // Passer les éléments dans le nouvel array
        for (i = 0; i < nbElements; i++)
            newArray[i] = array[i];

        newArray[nbElements] = valeurAPush;

        return newArray;
    }
}