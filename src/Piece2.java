/**
 * Gestion de la pièce à deux cases
 * <pre>
 *            _  _
 *           |_||_|
 * </pre>
 * @author Danick Côté-Martel
 */
public class Piece2 extends Piece {
        /**
     * Constructeur par défaut de la Piece2 qui appel le constructeur de Piece
     */
    public Piece2() {
        super();
    }
    
    /**
     * Constructeur de la pièce
     * @param case_1 le symbole de la première case
     * @param case_2 le symbole de la deuxième case
     * @param case_3 le symbole de la troisième case
     * @param case_4 le symbole de la quatrième case
     */
    public Piece2(char case_1, char case_2, char case_3, char case_4) {
        super(case_1, case_2, case_3, case_4);
    }
    
    
    /**
     * Rotation de la pièce à deux case
     * <p>
     *   Positionnement du coin supérieur gauche de la pièce 2, de sorte à faire les 4 possibilités.
     * </p>
     */
    @Override
    public void rotation() {
        char temp;
        temp = _piece[0][0];
        _piece[0][0] = _piece[1][0];
        _piece[1][0] = _piece[1][1];
        _piece[1][1] = _piece[0][1];
        _piece[0][1] = temp;
        
        /*
        Ajustement pour placer la pièce de manière à ce qu'elle soit toujours dans le coin supèrieur à gauche
        
        Illustrons : 
                 _  _
            1.  |X||X|
                |¯||¯|
                 ¯  ¯
                 _  _
            2.  |X|| |
                |X||¯|
                 ¯  ¯
                _
            Où |_| contient le caractère '\0' (null)
        */
        
        if(_piece[0][0] == '\0' && _piece[1][0] == '\0') {
            _piece[0][0] = _piece[0][1];
            _piece[1][0] = _piece[1][1];
            _piece[0][1] = '\0';
            _piece[1][1] = '\0';
        }
    }
}
