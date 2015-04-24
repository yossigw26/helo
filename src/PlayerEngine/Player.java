
package PlayerEngine;

import CardsEngine.Card;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public abstract class Player 
{  
    protected String name;
    private boolean isActivePlayer;    
    protected java.util.List<Card> listOfCards; 
    
    abstract public boolean isIHuman();
    abstract public CardsEngine.Card playMyTurn(String HumanPlayerChice,Card lastThrownCard);
    
    public Player(String i_Name)
    {
            this.name = i_Name;
            isActivePlayer = true;
            listOfCards  = new ArrayList<>();
    }
    public List<Card> returnMyCardAfterMyLost()
    {
        return listOfCards; 
    }    
    public void DeActivePlayer()
    {
        isActivePlayer = false;
    }    
    //Add card to new player
    public void addToNewPlaerCards(List<Card> i_ListOfCardsToAdd)            
    {
        if (listOfCards.size() > 0 )
        {
            listOfCards.removeAll(listOfCards);
        }
        for(Card cardtoadd :i_ListOfCardsToAdd )
            listOfCards.add(cardtoadd);
    } 
    public boolean addToExisitingPlaerCards(List<Card> i_ListOfCardsToAdd)            
    {
        if (!i_ListOfCardsToAdd.isEmpty())
        {
            for(Card cardtoadd :i_ListOfCardsToAdd )
                listOfCards.add(cardtoadd);
            
            return true;
        }
        else
        {
            return false;
        }
    }
    public String getName()
    {
        return name; 
    }    
    public int getNumberOfCards()
    {
        return listOfCards.size();
    }
    public String priintMyHand() 
    {
        String myHand = new String();

        Integer cardIndexer = 1;
        
        for (Card cardToAdd : listOfCards)     
        {
            if (cardToAdd != null)
            {
                myHand = myHand + cardIndexer ;
                myHand = myHand + ':' ;
                myHand = myHand + cardToAdd.toString() ;
                myHand = myHand + '\n'; 
                cardIndexer++;
            }               
            
        } 
        myHand = myHand + cardIndexer++ ;
        myHand = myHand + ':' ;
        myHand = myHand + "Take Card From The Deck?" ;
        myHand = myHand + '\n';
        
        myHand = myHand + cardIndexer++ ;
        myHand = myHand + ':' ;
        myHand = myHand + "Resign From The Game?" ;
        myHand = myHand + '\n';
        
        myHand = myHand + cardIndexer ;
        myHand = myHand + ':' ;
        myHand = myHand + "Save The Game?" ;
        myHand = myHand + '\n';
        
        return myHand;   
    }    
    public Card getCardFromMyListByIndex(Integer playerChoice) 
    {
        return (listOfCards.get(playerChoice));                
    }
    public Card throwHisPlusTwoCard()
    {
        for (Card card : listOfCards)
        {
            if (card.rank == Card.e_Rank.PLUSTWO)
            {
                return card;
            }
        }
        return null;
    }
    public void setActive(boolean b) 
    {
        isActivePlayer = b;
    }
    public boolean getActive() 
    {
        return isActivePlayer ;
    }
    public boolean isHavePlusTwoCard() 
    {
        for(Card card : listOfCards)
        {
            if (card.rank == Card.e_Rank.PLUSTWO)
            {
                return true;
            }
        }
        return false;
    }

    public List<Card> getHand() 
    {
        return listOfCards;
    }

    public void setHand(List<Card> hand) 
    {
        listOfCards.removeAll(listOfCards);
        listOfCards.addAll(hand);
    }
}
