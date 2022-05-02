package fi.hymyapp;

import static fi.hymyapp.MainActivity.EXTRA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Janita Korhonen
 * @author Daniel Heugenhauser
 */

public class GameActivity extends AppCompatActivity {

    GetFirebase base;

    //init values for path
    public static String dbpath;
    public static String dbTemp;
    private int pathNumber=1;
    private List<String>answerList=new ArrayList<String>();
    private int listIndex=0;


    //set statement text here for ui
    TextView statementView;
    Button aText;
    Button op1button;
    Button op2button;
    Button op3button;

    private static final String TAG ="Firebase" ;

    //score counter for end results
    Score score = new Score();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle b = getIntent().getExtras();
        int i =b.getInt(EXTRA,0);

        aText=(Button) findViewById(R.id.answerButton);
        statementView = (TextView) findViewById(R.id.statementText);
        op1button=(Button) findViewById(R.id.option1Button);
        op2button=(Button)findViewById(R.id.option2Button);
        op3button=(Button)findViewById(R.id.option3Button);


        dbpath = Theme.getInstance().getThemes().get(i).getDatapath()+"/question"+pathNumber;
        //save og dbpath info to be able to access it later again
        dbTemp = Theme.getInstance().getThemes().get(i).getDatapath()+"/question";
        base = new GetFirebase();
        base.setButtons(op1button, op2button, op3button);
        base.setCounters(statementView);
        //Hide answer button.
        aText.setVisibility(View.INVISIBLE);
        //Set answers to questions in the answersList.
        if(Theme.getInstance().getThemes().get(i).getDatapath().equals("involvementQuestions")) {
            answerList.add("Leikki on jokaisella omanlaista. Lapset saavat päättää leikistä, kunhan se ei aiheuta vaaraa tai uhkaa millekään tai kenellekään.");
            answerList.add("Vanhempien tehtävä on varmistaa, että jokaisen lapsen oikeus koulutukseen toteutuu.");
            answerList.add("Pyörätuoli ei estä koripalloa, eikä muutakaan urheilua. Melkein kaikkea urheilua voi harrastaa apuvälineillä ja omalla tavallaan. ");
            answerList.add("Lasten oikeuksissa sanotaan, että lasten mielipide pitää ottaa huomioon, kun päätetään heitä koskevia asioita. Aikuisten tehtävä on ottaa lapsen mielipide huomioon. Isot päätökset tekee aina aikuinen.");
            answerList.add("Sinulla on oikeus olla eri mieltä ja hakea tietoa ja ilmaista itseäsi sinulle parhaiten sopivalla tavalla, joka ei loukkaa muita. Aikuisten tehtävä on kuunnella lapsia myös silloin, kun he ovat eri mieltä.");
            answerList.add("Totta, saat valita, kunhan vaatteet sopivat säähän. Esimerkiksi aikuinen päättää, että laitetaan säähän sopivat vaatteet, mutta lapsi saa päättää millaiset. Aikuisten tehtävä on huolehtia, että lapsilla on tarpeeksi vaatteita ja ruokaa.");
            answerList.add("Lasten oikeuksissa sanotaan, että jokaisella lapsella tulee olla aikuinen, joka välittää ja hoitaa juuri hänestä. Lasten ei tarvitse huolehtia aikuisten asioista. ");
            answerList.add("Koti on tosi tärkeä. Lasten oikeuksissa todetaan, että jokaisella lapsella pitää olla koti, jossa asuu.");
            answerList.add(" Aikuisten tehtävä on hoitaa lapsia hyvin. Hyvä hoitaminen tarkoittaa sitä, että peliajasta päättää aikuinen, jos lapsi pelaa liikaa tai ei halua noudattaa pelien ikärajoja.");
            answerList.add("Aikuisten pitää aina miettiä mikä on lapselle parasta ja tehdä päätöksiä sen mukaan.");
        }else{
            answerList.add("Kaikilla lapsilla on oikeus leikkiin. Ketään ei saa syrjiä eikä kiusata. ");
            answerList.add("Aikuisten tehtävä on kuunnella lapsia. Lapsen tärkeiden asioiden kuuluu olla aikuisillekin tärkeitä.");
            answerList.add("Lapset pitää ottaa mukaan suunnitteluun. Aikuisten on tehtävä päätöksiä kuunnellen lapsia.");
            answerList.add("Se, kun tapaa samassa tilanteessa olevia lapsia ja juttelee heidän kanssaan, on nimeltään vertaistuki. Se auttaa, kun tajuaa, että on muitakin, joilla on se sama.");
            answerList.add("Joskus voi olla hankalaa, jos leikkiin ei mahdu kaikki halukkaat mukaan. Kun leikkiä suunnitellaan, on hyvä koittaa keksiä yhdessä, kuinka se olisi mahdollista. Leikin ulkopuolelle jääminen on kurja tunne ja se, jos joku häiritsee leikkiä.");
            answerList.add("Jokaisen mielipide on yhtä tärkeä. Kun ryhmässä äänestetään esimerkiksi juhlien herkuista, kaikilla on yksi ääni, jolla äänestää. Aikuisten tehtävä on selvittää lapsen mielipide, vaikka lapsi ei osaisi puhua.");
            answerList.add("Leikkiä saa ihan miten haluaa, kunhan ei satuta tai tahallaan riko mitään. Lelut on hyvä siivota, kun leikki loppuu.  Silloin löytää ne helpommin, kun tarvitsee niitä uudelleen.");
            answerList.add("Lapsi saa ehdottaa mitä vaan, ja aikuisen tehtävä on kuunnella ja ottaa ehdotus huomioon. Kaikkia ehdotuksia ei voi kuitenkaan aina toteuttaa.");
            answerList.add("Jos lapset eivät itse saa sovittua, aikuisen tehtävä on auttaa lapsia sopimaan.");
            answerList.add("Kun aikuinen huomaa hyvän ja kehuu, lapset tietää, kuinka ihania ja taitavia he ovat.");
        }
    }

    //onclick functions for option buttons
    public void option1(View view){
        //Sound for button click
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        final MediaPlayer ep = MediaPlayer.create(this,R.raw.sample2);
        //when option is clicked, increase value by 1
        int newValue = base.getOp1Value() + 1;
        //and set database value to match this new value
        base.getOp1Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);

        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op1")){
            //give score after right answer WIP
            score.increasePoints(2);
            mp.start();
        }else{
            ep.start();
        }

        //after increasing database value, show new question
        answerView();
    }
    public void option2(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        final MediaPlayer ep = MediaPlayer.create(this,R.raw.sample2);
        int newValue =base.getOp2Value()+1;
        base.getOp2Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);

        //Get correct answer from getFirebase class.
        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op2")){
            score.increasePoints(2);
            mp.start();
        }else{
            ep.start();
        }
        answerView();
    }
    public void option3(View view){
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.sample);
        final MediaPlayer ep = MediaPlayer.create(this,R.raw.sample2);
        int newValue = base.getOp3Value()+1;
        base.getOp3Counter().setValue(newValue);
        newValue = base.getTotalValue()+1;
        base.getTotalCounter().setValue(newValue);

        //Get correct answer from getFirebase class.
        //Increase points by 2 if correct answer is right
        if(base.getCorrectAnswer().equals("op3")){
            score.increasePoints(2);
            //Correct answer bell sound.
            mp.start();
        }else{
            //Wrong answer bell sound.
            ep.start();
        }
        answerView();
    }
    private void changeQuestion(){
        if(pathNumber!=10)
        {
            //increase pathnumber by one, to access next question in database
            pathNumber+=1;
            //change db references to match correct question number
            dbpath = dbTemp+pathNumber;
            base = new GetFirebase();
            base.setButtons(op1button, op2button, op3button);
            base.setCounters(statementView);

        }else{
            //last question answered, change activity
            Intent lastActivity = new Intent(GameActivity.this,ChartActivity.class);
            lastActivity.putExtra(EXTRA,score.toString());
            startActivity(lastActivity);
        }
    }
    // Close all Ui elements and show results button
    private void answerView(){
        statementView.setVisibility(View.INVISIBLE);
        op1button.setVisibility(View.INVISIBLE);
        op2button.setVisibility(View.INVISIBLE);
        op3button.setVisibility(View.INVISIBLE);
        aText.setVisibility(View.VISIBLE);
        aText.setText(answerList.get(listIndex));
        listIndex+=1;
    }
    //Show all Ui elements and close results button
    public void nextQuestion(View view){
        aText.setVisibility(View.INVISIBLE);
        statementView.setVisibility(View.VISIBLE);
        op1button.setVisibility(View.VISIBLE);
        op2button.setVisibility(View.VISIBLE);
        op3button.setVisibility(View.VISIBLE);
        changeQuestion();
    }
}