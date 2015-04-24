
package PlayerEngine;

import CardsEngine.Card;
import CardsEngine.Card.e_Color;
import CardsEngine.Card.e_Rank;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class ComputerPlayer extends Player
{    
    public ComputerPlayer(String name,List<Card> ListOfCards)
    {
        super(name);
        this.listOfCards = new LinkedList<>();
        this.listOfCards.addAll(ListOfCards);
    }
    public ComputerPlayer(String name)
    {        
        super(name);         
    }
    
    @Override
    public CardsEngine.Card playMyTurn(String HumanPlayerChice,Card lastThrownCard) 
    {
        int indexOfOutCardInTheList ;
        
        indexOfOutCardInTheList = OptionalCardToThrow(lastThrownCard);
        
        if (indexOfOutCardInTheList == -1)
        {
            return (null);
        }
        else
        {
            return (listOfCards.remove(indexOfOutCardInTheList));
        }
    }    
    @Override
    public boolean isIHuman()
    {
        return false;
    }
    private Integer OptionalCardToThrow(Card lastThrownCard) 
    {
        List<Card> ListOfCardToThrow = new ArrayList<>();
        Integer indexer = 0;
        
        for (Card card : listOfCards)
        {
            if (card != null)
            {
                if ((card.color == lastThrownCard.color) && (lastThrownCard.color!= e_Color.NO_COLOR )) 
                {
                    if (!((lastThrownCard.rank == e_Rank.PLUSTWO) && (card.rank == e_Rank.PLUSTWO)))
                    {
                        return indexer;
                    }
                }
                else
                {
                    if ((card.rank == lastThrownCard.rank)&&(card.rank!= e_Rank.SWITCHCOLOR))
                    {
                        return indexer;
                    }
                }
                indexer++;
            }
        }
        
        if (ListOfCardToThrow.isEmpty())
        {
            indexer=0;
            for (Card card : listOfCards)
            {
                 if (card != null)
                 {
                    if (card.rank == e_Rank.SWITCHCOLOR)
                    {
                        ListOfCardToThrow.add(card);
                        return indexer;                        
                    }
                    indexer++;
                 }
            }
        }       
        return (-1);
    }
}

