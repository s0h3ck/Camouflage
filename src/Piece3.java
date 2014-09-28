/**
 * Gestion de la pièce à trois cases
 * <pre>
 *            _  _
 *           |_||_|
 *           |_|
 * </pre>
 * @author Danick Côté-Martel
 */
public class Piece3 extends Piece {
    
    /**
     * Constructeur par défaut de la Piece3 qui appel le constructeur de Piece
     */
    public Piece3() {
        super();
    }
    
    /**
     * Constructeur de la pièce
     * @param case_1 le symbole de la première case
     * @param case_2 le symbole de la deuxième case
     * @param case_3 le symbole de la troisième case
     * @param case_4 le symbole de la quatrième case
     */
    public Piece3(char case_1, char case_2, char case_3, char case_4) {
        super(case_1, case_2, case_3, case_4);
    }
    
    /**
     * Rotation de la pièce
     */
    @Override
    public void rotation() {
        super.rotation();
    }
}
