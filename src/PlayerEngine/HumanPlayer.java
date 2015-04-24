
package PlayerEngine;

import CardsEngine.Card;
import java.util.LinkedList;
import java.util.List;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class HumanPlayer extends Player
{    
    private boolean isRetirement = false;
    
    public HumanPlayer(String name,List<Card> ListOfCards)
    {
        super(name);
        this.listOfCards = new LinkedList<>();
        this.listOfCards.addAll(ListOfCards);
        this.isRetirement = false;  
    }
    public HumanPlayer (String name)
    {        
        super(name);
        this.isRetirement = false;            
    }       
    @Override
    public CardsEngine.Card playMyTurn(String HumanPlayerChice,Card lastThrownCard)
    {
        int PlayerChice = Integer.parseInt(HumanPlayerChice);               
        return (listOfCards.remove(PlayerChice-1));
    }    
    @Override
    public boolean isIHuman()
    {
        return true;
    }   
    
}
