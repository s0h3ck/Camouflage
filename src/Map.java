/**
 * Gestion d'une grille (soit une map) d'un jeu 
 * @author Danick Côté-Martel
 */
public class Map {
    
    private String       _nom;         // Nom de la map
    private final int    _nbrLignes;   // Nombre de lignes
    private final int    _nbrColonnes; // Nombre de colonnes
    private final char   _map[][];     // Plateau du jeu Camouflage (grille 4 x 4)
    
    /**
     * Constructeur par défaut de la classe Map
     * <p>
     * Initialise chaque case de la grille (soit la map) avec des espaces.
     * </p>
     */
    public Map() {
        _nbrLignes   = 4;
        _nbrColonnes = 4;
        
        _map = new char[_nbrLignes][_nbrColonnes];
        
        /*
        Initialisation de la map avec espace
        */
        for( int i = 0 ; i < _nbrLignes ; i++ ) {       //   X 
            for( int j = 0 ; j < _nbrColonnes ; j++ ) { // x Y => XY cases
                _map[i][j] = '_';
            }
        }
    }
    
    /**
     * Constructeur par défaut de la classe Map
     * <p>
     * Initialise chaque case de la grille (soit la map) avec des espaces.
     * </p>
     * @param ligne nombre de ligne de la grille
     * @param colonne nombre de colonne de la grille
     */
    public Map(int ligne, int colonne) {
        /*
        Initialisation de la map avec espace
        */
        System.out.print("TEST");
        _nbrLignes   = ligne;
        _nbrColonnes = colonne;
        
        _map = new char[_nbrLignes][_nbrColonnes];
        
        for( int i = 0 ; i < _nbrLignes ; i++ ) {       //   X 
            for( int j = 0 ; j < _nbrColonnes ; j++ ) { // x Y => XY cases
                _map[i][j] = '_';
            }
        }
    }
    
    /**
     * Affiche la map
     */
    public void affiche() {
        for( int i = 0 ; i < _nbrLignes ; i++ ) {       //   X 
            for( int j = 0 ; j < _nbrColonnes ; j++ ) { // x Y => XY cases
                System.out.print(_map[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Setteur d'une case de la map
     * @param i indice à la ligne i
     * @param j indice à la colonne j
     * @param c le caractère a écrire dans une case de la map
     */
    public void setCase(int i, int j, char c) {
        if( i < 0 || j < 0 || i > _nbrLignes || j > _nbrColonnes) 
            throw new ArrayIndexOutOfBoundsException();
        _map[i][j] = c;
    }
    
    /**
     * Getteur d'une case de la map
     * @param i indice à la ligne i
     * @param j indice à la colonne j
     * @return la case de la map à l'indice i, j donné
     */
    public char getCase(int i, int j) {
        if( i < 0 || j < 0 || i > _nbrLignes || j > _nbrColonnes) 
            throw new ArrayIndexOutOfBoundsException();
        return _map[i][j];
    }
}
