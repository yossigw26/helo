
package xmlStuff;

import CardsEngine.Card;
import PlayerEngine.ComputerPlayer;
import PlayerEngine.HumanPlayer;
import static generated.CardColor.BLUE;
import static generated.CardColor.GREEN;
import static generated.CardColor.RED;
import static generated.CardColor.YELLOW;
import static generated.CardType.CHANGE_COLOR;
import static generated.CardType.CHANGE_DIRECTION;
import static generated.CardType.NUMBER;
import static generated.CardType.PLUS;
import static generated.CardType.STOP;
import static generated.CardType.TAKI;
import generated.Cards;
import generated.Direction;
import generated.PlayerType;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class InvertToEngine 
{
    protected static Queue<Card> invertsDeck(Cards deckStack) 
    {
        try 
        {
            Queue<Card> deck = new LinkedList<>();
            if (!deckStack.getCard().isEmpty())
            {
                deck.addAll(invertListOfCards(deckStack.getCard()));
            }
            return deck; 
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    protected static Stack<Card> invertsHeap(Cards pileStack) 
    {
        Stack<Card> heap = new Stack<>();
        if (!pileStack.getCard().isEmpty())
        {
            heap.addAll(invertListOfCards(pileStack.getCard()));
        }
        
        return heap;  
    }
    protected static PlayerEngine.Player invertPlayer(generated.Player player) 
    {
        PlayerEngine.Player newPlayer;
        List<Card> listOfCards=new LinkedList<>();
        String name=player.getName();
        boolean isHuman=isHumanPlayer(player.getType());
        
        while (!player.getCards().getCard().isEmpty())
        {
            if (player.getCards().getCard() != null)
            {
                listOfCards.add(invertCard(player.getCards().getCard().remove(0)));
            }
        }
        if (isHuman)
        {
            newPlayer=new HumanPlayer(name,listOfCards);
        }
        else
        {
            newPlayer=new ComputerPlayer(name,listOfCards);
            
        }  
        return newPlayer;
    }
    protected static Integer invertCurrentPlayerTurn(String currentplayer,List<PlayerEngine.Player> playersList) 
    {
        String turn;
        Integer CurrentTurn =0, indexer = 0;
        
        for (PlayerEngine.Player player : playersList)
        {
            if (player.getName().equals(currentplayer))
            {
                CurrentTurn = indexer;
            }
            indexer++;
        }
        
        return CurrentTurn;
    }
    protected static void invertDirection(Direction direction) 
    {        
        if(direction.value().equals("clockwise"))
        {
            XMLStuff.newEngine.setIsSwitchDirectionActivate(false);
        }
        else 
        {
            if (direction.value().equals("counterClockwise"))
            {
            XMLStuff.newEngine.setIsSwitchDirectionActivate(true);
            }
        }
    }
    private static Card invertCard(Cards.Card card) 
    {
        CardsEngine.Card.e_Rank rank ;
        CardsEngine.Card.e_Color color;
       color=getColorFromCard(card);
       rank=getRankFromCard(card);
       return (new Card(rank,color)); 
    }
    private static Card.e_Rank getRankFromCard(Cards.Card card) 
    {
        Card.e_Rank rank ;
        
         switch (card.getType())
        {
                case CHANGE_COLOR:
                {
                    rank= Card.e_Rank.SWITCHCOLOR;
                    break;
                }
                case CHANGE_DIRECTION:
                {
                    rank= Card.e_Rank.SWITCHDIRECTION;
                     break;
                }
                case NUMBER:
                {
                    rank=getNumericRank(card.getNumber());
                    break;
                }
                case STOP:
                {
                    rank= Card.e_Rank.STOP;
                    break;
                }
                case TAKI:
                {
                    rank= Card.e_Rank.TAKI;
                    break;
                }
                case PLUS:
                {
                    rank= Card.e_Rank.PLUS;
                    break;
                }
                default :
                     rank= Card.e_Rank.FOUR;
                    break;
            }
        
        return (rank);
    }
    private static Card.e_Color getColorFromCard(Cards.Card card)
    {
        Card.e_Color color ;
        if (card.getColor() != null)
        {
            switch (card.getColor())
            {
                case BLUE:
                {
                    color= Card.e_Color.BLUE;
                    break;
                }
                case GREEN:
                {
                    color= Card.e_Color.GREEN;
                    break;
                }
                case RED:
                {
                    color=Card.e_Color.RED;
                    break;
                }
                case YELLOW:
                {
                    color=Card.e_Color.YELLOW;
                    break;
                }
                default:
                {
                    color= Card.e_Color.NO_COLOR;
                    break;
                }
            }
        }
        else
        {
           color= Card.e_Color.NO_COLOR;            
        }
        
      return (color);
     
    }
    private static Card.e_Rank getNumericRank(Integer number) 
    {
         Card.e_Rank rank=null;
         
         if (number==1)
         {
             rank= Card.e_Rank.PLUSTWO;
         }
         else if (number==2)
         {
             rank= Card.e_Rank.PLUSTWO;
         }
         else if (number==3)
         {
             rank= Card.e_Rank.THREE;
         }
          else if (number==4)
         {
             rank= Card.e_Rank.FOUR;
         }
          else if (number==5)
         {
             rank= Card.e_Rank.FIVE;
         }
          else if (number==6)
         {
             rank= Card.e_Rank.SIX;
         }
          else if (number==7)
         {
             rank= Card.e_Rank.SEVEN;
         }
          else if (number==8)
         {
             rank= Card.e_Rank.EIGHT;
         }
          else if (number==9)
         {
             rank= Card.e_Rank.NINE;
         }
          return (rank);
    }
    private static  boolean isHumanPlayer(PlayerType type) 
    {
        boolean retValue;
        if (type.name().equals("HUMAN"))
        {
            retValue=true;
        }
        else
        {
            retValue=false;
        }
        
        return retValue;
    }    
    protected static List<Card> invertToListOfPlayerCards(generated.Player player)
    {
        return invertListOfCards(player.getCards().getCard());
    
    }              
    private static List<Card> invertListOfCards(List<generated.Cards.Card> cards) 
    {
        if (!cards.isEmpty())
        {
            List<Card> myNewCards = new LinkedList<>();
            for (generated.Cards.Card card : cards)
            {
                myNewCards.add(invertCard(card));
            }
            return myNewCards;
        }
        else
        {
            return null;
         }
    }
}
