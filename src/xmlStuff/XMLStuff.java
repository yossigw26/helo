
package xmlStuff;

import CardsEngine.Card;
import gameEngine.EngineGame;
import PlayerEngine.Player;
import generated.Taki;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class XMLStuff 
{
    String xmlPath;
    private final static String xsdPath = "taki.xsd";
    static gameEngine.EngineGame newEngine = new EngineGame();
    private final Scanner scanner = new Scanner(System.in);
    
    public XMLStuff(String xmlPathRecived)
    {
        this.xmlPath = xmlPathRecived;
    }        
    public boolean loadGameSettingFromXML(String xmlPathRecived) 
    {
        boolean isLoadXMLSuccess = true;       
        try 
        {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(generated.Taki.class.getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            unmarshaller.setSchema(schema);
            generated.Taki TakiFromXML = (generated.Taki) unmarshaller.unmarshal(new File(xmlPathRecived));
            
            List<generated.Player> players = TakiFromXML.getPlayers().getPlayer();
            
            if (checkIfSettingsFromXMLAreGood(players,TakiFromXML))
            {
                loadPlayersSettingFromXMLFile(players);
                loadCardSettingFromXMLFile(TakiFromXML);
                loadCurrentPlayer(TakiFromXML);
                loadCurrentDirection(TakiFromXML);
                loadFlags(TakiFromXML);
            } 
            else 
            {
                isLoadXMLSuccess = false;
                System.out.println("The file's contents is not valid! ");
            }
        } 
        catch (SAXException ex) 
        {
            System.out.println(ex);
            return false;
        } 
        catch (javax.xml.bind.JAXBException ex) 
        {
            System.out.println("The file Path is not valid! ");
            return false;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return false;
        }
        this.xmlPath = xmlPathRecived;
        return isLoadXMLSuccess;
    }
    public void SaveGameSettingToXML(String path, gameEngine.EngineGame engine) throws JAXBException, FileNotFoundException, Exception
    {        
        try 
        {
            InvertFromEngine saveTheGame = new InvertFromEngine(path,engine);
        }
        catch (SAXException ex) 
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
    private void loadPlayersSettingFromXMLFile(List<generated.Player> playersArray) 
    {
        newEngine.setNumberOfPlayers( playersArray.size());   
        newEngine.setNumberOfActivePlayers( playersArray.size());
        
        for(generated.Player player:playersArray)
        {
            newEngine.addNewPlayer(InvertToEngine.invertPlayer(player));
        }
        
    }
    private void loadCardSettingFromXMLFile(Taki TakiFromXML)
    {       
        newEngine.myCard.createNewDeckFromXml(InvertToEngine.invertsDeck(TakiFromXML.getStack()));
        newEngine.myCard.createNewHeapFromXml(InvertToEngine.invertsHeap(TakiFromXML.getPile()));
    }    
    private void loadCurrentPlayer(Taki TakiFromXML) 
    {
        newEngine.setPlayerTurn(InvertToEngine.invertCurrentPlayerTurn(TakiFromXML.getCurrentPlayer(),newEngine.getListOfPlayers()));
    }
    private static void loadCurrentDirection(Taki TakiFromXML) 
    {
       InvertToEngine.invertDirection(TakiFromXML.getDirection());
    }
    private void loadFlags(Taki TakiFromXML) 
    {
        newEngine.setTakiActivate(false);
        newEngine.setPlusTwoActivate(false);
        newEngine.setPlustwoCounter(0);
        newEngine.setStopActivate(false);
        newEngine.setIsSwitchColorActivate(false);
        newEngine.setIsSwitchDirectionActivate(false);
        newEngine.setIsPlusActivate(false);
        newEngine.setIsWinner(false);     
    }
    private boolean checkIfSettingsFromXMLAreGood(List<generated.Player> playersArray, Taki TakiFromXML) 
    {
        boolean isLegalNumOfPlayers = true , isLegalCard;        
        if (!(playersArray.size()< 5) && (playersArray.size()>=2))
        {
            isLegalNumOfPlayers = false;
        }        
        isLegalCard = checkIfTheCardsAreLegal(); 
        return (isLegalCard&&isLegalNumOfPlayers);
    }
    private boolean checkIfTheCardsAreLegal() 
    {
        return true;   
    }
    public int getAmountOfPlayers() 
    {
        return newEngine.getAmountOfPlayers();        
    }
    public List<Player> getPlayers() 
    {
        return newEngine.getListOfPlayers();
    }
    public Queue<Card> getDeckCard() 
    {
        return newEngine.myCard.getDeck();
   }
    public Stack<Card> getHeapCard()
    {
        return newEngine.myCard.getHeap();
    }
    public Integer getNumberOfActivePlayers()
    {
        return newEngine.getNumberOfActivePlayers();
    }    

    public EngineGame GetEngineFromXml() 
    {
        return newEngine;
    }
 
}
