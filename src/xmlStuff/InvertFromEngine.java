
package xmlStuff;

import CardsEngine.Card;
import static CardsEngine.Card.e_Color.BLUE;
import static CardsEngine.Card.e_Rank.PLUS;
import static CardsEngine.Card.e_Rank.SWITCHCOLOR;
import gameEngine.EngineGame;
import generated.CardColor;
import generated.CardType;
import generated.Cards;
import generated.Direction;
import generated.ObjectFactory;
import generated.Player;
import generated.PlayerType;
import generated.Taki;
import generated.Taki.Players;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class InvertFromEngine 
{
    public InvertFromEngine (String xmlFileName, EngineGame myEngine) throws JAXBException, FileNotFoundException , Exception
    {
        ObjectFactory factory = new ObjectFactory();

        generated.Taki newTaki = factory.createTaki();
        
        newTaki.setName(xmlFileName);         
        
        newTaki.setCurrentPlayer(myEngine.getCurrentPlayer().getName());
        
        newTaki.setDirection(ConvertDirection(myEngine.getIsSwitchDirectionActivate()));
        
        newTaki.setPile(convertHeapToGeneric(myEngine.getHeap()));
        
        newTaki.setColor(convertColor(myEngine.getTopHeapColor()));
        
        newTaki.setStack(convertDeckToGeneric(myEngine.getDeck()));     
        
        Players players = new Taki.Players();
        
        for (PlayerEngine.Player player : myEngine.getListOfPlayers())
        {
            Player newPlayer = new Player();
            newPlayer.setCards(convertCardsToGeneric(player.getHand()));
            newPlayer.setName(player.getName());
            if (player.isIHuman())
            {
                newPlayer.setType(PlayerType.HUMAN);
            }
            else
            {
                newPlayer.setType(PlayerType.COMPUTER);
            }          
           
            players.getPlayer().add(newPlayer);
        }
        
        newTaki.setPlayers(players);
     
        JAXBContext context = JAXBContext.newInstance("generated");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(newTaki, new FileOutputStream(xmlFileName));
    }
    private static Direction ConvertDirection(boolean direction) 
    {
        if (direction)
        {
            return Direction.COUNTER_CLOCKWISE;
        }
        else
        {
            return Direction.CLOCKWISE;
        }        
    }        
    private static generated.Cards convertHeapToGeneric(Stack<CardsEngine.Card> cards)
    {
        generated.Cards genericCards = new  generated.Cards();
        
        for (CardsEngine.Card card : cards)
        {
            genericCards.getCard().add(convertCard(card));
        }
        return genericCards;
    }    
    private static generated.Cards convertDeckToGeneric(Queue<CardsEngine.Card> cards)
    {
        generated.Cards genericCards = new  generated.Cards();
        
        for (CardsEngine.Card card : cards)
        {
            genericCards.getCard().add(convertCard(card));
          
        }
        return genericCards;
    }
    private static generated.Cards.Card convertCard(CardsEngine.Card card)
    {
       generated.Cards.Card newCard= new generated.Cards.Card();
       CardColor newColor=convertColor(card.color);
       CardType newType=convertType(card.rank);
       if (newColor!=null)
      {   
             newCard.setColor(newColor);
       }
       
       newCard.setType(newType); 
       if (newType== CardType.NUMBER)
       {
           newCard.setNumber(convertNumericType(card.rank));
       }
       return newCard;
        
       }    
    private static CardColor convertColor(CardsEngine.Card.e_Color color) 
    {
        CardColor newColor = null;
        switch (color)
        {
            case BLUE:
            {
                newColor= CardColor.BLUE;
               break; 
            }
            case RED:
            {
                newColor= CardColor.RED;
                break; 
            }
            case GREEN:
            {
                newColor= CardColor.GREEN;
                break; 
            } 
            case YELLOW:
            {
                newColor= CardColor.YELLOW;
                break; 
            }    
            case NO_COLOR:
            {
                newColor=null;
                break;
            }
        }
        return newColor;          
    }
    private static CardType convertType(CardsEngine.Card.e_Rank rank)
    {
        CardType newType;
        switch (rank)
        {
            case PLUS:
            {
                newType= CardType.PLUS;
                break; 
            }
            case STOP:
            {
                newType= CardType.STOP;
                break; 
            }
            case SWITCHCOLOR:
            {
                newType= CardType.CHANGE_COLOR;
                break; 
            } 
            case SWITCHDIRECTION:
            {
                newType= CardType.CHANGE_DIRECTION;
                break; 
            } 
            case TAKI:
            {
                newType= CardType.TAKI;
                break; 
            }
            default:
            {
                newType= CardType.NUMBER;
                break; 
            }   
        }
        return newType;  
    }
    private static Integer convertNumericType(Card.e_Rank Rank)
    {
        Integer number=0;
        
         switch (Rank)
        {
            case ONE:
            {
                number = 1;
                break; 
            }
            case PLUSTWO:
            {
                number = 2;
                break; 
            }
            case THREE:
            {
                 number = 3;
                break; 
            } 
            case FOUR:
            {
                number = 4;
                break; 
            } 
            case FIVE:
            {
                 number = 5;
                break; 
            }
           case SIX:
            {
                 number = 6;
                break; 
            } 
            case SEVEN:
            {
                 number = 7;
                break; 
            }  
            case EIGHT:
            {
                 number = 8;
                break; 
           }  
            case NINE:
            {
                 number = 9;
                break; 
            }    
        
    }
    return number;
}   
    private Cards convertCardsToGeneric(List<Card> hand) 
    {          
        generated.Cards genericCards = new  generated.Cards();
        
        for (CardsEngine.Card card : hand)
        {
            genericCards.getCard().add(convertCard(card));
        }
        return genericCards;   
    }
}

