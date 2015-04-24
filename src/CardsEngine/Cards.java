
package CardsEngine;

import CardsEngine.Card.e_Color;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class Cards 
{

    private Stack<Card> heap = new Stack<>();    
    private Queue<Card> deck = new LinkedList<>();
    
    
    public void newCards()
    {
        for (Card.e_Rank ernk : Card.e_Rank.values())
        {
            if (ernk==Card.e_Rank.SWITCHCOLOR)
                    continue;
            for (Card.e_Color eclr : Card.e_Color.values() )
            {
                if (eclr==Card.e_Color.NO_COLOR)
                    continue;
                deck.add(new Card(ernk,eclr));
            } 
         }
         deck.add(new Card(Card.e_Rank.SWITCHCOLOR,Card.e_Color.NO_COLOR));
         deck.add(new Card(Card.e_Rank.SWITCHCOLOR,Card.e_Color.NO_COLOR));
    }
    
    public List<Card> retriveFromDeck(int i_SizeOfCards)
    {
        List<Card> o_CardListToReturn = new LinkedList<>();
        
        if (i_SizeOfCards > deck.size())
        {
            retriveFromHeapToDeck();
            
        }
        for (int i=0; i<i_SizeOfCards;i++)
        {
            o_CardListToReturn.add(deck.poll());            
        }
        
        return o_CardListToReturn;
        
    }
    public void insertToHeap()
    {
        heap.add(deck.poll());
    }

    public void retriveFromHeapToDeck() 
    {
         deck.addAll(heap);    
         heap.removeAllElements();
         heap.add(deck.poll());
              
    }
    
    public Card getTopCardFromHeap()
    {
        if(heap.size() > 0)
        {
            return (heap.peek());
        }
        else
        {
            insertToHeap();
            return (heap.peek());
        }
    }
    
    public List<Card> divCardsToNewPlayer() 
    {        
        int i;
        List<Card> o_PlayersCards = new LinkedList<>();
        
        for(i=0;i<8;i++)
        {            
            o_PlayersCards.add(this.deck.poll());
        }
         
        return o_PlayersCards;
    }
        
    public void addCardsToDeckFromPLayerWhoLose(List<Card> i_CardList)
    {
        for(Card card : i_CardList)
        {
            deck.add(card);
        }
    }

    public void newDeckCard() 
    {
        retriveFromHeapToDeck();
        Collections.shuffle((List)deck);
        while (isLegalCardToStartPlayWith(deck.peek()) == false)
        {
            Collections.shuffle((List)deck);
        }
        heap.add(deck.poll());
        
    }

    public void addCardsToHeapFromPlayer(Card cardThatWasThrownByTheLastPlayer) 
    {
        heap.add(cardThatWasThrownByTheLastPlayer);
    }

    public String printLastThrownCard() 
    {
       if (!heap.empty())
       {
           return (heap.peek().toString());    
       }
       return "no Card";
    }
    private boolean isLegalCardToStartPlayWith(Card card) 
    {
        if ((card.color == e_Color.NO_COLOR))
        {
            return false;
        }
        return true;
    }

    public void createNewDeckFromXml(Queue<Card> listOfCard)
    {
        if (!listOfCard.isEmpty())
        {
            if (!deck.isEmpty())
            {
                deck.clear();
            }
            try
            {
               deck.addAll(listOfCard); 
            }
            catch(Exception e)
            {
                 heap.clear();
            }
            
        }
    }

    public void createNewHeapFromXml(Stack<Card> listOfCard) 
    {
        if (!listOfCard.isEmpty())
        {
            if (!heap.isEmpty())
            {
                heap.clear();
            }
            try
            {
                heap.addAll(listOfCard);  
            }
            catch(Exception e)
            {
                 heap.clear();
            }
        }
    }

    public Stack<Card> getHeap()
    {
        return heap;
    }

    public Queue<Card> getDeck() 
    {
        return deck;
    }

    public void setDeck(Queue<Card> deckRecieved) 
    {
        if(!deck.isEmpty())
        {
            this.deck.clear();
        }
        deck.addAll(deckRecieved);
        
    }

    public void setHeap(Stack<Card> heapRecieved) 
    {
        if (!heap.empty())
        {
            this.heap.clear();
        }
        heap.addAll(heapRecieved);
        
    }

    public void addCardsToHeapFromPLayerWhoLose(List<Card> returnMyCardAfterMyLost) 
    {
         for(Card card : returnMyCardAfterMyLost)
        {
            heap.add(card);
        }    
    }

}
