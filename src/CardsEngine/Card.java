
package CardsEngine;
/**
 * @author Bar Shauly 303016240 and Amit Noyman 201112224
 **/
public class Card
{


	public enum e_Rank {ONE , THREE ,FOUR ,FIVE ,SIX ,SEVEN ,EIGHT ,NINE ,
            PLUS ,PLUSTWO ,STOP ,SWITCHDIRECTION ,SWITCHCOLOR ,TAKI }

	public enum e_Color {YELLOW ,BLUE ,GREEN ,RED ,NO_COLOR}

	public  e_Rank rank;
	public  e_Color color;
        
        
	public Card(e_Rank rank, e_Color color)
        {        		
            this.rank = rank;        
            this.color = color;	
        }
        public Card(){};
        
        @Override
        public String toString()
        {
            String convertCardToString ;
            convertCardToString = rank.toString() + " " + color.toString();
            return (convertCardToString);        
        }
        
        public boolean equals(Card cardtoCheck) 
        {
            if ((color == cardtoCheck.color) && (rank == cardtoCheck.rank))
            {
                return true;
            }
            else
            {
                return false;
            }
        }     
        public void setColor(String choosenColor)
         {
             color = e_Color.valueOf(choosenColor);
         }
        public void setRank(String choosenRank)
        {
             rank = e_Rank.valueOf(choosenRank);
        }
        public void setcard(String choosenRank,String choosenColor)
        {
            rank = e_Rank.valueOf(choosenRank);
            color = e_Color.valueOf(choosenColor);
        }
        
        public e_Color getColor()
        {
            return color;
        }
}

