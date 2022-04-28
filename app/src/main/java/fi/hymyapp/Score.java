package fi.hymyapp;

import android.util.Log;
/**
 * @author Janita Korhonen
 * @version 1
 * This is a class for tracking points as player progresses through the game
 */
public class Score {

    private int currentPoints;

    /**
     * Default constructor for Score class.
     * Set currentPoints to 0.
     */
    public Score(){
        this.currentPoints=0;
    }

    /**
     * Optional constructor for Score class. It is used when
     * amount of points is something else than 0.
     * @param  points  amount of points given to the player when creating new Score class
     */
    public Score(int points){
        this.currentPoints=points;
    }

    /**
     * Increases currentPoints the amount given in parameter.
     * <p>
     * This method is called when player answers correct.
     * @param  points  amount of points to be given to the player
     */
    public void increasePoints(int points){
        this.currentPoints = this.currentPoints+points;
    }

    /**
     * Check how many points player has got,
     * and return a message related to that point amount.
     * <p>
     * This method is called when player has answered all questions and it is time to show results.
     * @return  currentPoints and message related to the amount
     */
    public String checkResult(){
        if(currentPoints<3){
            return "Oi, voi! Sait vain " + currentPoints+" pistettä. Kannattaa jutella vielä aikuisen kanssa näistä asioista.";
        }else if(currentPoints<5){
            return "Tiesit hienosti osaan oikean vastauksen! Sait " + currentPoints+" pistettä. Vielä olisi tosin parantamisen varaa.";
        }else if(currentPoints<8){
            return "Tiesit tosi moneen vastauksen. Sait " + currentPoints+ " pistettä! Hyvin tiedetty!";
        }else{
            return "VAU!! Sait " + currentPoints+" pistettä! Osaat tosi hyvin nää asiat, hyvä sä!";
        }


    }
    /**
     * Convert currentPoints from integer to String
     * <p>
     * This method is called when it is needed to print out currentPoints
     * @return  currentPoints as String
     */
    public String toString(){
        return Integer.toString(currentPoints);
    }
}
