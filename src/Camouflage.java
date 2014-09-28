import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Gestion d'un algorithme de solution du jeu Camouflage
 * @author Danick Côté-Martel
 */
public class Camouflage {
    
    private final int _nbrPieces;                          // Nombre de pièces dans le jeu camouflage
    
    private final Map _map;                                // Map du puzzle
    private final Piece _piece[];                          // Tableau contenant les six pièces du puzzle
    private final String _solution[][] = new String[4][4]; // Solution du puzzle (incluant les six pièces)
    
    /**
     * Constructeur par défaut
     * <p>
     * Initialisation de la map
     * Définitions des pièces du jeu
     * Initialisation du tableau solution (avec des espaces)
     * </p>
     */
    public Camouflage() {
        _nbrPieces = 6;
        _piece = new Piece[_nbrPieces]; 
                
        /*
        Initialisation de la map
        */
        _map = new Map(); 
        
        /*
        On définit nos six pièces du jeu Camouflage
        */
        _piece[0] = new Piece3(   ' ',   'P',   'O',  '\0' ); // Pièce U
        _piece[1] = new Piece3(   'P',   ' ',   'O',  '\0' ); // Pièce V
        _piece[2] = new Piece3(   ' ',   'O',   'P',  '\0' ); // Pièce W
        _piece[3] = new Piece2(   'P',   'P',  '\0',  '\0' ); // Pièce X
        _piece[4] = new Piece2(   'P',   'O',  '\0',  '\0' ); // Pièce Y
        _piece[5] = new Piece3(   'O',   ' ',   ' ',  '\0' ); // Pièce Z
        
        /*
        On donne le nom exact aux six pièces dans l'ordre alphabétique,
		soit U, V, W, X, Y, Z. 
        */
        for(int i = 0; i < _nbrPieces ; i++ )
            _piece[i].setNom( (char)(85+i) );
    }
    
    /**
     * Initialise la map du jeu.
     * @param map la map du puzzle
     * @return true si la map s'est initialisée sans problème
     */
    public boolean initialiser(char map[][]) {
        try {
            for(int i = 0 ; i < map.length ; i++) {
                for(int j = 0 ; j < map[0].length ; j++) {
                    _map.setCase(i, j, map[i][j]);
                    _solution[i][j] = "__";
                }
            }
        } catch( Exception e ) {
            System.out.println("L'initialisation a échoué. Voici l'erreur : " + e);
            return false;
        }
        
        /*
        On initialise aussi le tableau de solution avec des espaces
        */
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 0 ; j < 4; j++) {
                _solution[i][j] = "__";
            }
        }
        
        return true;
    }
    
    /**
     * Solutionne la puzzle du jeu.
     * @return true s'il y a une solution au puzzle
     */
    public boolean solutionner() {
        return solutionner(0);
    }
    
    /**
     * Solutionne la map du jeu.
     * @param noPiece le numero de la pièce
     * @return true s'il y a une solution au puzzle
     */
    private boolean solutionner(int noPiece) {
        if( noPiece == 6 )
            return true;
        
        /*
        
        Explication du processus :
        
        But: Vérifier toutes les possibilités des six pièces sur le plateau de jeu.
        Comment : 
            1. On entre dans la boucle de rotation (4 rotation (90°) par pièce)
            2. On entre dans la boucle des translations en y
            3. On entre dans la boucle des translations en x
        
        Prendre note que le référentiel est le suivant :
        
            X vers le bas
            Y vers la droite

                 _ _ _ _ _ _Y__ 
                |   _  _
                |  |_||_|
                |  |_||_|
              X |
                |
        
        Ensuite, on vérifie si la pièce fonctionne sur la map à la rotation r, translation x et y.
        Si isMatch() renvoie true, cela nous confirme que la pièce est valide sur le jeu
        et donc placable dans l'emplacement mémoire pour la solution.
        Ainsi, on appel placerPiece qui place la pièce.
        
        Finalement, on vérifie si la pièce qui suit a un emplacement,
        si oui, on continue avec la pièce suivante
        si non, on retire la pièce et on trouve un autre emplacement avec la pièce précédente
        
        Si les six pièces sont placés, l'algorithme trouve une solution et renvoie true.
        
        */
        for(int r = 0 ; r < 4 ; r++) {                                   // Rotation r
            try {                                            
                for( int y = 0 ; y < 4 ; y++ ) {                         // Translation y
                    try {
                        for(int x = 0 ; x < 4 ; x++ ) {                  // Translation x
                            if( isMatch(_piece[noPiece], x, y) ) {       // Existe-t-il un emplacement pour cette pièce ? Si oui...
                                placerPiece( _piece[noPiece], x, y );    // On place la pièce
                                
                                if( !solutionner(noPiece+1) ) {          // Peut-on solutionner la pièce suivante ? Si non ...
                                    retirerPiece(_piece[noPiece], x, y); // On retire la pièce
                                } else {                                 // Emplacement pour toutes les pièces trouvées,
                                    return true;                         // le puzzle est solutionné! \o/ (retourne true)
                                }
                            }
                        } 
                    }catch(ArrayIndexOutOfBoundsException e) {}          // COLONNE
                }
            }catch(ArrayIndexOutOfBoundsException e) {}                  // LIGNE
            _piece[noPiece].rotation();                                  // Rotation de la pièce
        }
        return false;                                                    // Pas d'emplacement pour la pièce, (retourne false)
    }
    
    /**
     * Vérification de la pièce sur la map
     * @param p le numéro de la pièce
     * @param x la position en x du tableau solution
     * @param y la position en y du tableau solution
     * @return true si la pièce fonctionne sur la map
     */
    private boolean isMatch(Piece p, int x, int y) {
        for(int i = 0 ; i < 2 ; i++ ) {
            for(int j = 0 ; j < 2 ; j++) {
                try {
                    if("__".equals(_solution[i+x][j+y])) {                // Si l'emplacement est disponible ("__")
                        switch( p.getCase(i, j) ) {                       // On vérifie la case de la pièce à la position i, j
                            case 'O':                                     // Si c'est un ours
                                if(_map.getCase(x+i, y+j) != 'B') {       // Et autre qu'une banquise
                                    return false;                         // Retourne false
                                }
                                break;
                            case 'P':                                     // Si c'est un poisson
                                if(_map.getCase(x+i, y+j) != 'E') {       // Et autre que de l'eau
                                    return false;                         // Retourne false
                                }
                                break;
                        }
                    } else {                                              // Si c'est un emplacement non disponible
                        if(p.getCase(i, j) != '\0') {                     // Et que la case n'est pas vide
                            return false;                                 // Forcément, c'est un emplacement non permi, donc on retourne false
                        }
                    }
                } catch( ArrayIndexOutOfBoundsException e ) {             // Si on 
                    if(p.getCase(i, j) != '\0') {                         // Et autre qu'une case vide
                        return false;                                     // Retourne false
                    }
                }
            }
        }
       return true;                                                       // Il existe un emplacement, on retourne true
    }
    
    /**
     * Placer la pièce dans la solution
     * @param p le numéro de la pièce
     * @param x la position en x du tableau solution
     * @param y la position en y du tableau solution
     */
    private void placerPiece(Piece p, int x, int y) {
        for(int i = 0 ; i < 2 ; i++ ) {
            for(int j = 0 ; j < 2 ; j++) {
                if(p.getCase(i, j) != '\0')                               // Si la case de la pièce n'est pas vide (null), on la place dans la solution
                    _solution[x+i][y+j] = String.valueOf(p.getNom()) + String.valueOf(p.getCase(i, j));
            }
        }
    }
    
    /**
     * Retirer la pièce de la solution
     * @param p le numéro de la pièce
     * @param x la position en x du tableau solution
     * @param y la position en y du tableau solution
     */
    private void retirerPiece(Piece p, int x, int y) {
        for(int i = 0 ; i < 2 ; i++ ) {
            for(int j = 0 ; j < 2 ; j++) {
                if(p.getCase(i, j) != '\0')                                // Si la case de la pièce n'est pas vide (null), on rénitialise la case dans la solution 
                    _solution[x+i][y+j] = "__";
            }
        }
    }
    
    /**
     * Affiche la solution du puzzle
     */
    public void afficher() {
        for(int i = 0 ; i < 4 ; i++ ) {
            for(int j = 0 ; j < 4 ; j++) {
                System.out.print(_solution[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Sauvegarde la solution dans un dossier
     * <p>
     * Racine : ./src/resources/sauvegarde/solution[Map].txt
     * </p>
     * @param chemin le nom de la map du puzzle
     * @throws IOException repertoire ou fichier non valide
     */
    public void sauvegarder(String chemin) throws IOException {
        /*
        Instance de la classe BufferedReader pour écrire la solution dans un fichier :D
        On envoie le chemin, soit le nom du fichier source (nom de la map) en argument
        Prendre note qu'on met en majuscule la première lettre du chemin
        */
        BufferedWriter output = new BufferedWriter(new FileWriter("./src/resources/sauvegarde/solution" + chemin.substring(0,1).toUpperCase() + chemin.substring(1) + ".txt", false)); 
        
        output.write("Pour la map suivante :\n");
        for(int i = 0 ; i < 4 ; i++ ) {
            for(int j = 0 ; j < 4 ; j++) {
                output.write(_map.getCase(i,j) + " ");
            }
            output.write('\n');
        }
        
        output.write("\nUne solution a été trouvée :\n");
        for(int i = 0 ; i < 4 ; i++ ) {
            for(int j = 0 ; j < 4 ; j++) {
                output.write(_solution[i][j] + "  ");
            }
            output.write('\n');
        }
        
        output.flush(); // on flush envoie dans le fichier (pour BufferedWriter)
        output.close(); // on ferme le fichier
	
        System.out.println("Solution sauvegardée dans le répertoire /sauvegarde/solution" + chemin.substring(0,1).toUpperCase() + chemin.substring(1) + ".txt\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Camouflage jeu = new Camouflage();                               // Création d'une instance de Camouflage
        
        while(true) {                                                    // Tant que l'utilisateur veut résoudre des puzzles, on continue
            /*
            Lecture du fichier source (soit lecture de la map)
            */
            try {
                Scanner scanner = new Scanner(System.in);                // Création d'une instance de scanner prêt pour le input
                System.out.print("Veuillez saisir le nom de la map : "); // Affichage de la demande du nom de la map
                String source = scanner.nextLine();                      // Saisie du nom de la map 
                         
                BufferedReader fichier = new BufferedReader (               
                                      new FileReader("./src/resources/source/map" + source + ".txt") 
                                      );                                 // Instance de la classe BufferedReader pour lire un fichier
                scanner = new Scanner(fichier);                          // Instance de la classe Scanner vers le fichier
                
                
                scanner.useDelimiter(",?(\\s*)*");                       // Utilisation d'un délimiteur
                
                int ligne   = scanner.nextInt();                         // Lecture du nombre de lignes
                int colonne = scanner.nextInt();                         // Lecture du nombre de colonnes
                
                char grille[][] = new char[ligne][colonne];              // Création de la grille (soit la map) pour stocker les données du fichier
                
                    
                for(int i = 0 ; i < ligne ; i++) {                       // En fonction du nombre de ligne
                    for( int j = 0 ; j < colonne ; j++ ) {               // En fonction du nombre de colonne
                        grille[i][j] = scanner.next().charAt(0);         // Ajout du caractères dans le tableau
                    }
                }
                
                if( jeu.initialiser(grille) ) {                          // Initialisation de la map (on envoie la grille)
                    if( jeu.solutionner() ) {                            // Si l'initialisation n'échoue pas, on essaye de solutionner le puzzle
                        jeu.afficher();                                  // Si une solution, on l'affiche
                        jeu.sauvegarder(source);                         // On enregistre la solution dans un fichier
                    } else {                                             // Sinon, on affiche qu'aucune solution existe pour ce puzzle
                        System.out.println("Impossibilité de résoudre la map.\n");
                    }
                }

                scanner = new Scanner(System.in);                        // Dirige vers le scanner du input
                System.out.print("Une autre map ? (oui/non) : ");        // Demande si l'utilisateur veut résoudre une autre map
                String reponse = scanner.nextLine();                     // Saisie de la réponse de l'utilisateur
                
                
                if( !"oui".equals(reponse) && !"o".equals(reponse)  ) {  // Si ce n'est pas "oui" ou "o", on quitte le programme
                    System.out.println("Au revoir! \\o/");               // Affichage d'un message sympathique
                    break;                                               // Fin de l'algorithme "forceBrute"
                }
                
            } catch (FileNotFoundException exception) {                  // Fichier non trouvé
                System.err.println("Le répertoire ou le fichier n'est pas valide : " + exception.getMessage());
            } catch (IOException exception ) {                           // Lecture ou écriture échouée
                System.err.println("Erreur lors de la lecture du fichier : " + exception);
                exit(0);
            } catch( NumberFormatException exception) {                  // Mauvais format
                System.err.println("Un bon format est requis la prochaine fois : " + exception);
                exit(0);
            } catch( StringIndexOutOfBoundsException | NoSuchElementException  exception ) { 
                                                                         // Aucun caractère à l'indice demandé
                System.err.println("Assurez-vous de respecter le format à l'intérieur du fichier : " + exception);
                exit(0);
            } catch (Exception e) {                                      // Si une autre erreur survient
                exit(0);
            }
        }
    }
}
