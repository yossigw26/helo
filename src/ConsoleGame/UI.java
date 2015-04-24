
package ConsoleGame;

import xmlStuff.XMLStuff;
import CardsEngine.Card;
import gameEngine.EngineGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.xml.bind.JAXBException;
import xmlStuff.InvertFromEngine;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class UI 
{
    public static final Integer MINNUMOFROUNDS =1;
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
    private gameEngine.EngineGame myEngine = new EngineGame();
    private Integer numberOfPlayers;
    private Integer numberOfComputerPlayers;
    private Integer numberOfHumanPlayers;
    private Integer numberOfRounds =0;    
    private final Scanner scanner = new Scanner(System.in);
    private boolean isThePlayerAlreadyPlay = false;
    boolean isXml = false;
    private XMLStuff xmlStuff;
    private String pathToSave;
    
    
    public static void main(String[] args) throws JAXBException, Exception 
    {
        UI ui = new UI();        
        if (ui.createGameSettings())
        {
            ui.startGame();
        }
    }
    private boolean createGameSettings() 
    {
        boolean isValidSetting;        
        System.out.println("Welcome to TAKI game!");
        if (xmlOrRegularInput())
        {     
            isXml = true;
            return xmlRead();            
        }
        else
        {
            isXml = false;
            enterParticipants();
            isValidSetting = true;
        }        
        return isValidSetting;
    }
    private void enterParticipants() 
    {  
        getNumberOfParticipants();        
        myEngine.setNumberOfPlayers(numberOfPlayers);
        myEngine.setNumberOfActivePlayers(numberOfPlayers);
        arrangeComputerParticipants();
        getHumanPlayersNames();
        myEngine.setCurrentPlayerTurn(0);        
        myEngine.newCards();   
    }
    private void startGame() throws JAXBException, Exception 
    {
        numberOfRounds = 1;
        boolean EndOfAllRounds = false;
        while (EndOfAllRounds == false)
        {
            play();
            System.out.println("Another Round? preess 'Y' or 'N' only");
            String YN = scanner.nextLine();
            while (!YN.contains("n") && !YN.contains("y"))
            {
               System.out.println("Wrong Answer !!! Another Round? preess 'Y' or 'N' only");
               YN = scanner.nextLine();
            }
            if (YN.contains("n"))
            {
                    EndOfAllRounds = true;
            }
        }
    }
    private void play()throws JAXBException, Exception 
    {  
      if (numberOfRounds > MINNUMOFROUNDS)
      {
            myEngine.newRound();
      }
        while (!myEngine.endGame())
        {   
            printDetailsForThisRound();
            
            if (myEngine.isPlayerHuman() == true)
            {
                String HumanPlayerChoice ;
                HumanPlayerChoice = humanPleaseSelectCard();  
                if ((!HumanPlayerChoice.equals(ALREADYPLAYED)) && (isThePlayerAlreadyPlay == false))
                { 
                    myEngine.playHisTurn(HumanPlayerChoice);  
                }
                isThePlayerAlreadyPlay = false;
                handleEventForHumanPlayer();
            }
            else
            {
                myEngine.playHisTurn(null);
            }
        }        
        declaredTheWinner();        
        numberOfRounds++;        
    }
    private void declaredTheWinner() 
    {
        System.out.println("THE WINNER IS:");
        System.out.println(myEngine.winnerName);
    }
    private String humanPleaseSelectCard() throws JAXBException, Exception 
    {
        if (myEngine.getPlusTwoActivate() == true)
        {            
            if (handlePlusTwoEventByThePlayer() == true)
            {
                myEngine.throwPlusTwoCardForCurrentPlayer();
                return ALREADYPLAYED;
            }
            else
            {
                myEngine.giveCardsToPlayerAfterPlusTwoLost();
                return ALREADYPLAYED;
            }
        }
        else
        {
            if (myEngine.handleEvents().equalsIgnoreCase(ALREADYPLAYED))
            {
                return ALREADYPLAYED;
            }
            else
            {
                return (humanPleaseSelectRegularCard());
            }
        }
    }
    private boolean checkHumanChoiceForNextMove(String tempScanner, Integer numberOfCardsOfThePlayer)throws JAXBException, Exception 
    {   
        Integer playerChoice ;        
        if (isNumeric(tempScanner))
        {
            playerChoice = Integer.parseInt(tempScanner);
        }
        else
        {
            return false;
        }        
        if (((playerChoice < 0)||(playerChoice > numberOfCardsOfThePlayer + 4)) == true)
        {
                return false;
        }        
        else
        {
            if (playerChoice == numberOfCardsOfThePlayer + 1)
            {
                return handleTakeCardFromDeck();                
            }
            else
            {
                if (playerChoice == numberOfCardsOfThePlayer + 2)
                {       
                    return handleResignPlayer(); 
                }
                else
                {          
                    if (playerChoice == numberOfCardsOfThePlayer + 3)
                    {                  
                        saveToFile();
                        isThePlayerAlreadyPlay = false;
                    }
                    else
                    {
                        return handleRegularChoice(playerChoice);
                    }
                }
            }            
        }
        return true;      
    }
    private Boolean handlePlusTwoEventByThePlayer()
    {
        String playerAnswer;        
        if (myEngine.theCurrentPlayerHavePlusTwoCard())
        {
              System.out.println("Do You Want To Throw Him Now? Please Enter Y or N");
              playerAnswer = scanner.nextLine();                
              while (!playerAnswer.contains("n") && !playerAnswer.contains("y"))
              {
                  System.out.println("Wrong Answer , Do You Want To Throw Him Now?");
                  playerAnswer = scanner.nextLine();
              }                  
              if (playerAnswer.contains("y"))
              {
                      return true;
              }
              else 
              {
                   return false;
              }
        }
        return false;
    }
    private String humanPleaseSelectRegularCard() throws JAXBException, Exception 
    {
        String scannerAnswer ;
        System.out.println("Please enter the Card you Want to Play With : Enter His Row num ");
        scannerAnswer = scanner.nextLine();        
        while (!checkHumanChoiceForNextMove(scannerAnswer,myEngine.getNumberOfCardOfCurrentPlayer()))
        {
             System.out.println("Wrong Answer , Please Enter Your Choice Again ");
             System.out.println("Please enter the Card you Want to Play With : Enter His Row num ");
             scannerAnswer = scanner.nextLine();
        }
        return scannerAnswer;
    }
    private void printDetailsForThisRound() 
    {
        String HandPrint;        
            System.out.print(myEngine.getNameOfCurrentPlayer());
            System.out.println(" Is Your Turn");
            
            System.out.print("The Last Card That Was Thrown :");
            System.out.println(myEngine.printLastThrownCard());
            HandPrint = myEngine.printHand();
            System.out.println(HandPrint);
    }
    private void takiStateHandle()
    {
        String scannerAnswer;                  
        System.out.print("Wuold you like to thrown another card? Y/N");
        scannerAnswer = scanner.nextLine();
        while (!scannerAnswer.equalsIgnoreCase("N") && !scannerAnswer.equalsIgnoreCase("Y"))
        {
            System.out.println("Wrong Answer , Wuold you like to thorwn another card? Y/N");
            scannerAnswer = scanner.nextLine();
        }
        if (scannerAnswer.contains("N")||scannerAnswer.contains("n"))
        {
            myEngine.setTakiActivate(false);
            myEngine.continueToNextPlayer();
        }
    }
    private void switchColorStateHandle()
    {
        String choosenColor;                  
        System.out.print("Which color would you to choose?");
        System.out.println("GREEN,BLUE,YELLOW,RED");
        choosenColor = scanner.nextLine();
        //Check impossible entery
        while (!choosenColor.contains(GREEN) && !choosenColor.contains(BLUE)&& !choosenColor.contains(YELLOW)&& !choosenColor.contains(RED))
        {
            System.out.println("Wrong Answer , Please choose color again");
            choosenColor = scanner.nextLine();
        }
        myEngine.handleChangedColorEventForHuman(choosenColor);
    }
    private void handleEventForHumanPlayer() 
    {        
         if (myEngine.getTakiActivate() == true)
         {
            takiStateHandle();
         }
         if (myEngine.getSwitchColorActivate()==true)
         {
            switchColorStateHandle();
         }    
    }
    private boolean xmlOrRegularInput() 
    {
        boolean XmlGame;
        System.out.println("Would you like to load a game from XML file?");
        String answer = scanner.nextLine();
        
        while (!(answer.equalsIgnoreCase("n") || (answer.equalsIgnoreCase("y")) ))
        {
            System.out.println("Wrong Answer , Would you like to load a game from XML file?");
            answer = scanner.nextLine();
        }
        
        if (answer.contentEquals("y")) 
        {
            XmlGame = true;
        } 
        else
        {
            XmlGame = false;
        }
        return XmlGame;
    }    
    private String askForXmlPath()
    {
       String UserPath;
       System.out.println("Enter your Xml file's  Path:");
       UserPath = scanner.nextLine();
       return UserPath;
    }
    private void saveToFile() throws JAXBException, Exception 
    {    
       try
       {
           saveOrSaveAs();        
       }
        catch (javax.xml.bind.JAXBException ex) 
        {
            throw ex;
        }
        catch (FileNotFoundException ex) 
        {
        throw ex;
        }
       catch (Exception ex)
       {
           throw ex;
       }
    }
    private void save() throws JAXBException, Exception 
    {
        try
        {
            InvertFromEngine saveTheGame = new InvertFromEngine(pathToSave,this.myEngine);
        }
        catch (javax.xml.bind.JAXBException ex) 
        {
            throw ex;
        }        
        catch (FileNotFoundException ex) 
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw ex;
        }       
    }
    private void saveAs()throws JAXBException, Exception ,FileNotFoundException
    {
       String path , nameOfTheFIle;
       System.out.println("Please Enter Your Path , without the name, only the path to the location folder:");
       path = scanner.nextLine();
       System.out.println("Please Enter The Name Of The File:");
       nameOfTheFIle = scanner.nextLine();       
        try
        {
            if (path.endsWith("\\"))
            {
                    path = path + nameOfTheFIle + ".xml";
            }
            else
            {
                    path = path + "\\"+ nameOfTheFIle + ".xml";
            }                
            File f = new File(path);
            String AbsolutePath = f.getAbsolutePath();
            pathToSave = AbsolutePath; 
            f.canWrite();            
            InvertFromEngine saveTheGame = new InvertFromEngine(pathToSave,this.myEngine);                        
        }        
        catch (javax.xml.bind.JAXBException ex) 
        {
            throw ex;
        }
        catch (FileNotFoundException ex) 
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
    private boolean isNumeric(String str)  
    {  
        try  
        {  
          Integer d = Integer.parseInt(str);
        }  
        catch(NumberFormatException nfe)  
        {  
            return false;  
        }  
        return true;  
}
    private void getNumberOfParticipants() 
    {
        String tempScanner;
        System.out.println("Please Enter The Number Of Participants You Want:");
        tempScanner = scanner.nextLine();        
        while (!isNumeric(tempScanner))
        {            
            System.out.println("Wrong Answer!! Please Enter The Number Of Participants You Want");  
            tempScanner = scanner.nextLine();            
        }        
        numberOfPlayers = Integer.parseInt(tempScanner);        
        while (!((numberOfPlayers > MINNUMOFPLAYERS-1)  &&  (numberOfPlayers < MAXNUMOFPLAYERS +1)))
        {            
            System.out.print("Wrong Answer!! Please Enter The Number Of Participants You Want:"); 
            System.out.println("The Value Should Be Between 1-4");
            tempScanner = scanner.nextLine(); 
            numberOfPlayers = Integer.parseInt(tempScanner);
        }
    }
    private void arrangeComputerParticipants() 
    {
        String tempScanner;
        System.out.println("Please Enter The Number Of Computer Participants You Want:"); 
        tempScanner = scanner.nextLine();
        
        while (!isNumeric(tempScanner))
        {            
            System.out.println("Wrong Answer!! Please Enter The Number Of Computer Participants You Want");  
            tempScanner = scanner.nextLine();            
        }
        numberOfComputerPlayers = Integer.parseInt(tempScanner);        
        while (!((numberOfComputerPlayers >= 0)  &&  (numberOfComputerPlayers <=MAXNUMOFPLAYERS +1 )))
        {            
            System.out.print("Wrong Answer!! Please Enter The Number Of Computer Participants You Want :"); 
            System.out.println("The Value Should Be Between 1-4");
            tempScanner = scanner.nextLine(); 
            numberOfComputerPlayers = Integer.parseInt(tempScanner);
        }        
        numberOfHumanPlayers = numberOfPlayers - numberOfComputerPlayers;        
        for(int i=0;i<numberOfComputerPlayers;i++)
        {
            String name = "Computer"+i;
            myEngine.createNewPlayer(name,COMPUTERPLAYER); 
        }        
    }
    public void getHumanPlayersNames()
    {
        for(int i=0;i<numberOfHumanPlayers;i++)
        {
            System.out.print("Please Enter The Name Of Participants Number");
            System.out.println(i+1);
            String name = scanner.nextLine();
            myEngine.createNewPlayer(name,HUMANPLAYER);                 
        }    
    }
    private boolean xmlRead() 
    {
       boolean isValidSetting ;
            try 
            {
                String xmlPath=askForXmlPath();
                xmlStuff = new XMLStuff(xmlPath);    
                
                while (!xmlStuff.loadGameSettingFromXML(xmlPath))
                {                    
                    System.out.println("something want wrong");
                    xmlPath = askForXmlPath();
                }
                
                myEngine.setEngine(xmlStuff.GetEngineFromXml());
                isValidSetting = true;            
                
            }
            catch (Exception ex)
            {
              isValidSetting = false;
              throw ex;               
            }
            
            return isValidSetting;
    }
    private void saveOrSaveAs() throws JAXBException, Exception ,FileNotFoundException
    {        
       String saveOrNot ,answerToSaveOrSaveAs;
       Integer parseAnswerToSaveOrSaveAs;       
        System.out.println("Would you like to save the game to file?");
        saveOrNot = scanner.nextLine();
        while (!((saveOrNot.contentEquals("y") || (saveOrNot.contentEquals("n")))))
        {
            System.out.println("Wrong Answer !! Would you like to save the game to file?");
            saveOrNot = scanner.nextLine();
        } 
        if (saveOrNot.contentEquals("y"))
        {
             if (pathToSave != null)
             {
                  System.out.println("Would you like to save or SaveAs? ");
                  System.out.println("1.Save");
                  System.out.println("2.SaveAs");
                  System.out.println("Please Type Only 1 or 2");
                  answerToSaveOrSaveAs = scanner.nextLine();
                  while   (!(answerToSaveOrSaveAs.contentEquals("1") || answerToSaveOrSaveAs.contentEquals("2")))
                  {
                      System.out.println("Wrong Answer !!! Would you like to save or SaveAs? ");
                      System.out.println("1.Save");
                      System.out.println("2.SaveAs");
                      System.out.println("Please Type Only 1 or 2");
                      answerToSaveOrSaveAs = scanner.nextLine();
                  }
                  parseAnswerToSaveOrSaveAs = Integer.parseInt(answerToSaveOrSaveAs);   
                  if (parseAnswerToSaveOrSaveAs == 1)
                  {
                      save();
                  }
                  if (parseAnswerToSaveOrSaveAs == 2)
                  {
                      saveAs();
                  }
             }
             else
             {
                 saveAs();
             }
        }
    }
    private boolean handleTakeCardFromDeck() 
    {
        Integer takeOneCardFromDeck =1;               
        myEngine.addCardFromDeckToPlayer(takeOneCardFromDeck); 
        myEngine.checkPlayerTurnAndHandleTheFollowingEvent(null);
        isThePlayerAlreadyPlay = true;
        return true;
    }
    private boolean handleResignPlayer() 
    {        
       myEngine.resigenPlayer(myEngine.getNameOfCurrentPlayer());
       myEngine.checkPlayerTurnAndHandleTheFollowingEvent(null);
       isThePlayerAlreadyPlay = true;
       return true; 
    }
    private boolean handleRegularChoice(Integer playerChoice) 
    {
       Card chosenCardByThePlayer , topCardOnHeap;
       topCardOnHeap = myEngine.topCardOnHeap();
       chosenCardByThePlayer = myEngine.getPlayerCardByIndex(playerChoice -1 );
       if (!((topCardOnHeap.color == chosenCardByThePlayer.color) || (topCardOnHeap.rank == chosenCardByThePlayer.rank)))                
       {
           if (!((chosenCardByThePlayer.color == Card.e_Color.NO_COLOR) && (chosenCardByThePlayer.rank == Card.e_Rank.SWITCHCOLOR)))
           {
                                return false;
           }                
       }
       return true;
   }
}  


