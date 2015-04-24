
package gameEngine;

import CardsEngine.Card;
import CardsEngine.Cards;
import PlayerEngine.Player;
import PlayerEngine.ComputerPlayer;
import PlayerEngine.HumanPlayer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class EngineGame 
{    
    public static final int ONECARDTOTAKE =1;
    public static final Integer MINNUMOFPLAYERS = 2;
    public static final Integer MAXNUMOFPLAYERS = 4;
    public static final Integer LASTPLAYER = 1;
    public static final char COMPUTERPLAYER = 'H';
    public static final char HUMANPLAYER = 'c';
    public static final String STOP = "STOP";
    public static final String PLUS = "PLUS";
    public static final String PLUSTWO = "PLUSTWO";
    public static final String TAKI = "TAKI";
    public static final String SWITCHDIRECTION = "SWITCHDIRECTION";
    public static final String SWITCHCOLOR = "SWITCHCOLOR";
    public static final String ALREADYPLAYED = "ALREADYPLAYED";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String NULL = null;
    public static final String YELLOW = "YELLOW";
    public static final String BLUE = "BLUE";
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public String winnerName = null;
    public List<Player> listOfPlayers =  new LinkedList<>();
    public Cards myCard = new Cards();
    private Integer numOfActivePlayers ;
    private int playerTurn ;
    private Integer numOfPlayers ;
    private boolean isTakiActivate = false;
    private boolean isPlusTwoActivate = false;
    private int plusTwoCounter = 0 ;
    private boolean isStopActivate= false;
    private boolean isSwitchColorActivate= false;
    private boolean isSwitchDirectionActivate= false;
    private boolean isPlusActivate= false;
    private boolean isWinner = false;
    private boolean emptyDeck = false;
    private Card cardThatWasThrownByTheLastPlayer ;
    
    public boolean endGame() 
    {
        if (numOfActivePlayers == LASTPLAYER)
        {
            while (listOfPlayers.get(playerTurn%numOfPlayers).getActive()==false)
            {
                playerTurn++;
            }                
            winnerName = listOfPlayers.get(playerTurn%numOfPlayers).getName();            
            return true;            
        }
        else
        {
            if (emptyDeck == true)
            {
                return true;
            }
            for(Player player : listOfPlayers)
            {
                if ((player.getNumberOfCards() == 0)&&(player.getActive()== true))
                {
                    winnerName = listOfPlayers.get(playerTurn%numOfPlayers).getName();
                    return true;
                }
            }
        }        
        return false;        
    } 
    public boolean playHisTurn(String i_HumanPlayerChice) 
    {
        boolean o_WantToQuit = false;                
        try 
        {
            if ((listOfPlayers.get(playerTurn%numOfPlayers).isIHuman()== false)&&(isPlusTwoActivate == true))
            {
                giveCardsToPlayerAfterPlusTwoLost();
            }
            else
            {
                cardThatWasThrownByTheLastPlayer = listOfPlayers.get(playerTurn%numOfPlayers).playMyTurn(i_HumanPlayerChice,myCard.getTopCardFromHeap());

                if (cardThatWasThrownByTheLastPlayer != null)
                {
                    checkThrownCard(cardThatWasThrownByTheLastPlayer);   
                    myCard.addCardsToHeapFromPlayer(cardThatWasThrownByTheLastPlayer);
                }
                else
                {
                    if (!(listOfPlayers.get(playerTurn%numOfPlayers).isIHuman())&&(cardThatWasThrownByTheLastPlayer==null))
                    {
                        if (isPlusTwoActivate == false)
                        {
                            addCardFromDeckToPlayer(ONECARDTOTAKE);                        
                        }
                        else
                        {
                           giveCardsToPlayerAfterPlusTwoLost();
                        }
                    }
                    else
                    {
                        isWinner = TakeCardFromDeckOrResignOrWinner();
                    }  
                }
            }            
        } 
        catch( Exception exception)
        {
            throw (exception);
        } 
        finally
        {
            if (o_WantToQuit)
            {
                returnPlayerCardToHeap(listOfPlayers.get(playerTurn%numOfPlayers));
            }
            return o_WantToQuit;             
        }        
    }    
    public void createNewPlayer(String i_name, char i_DefinePlayer) 
    {
        if (i_DefinePlayer == COMPUTERPLAYER)
        {
            listOfPlayers.add(new ComputerPlayer(i_name));
        }
        else
        {
            listOfPlayers.add(new HumanPlayer(i_name));
        }
    }
    public void newDeckCard() 
    {
        myCard.newDeckCard();
    }
    private void returnPlayerCardToHeap(Player i_Player) 
    {
        myCard.addCardsToDeckFromPLayerWhoLose(i_Player.returnMyCardAfterMyLost());        
    }
    public boolean isPlayerHuman() 
    {
       return (listOfPlayers.get(playerTurn%numOfPlayers).isIHuman());
    }
    public String printHand()    
    {
        return (listOfPlayers.get(playerTurn%numOfPlayers).priintMyHand());
    }
    public void checkPlayerTurnAndHandleTheFollowingEvent(String i_Event) 
    {
        if (i_Event != null)
        {
            switch (i_Event)
            {
                case STOP:
                {
                    isPlusActivate=false;
                    continueToNextPlayer();
                    continueToNextPlayer();
                    break;
                }
                case PLUS:
                {
                    break;
                }
                case TAKI:
                {
                    isPlusActivate=false;
                    if (!listOfPlayers.get(playerTurn%numOfPlayers).isIHuman())
                    {
                        isTakiActivate = false;    
                        continueToNextPlayer();                        
                    }
                    break;       
                }
                case SWITCHDIRECTION:
                {
                    SwitchedDirectionEventHandle();
                    continueToNextPlayer();
                    continueToNextPlayer();
                    isPlusActivate=false;
                    break;
                }
                case PLUSTWO:
                {
                    isPlusActivate=false;                   
                    continueToNextPlayer();
                    break;
                }
                case SWITCHCOLOR:
                {
                    if (listOfPlayers.get(playerTurn%numOfPlayers).isIHuman() == false)
                    {
                        handleChangedColorEventForComputer();                        
                    }
                    isPlusActivate=false;
                    continueToNextPlayer();
                    break;
                }
                default: 
                    break;
            }
        }
        else
        {   
            isPlusActivate=false;
            if (isTakiActivate==false)
            {
                continueToNextPlayer();
            }
        }    
    }
    public Integer getNumberOfCardOfCurrentPlayer() 
    {
        return (listOfPlayers.get(playerTurn%numOfPlayers).getNumberOfCards());
    }
    public Card getPlayerCardByIndex(Integer playerChoice ) 
    {
       return (listOfPlayers.get(playerTurn % numOfPlayers).getCardFromMyListByIndex(playerChoice));
    }
    public Card topCardOnHeap() 
    {
        return (myCard.getTopCardFromHeap());
    }
    public void setNumberOfPlayers(int NumbersOfPlayers) 
    {
        this.numOfPlayers = NumbersOfPlayers ;
    }
    private Player thereIsAWinner() 
    {
        if (listOfPlayers.size() == 1)
        {
            return listOfPlayers.get(1);
        }
        else
        {
            for (Player player : listOfPlayers)
            {
                if (player.getActive() == true && player.getNumberOfCards() == 0)
                {
                    return player;
                }
            } 
        }
        return null;            
    }
    public Player getCurrentPlayer ()
    {
        return listOfPlayers.get(playerTurn%numOfPlayers);
    }
    private void checkThrownCard(Card cardThatWasThrownByTheLastPlayer)
    {
        if (isPlusTwoActivate && cardThatWasThrownByTheLastPlayer.rank!= Card.e_Rank.PLUSTWO)
        {
            checkPlayerTurnAndHandleTheFollowingEvent(NULL);
        }
        else 
        {
            switch (cardThatWasThrownByTheLastPlayer.rank )
            {
                case PLUSTWO:
                {
                    isPlusTwoActivate = true;
                    plusTwoCounter++;
                    checkPlayerTurnAndHandleTheFollowingEvent(PLUSTWO);
                    break;
                }
                case STOP:
                {
                    isStopActivate = true;
                    checkPlayerTurnAndHandleTheFollowingEvent(STOP);
                    isStopActivate = false;
                    break;
                } 
                case SWITCHCOLOR:
                {
                    isSwitchColorActivate = true;
                    checkPlayerTurnAndHandleTheFollowingEvent(SWITCHCOLOR);
                    break;
                } 
                case SWITCHDIRECTION:
                {
                    isSwitchDirectionActivate = !isSwitchDirectionActivate;
                    checkPlayerTurnAndHandleTheFollowingEvent(SWITCHDIRECTION);
                    break;
                } 
                case TAKI:
                {
                    isTakiActivate = true;
                    checkPlayerTurnAndHandleTheFollowingEvent(TAKI);
                    break;
                }  
                case PLUS:
                {
                    isPlusActivate = true;                
                    checkPlayerTurnAndHandleTheFollowingEvent(PLUS);
                    break;
                }
                default:
                {
                    checkPlayerTurnAndHandleTheFollowingEvent(NULL);
                    break;
                } 
            }
        }
    }
    public boolean getTakiActivate() 
    {
       return isTakiActivate;
    }
    public void setTakiActivate(boolean b) 
    {
        isTakiActivate = b;
    }
    public boolean getPlusTwoActivate() 
    {
        return isPlusTwoActivate;
    }
    public void throwPlusTwoCardForCurrentPlayer() 
    {
        try 
        {
            myCard.addCardsToHeapFromPlayer(listOfPlayers.get(playerTurn%numOfPlayers).throwHisPlusTwoCard());
            plusTwoCounter++;
            continueToNextPlayer();
        }
        catch (Exception e)
        {
            throw (e);
        }
    }
    public boolean getStopActivate() 
    {
        return isStopActivate;
    }
    public void setPlusTwoActivate(boolean b) 
    {
        isPlusTwoActivate = b;
    }
    public void setStopActivate(boolean b) 
    {
        isStopActivate = b;
    }
    public boolean getPLUSActivate() 
    {
       return isPlusActivate;
    }
    private void SwitchedDirectionEventHandle() 
    {
        Collections.reverse(listOfPlayers);
    }
    public boolean getSwitchDirectionActivate()
    {
        return isSwitchDirectionActivate;
    }
    public boolean getSwitchColorActivate() 
    {
       return isSwitchColorActivate;
    }
    public void continueToNextPlayer() 
    {     
        if (endGame() == false)
        {
            playerTurn++;
            while (listOfPlayers.get(playerTurn % numOfPlayers).getActive() == false)
            {
               playerTurn++;
            }
        }
    }
    public void addCardFromDeckToPlayer(Integer numOfCards)
    {
        if (listOfPlayers.get(playerTurn%numOfPlayers).addToExisitingPlaerCards(myCard.retriveFromDeck(numOfCards)) == false)
        {
            emptyDeck = true;
        }
        continueToNextPlayer();            
    }    
    public void addCardToNewPlayers()
    {
       for(Player PlayerToAddCardToHisList : listOfPlayers)
        {
            PlayerToAddCardToHisList.addToNewPlaerCards(myCard.divCardsToNewPlayer());
        }       
    }
    public void resigenPlayer(String nameOfResignedPlyer) 
    {
        for(Player player : listOfPlayers)
        {
            if (player.getName().equals(nameOfResignedPlyer))
            {
                player.DeActivePlayer();
            }
        }        
        numOfActivePlayers--;
    }
    public String printLastThrownCard() 
    {
        return (myCard.printLastThrownCard());
    }
    public String getNameOfCurrentPlayer()
    {
       return (listOfPlayers.get(playerTurn%numOfPlayers).getName());
    }
    public void newLifesForPlayers() 
    {
        for( Player player : listOfPlayers )
        {
            player.setActive(true);
            myCard.addCardsToHeapFromPLayerWhoLose(player.returnMyCardAfterMyLost());
        }
    }
    public void newRound() 
    {   
        this.newLifesForPlayers();
        this.newDeckCard();        
        this.addCardToNewPlayers();
        this.isWinner = false;
        this.playerTurn = 0;
        this.winnerName = null;
        this.numOfActivePlayers = this.numOfPlayers;
        this.isTakiActivate = false;
        this.isPlusTwoActivate = false;
        this.plusTwoCounter = 0;
        this.isStopActivate= false;
        this.isSwitchColorActivate= false;
        this.isSwitchDirectionActivate= false;
        this.isPlusActivate= false;
        this.isWinner = false;    
        this.emptyDeck = false;
        this.winnerName = null;
        this.cardThatWasThrownByTheLastPlayer = myCard.getTopCardFromHeap();
        
    }
    public String handleEvents()
    {
        if (isStopActivate == true)
        {
            isStopActivate = false;            
            return ALREADYPLAYED;
        }                
        else
        {
           if (isPlusActivate == true)
           {
               isPlusActivate = false;
               return FALSE; //already handled only need to play again
           }
           else
           { 
                if (isSwitchDirectionActivate == true)                    
                {                        
                      isSwitchDirectionActivate = false;
                      return FALSE;
                }
                else
                {
                    return FALSE;
                }
           }
        }
    }
    public boolean theCurrentPlayerHavePlusTwoCard() 
    {
        return (listOfPlayers.get(playerTurn%numOfPlayers).isHavePlusTwoCard());
    }
    public int getPlusTwoCounter()
    {
        return this.plusTwoCounter;
    }
    public void giveCardsToPlayerAfterPlusTwoLost() 
    {       
       Integer numOfCardsToAddToPlayer = plusTwoCounter *2;                
       addCardFromDeckToPlayer(numOfCardsToAddToPlayer );
       continueToNextPlayer();
       this.plusTwoCounter =0;
       this.isPlusTwoActivate = false;
       
    }    
    public void handleChangedColorEventForHuman(String choosenColor) 
    {        
        this.myCard.getTopCardFromHeap().setColor(choosenColor);
        this.isSwitchColorActivate=false;
    }
    private void handleChangedColorEventForComputer() 
    {
        String choosenColor  = chooseColorForSwitchColorCmputerPlayer();
        cardThatWasThrownByTheLastPlayer.setColor(choosenColor);
        this.isSwitchColorActivate=false;    
    }
    private String chooseColorForSwitchColorCmputerPlayer() 
    {
        boolean again = false;        
        Random rand = new Random();
        int randAction = rand.nextInt() % 4;       
        while (!again)
        {
            switch (randAction)
            {
                case 1:
                {
                    return YELLOW;
                }
                case 2:
                {
                    return BLUE;
                }
                case 3:
                {
                    return GREEN;
                }
                case 4:
                {
                    return RED;
                }
                default :
                {
                    again = false;
                }
            }
            randAction = rand.nextInt() % 4;
        }
        return null;
    }    
    public void setPlayersFromXml(List<Player> ListOfPlayer)
    {
        listOfPlayers.clear();
        listOfPlayers.addAll(ListOfPlayer);
    }
    public void setDeckCard(Queue<Card> listOfCard) 
    {
        this.myCard.createNewDeckFromXml(listOfCard);    
    }
    public void setHeapCard(Stack<Card> listOfCard)
    {
         this.myCard.createNewHeapFromXml(listOfCard); 
    }
    public void setNumberOfActivePlayers(Integer numberOfActivePlayers) 
    {
        this.numOfActivePlayers = numberOfActivePlayers;
    }
    public void addNewPlayer(Player invertPlayer) 
    {
        this.listOfPlayers.add(invertPlayer);
    }
    public List<Player> getListOfPlayers() 
    {
        return this.listOfPlayers;
    }
    public void setPlayerTurn(Integer playerTurn)
    {
        this.playerTurn = playerTurn;
    }
    public void setPlustwoCounter(int i)
    {
        this.plusTwoCounter=i;
    }
    public void setIsSwitchColorActivate(boolean b)
    {
        this.isSwitchColorActivate=b;
    }
    public void setIsSwitchDirectionActivate(boolean b)
    {
        this.isSwitchDirectionActivate=b;
    }
    public boolean getIsSwitchDirectionActivate()
    {
        return this.isSwitchDirectionActivate;
    }
    public void setIsPlusActivate(boolean b) 
    {
        this.isPlusActivate=b;
     }
    public void setIsWinner(boolean b)
    {
        this.isWinner=b;
    }
    public Integer getNumberOfActivePlayers() 
    {
        return this.numOfActivePlayers;
    }
    public int getAmountOfPlayers()
    {
        return this.numOfPlayers;
    }
    public void newCards() 
    {       
       this.myCard.newCards();
       this.myCard.newDeckCard();
       divCards();
    }
    private void divCards()
    {
        for(Player player : listOfPlayers)
        {
            player.setHand(myCard.divCardsToNewPlayer());
        }
    }
    public void setEngine(EngineGame engine)
    {
        this.numOfActivePlayers = engine.numOfActivePlayers;
        this.numOfPlayers = engine.numOfPlayers;
        this.playerTurn = engine.playerTurn;
        this.isTakiActivate = false;
        this.isPlusTwoActivate = false;
        this.plusTwoCounter = 0;
        this.isStopActivate= false;
        this.isSwitchColorActivate= false;
        this.isSwitchDirectionActivate= false;
        this.isPlusActivate= false;
        this.isWinner = false;
        this.emptyDeck = false;        
        this.winnerName = null;
        this.cardThatWasThrownByTheLastPlayer = null;
        
        this.listOfPlayers =  new LinkedList<>();
        for (Player player : engine.listOfPlayers)
        {
            if (player.isIHuman())
            {
                listOfPlayers.add(new HumanPlayer(player.getName(),player.getHand()));
            }
            else
            {
                listOfPlayers.add(new ComputerPlayer(player.getName(),player.getHand())); 
            }
        }        
        this.myCard = new Cards();
        myCard.setDeck(engine.myCard.getDeck());
        myCard.setHeap(engine.myCard.getHeap());
        this.cardThatWasThrownByTheLastPlayer = myCard.getTopCardFromHeap();
    }
    public Stack<Card> getHeap() 
    {
        return myCard.getHeap();
    }
    public Queue<Card> getDeck() 
    {
        return myCard.getDeck();
    }
    public void setCurrentPlayerTurn(int i) 
    {
        playerTurn =i;
    }
    public Card.e_Color getTopHeapColor() 
    {
       return myCard.getTopCardFromHeap().getColor();
    }
    private Boolean TakeCardFromDeckOrResignOrWinner() 
    {
        Player WonPlayer = thereIsAWinner();             
        if ( WonPlayer != null)
        {
              isWinner=true;
              winnerName = WonPlayer.getName();
        }
        return isWinner;     
    }

}
