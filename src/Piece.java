/**
 * Gestion d'une pièce 
 * @author Danick Côté-Martel
 */
public class Piece {
    
    private char _nom;                          // Nom de la pièce
    protected char _piece[][] = new char[2][2]; // Les cases des pièces (4 x 4)
    
    /**
     * Constructeur par défaut de la pièce
     * <p>
     * Initialise chaque cases à la valeur par défaut du type primitif char.
     * </p>
     */
    public Piece() {
        for( int i = 0 ; i < _piece.length ; i++ ) {
            for( int j = 0 ; j < _piece[0].length ; j++ ) {
                _piece[i][j] = '\0';
            }
        }
    }
    
    /**
     * Constructeur de la pièce
     * @param case_1 le symbole de la première case
     * @param case_2 le symbole de la deuxième case
     * @param case_3 le symbole de la troisième case
     * @param case_4 le symbole de la quatrième case
     */
    public Piece(char case_1, char case_2, char case_3, char case_4) {
        _piece[0][0] = case_1;
        _piece[0][1] = case_2;
        _piece[1][0] = case_3;
        _piece[1][1] = case_4;
    }
    
    /**
     * Setteur d'une case de la pièce
     * @param i indice à la ligne i
     * @param j indice à la colonne j
     * @param c le caractère a écrire dans une case de la pièce
     */
    public void setCase(int i, int j, char c) {
        if( i < 0 || j < 0 || i > 2 || j > 2) 
            throw new ArrayIndexOutOfBoundsException();
        _piece[i][j] = c;
    }
    
    /**
     * Getteur d'une case de la pièce
     * @param i indice à la ligne i
     * @param j indice à la colonne j
     * @return la case à l'indice i, j
     */
    public char getCase(int i, int j) {
        if( i < 0 || j < 0 || i > 2 || j > 2) 
            throw new ArrayIndexOutOfBoundsException();
        return _piece[i][j];
    }
    
    /**
     * Setteur du nom de la pièce
     * @param nom nom de la pièce (un caractère)
     */
    public void setNom(char nom) {
        _nom = nom;
    }
    
    /**
     * Getteur du nom de la pièce
     * @return le nom de la pièce
     */
    public char getNom() {
        return _nom;
    }
    
    /**
     * Rotation de la pièce dans le sens anti-horaire
     * <p>
     * Permute les anti-diagonales
     * Permute les lignes
     * </p>
     */
    public void rotation() {
        /*
        On passe en revue tout le tableau et
        on permute les anti-diagonales.
        */
        for( int i = 0; i < _piece.length ; i++ ) {
            for( int j = i; j < _piece[0].length; j++ ) { 
                char tmp = _piece[i][j];
                _piece[i][j] = _piece[j][i];
                _piece[j][i] = tmp;
            }
        }
        
        /*
        On permtute ensuite les lignes entre elles.
        */
        for( int  i = 0, k = _piece.length - 1; i < k; ++i, --k ) {
            char[] tmp = _piece[i];
            _piece[i] = _piece[k];
            _piece[k] = tmp;
        }
    }
}
