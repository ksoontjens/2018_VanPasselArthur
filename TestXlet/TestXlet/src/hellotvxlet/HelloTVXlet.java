package hellotvxlet;

// Om javax . tv . xlet . Xlet verkort als Xlet te
// schrijven importeren we javax . tv . xlet . ?

import javax.tv.xlet.*;
import org.havi.ui.*;
import org.dvb.ui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.havi.ui.event.*;

public class HelloTVXlet implements Xlet, HActionListener {
    
    // Variabele om de actuele Xlet?context in te bewaren
    private XletContext actueleXletContext ;
    private HScene scene;
    private HSceneTemplate sceneTemplate = new HSceneTemplate();
    
    // Componenten
    private HStaticText tekstLabel;
    private HTextButton buttons[] = new HTextButton[16];
   
    // Variabelen 
    private int cards[] = new int[16];
    private boolean firstTurn = true;
    private int firstCard;
    private int secondCard;
    private boolean chosenCards[] = new boolean[16];
    
    public void setupKnoppen() {
        // for lus om knoppen in te stellen
        
        int kolom = 0;
        for(int i = 0;i<16;i++) {
            buttons[i] = new HTextButton("");                 
            buttons[i].setBackground(Color.GRAY);
            buttons[i].setBackgroundMode(HVisible.BACKGROUND_FILL);
            buttons[i].setSize(100,100); 
            int rij = i % 4;
            buttons[i].setLocation(20 + kolom*110,110 + rij*110);
            if (rij==3) {
                kolom++;
            }
        }

        // pijltjes beweging toevoegen
        buttons[0].setFocusTraversal(null, buttons[1], null, buttons[4]); // op, neer, links, rechts
        buttons[1].setFocusTraversal(buttons[0], buttons[2], null, buttons[5]);
        buttons[2].setFocusTraversal(buttons[1], buttons[3], null, buttons[6]);
        buttons[3].setFocusTraversal(buttons[2], null, null, buttons[7]);
        buttons[4].setFocusTraversal(null, buttons[5], buttons[0], buttons[8]);
        buttons[5].setFocusTraversal(buttons[4], buttons[6], buttons[1], buttons[9]);
        buttons[6].setFocusTraversal(buttons[5], buttons[7], buttons[2], buttons[10]);
        buttons[7].setFocusTraversal(buttons[6], null, buttons[3], buttons[11]);
        buttons[8].setFocusTraversal(null, buttons[9], buttons[4], buttons[12]);
        buttons[9].setFocusTraversal(buttons[8], buttons[10], buttons[5], buttons[13]);
        buttons[10].setFocusTraversal(buttons[9], buttons[11], buttons[6], buttons[14]);
        buttons[11].setFocusTraversal(buttons[10], null, buttons[7], buttons[15]);
        buttons[12].setFocusTraversal(null, buttons[13], buttons[8], null);
        buttons[13].setFocusTraversal(buttons[12], buttons[14], buttons[9], null);
        buttons[14].setFocusTraversal(buttons[13], buttons[15], buttons[10], null);
        buttons[15].setFocusTraversal(buttons[14], null, buttons[11], null);
        
        // knoppen aan de Scene toevoegen
        for (int i = 0;i<16;i++) {
            scene.add(buttons[i]);
        }
        
        // Startpunt selectie
        buttons[0].requestFocus();
        
        // acties koppelen aan knoppen
        for (int i = 0;i<16;i++) {
                buttons[i].setActionCommand(""+i);
                buttons[i].addHActionListener(this);
        }
        
        System.out.println("Knoppen ingesteld");
    }

    public void setupCards() {
        // Maak een randomgenerator aan
        Random rand = new Random();
        //System.out.println("test");
        int cardsOpVolgorde[] = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};
        boolean cardAssigned = false;
        int i = 0;
        for(i=0;i<16;i++) {
            chosenCards[i] = false;
            cardAssigned = false;
            do {
                //System.out.println("TEST");
                int n = rand.nextInt(16);    // van 0 - 15
                if (cardsOpVolgorde[n] != 0) {
                    cards[i] = cardsOpVolgorde[n];
                    cardsOpVolgorde[n] = 0;
                    cardAssigned = true;
                }
            } while (!cardAssigned);
            System.out.println("Kaart "+i+": "+cards[i]);
        }
    }
    
    public void actionPerformed (ActionEvent e){
            System.out.println("knop" + e.getActionCommand() + "_actioned");
            int i = Integer.parseInt(e.getActionCommand());

            giveColor(i);
            checkCard(i);
    }
    
    public void giveColor(int i) {
        if (cards[i] == 1) {
            buttons[i].setBackground(Color.BLUE);
        }
        
        if (cards[i] == 2) {
            buttons[i].setBackground(Color.RED);
        }
        
        if (cards[i] == 3) {
            buttons[i].setBackground(Color.YELLOW);
        }
        
        if (cards[i] == 4) {
            buttons[i].setBackground(Color.ORANGE);
        }
        
        if (cards[i] == 5) {
            buttons[i].setBackground(Color.GREEN);
        }
        
        if (cards[i] == 6) {
            buttons[i].setBackground(Color.WHITE);
        }
        
        if (cards[i] == 7) {
            buttons[i].setBackground(Color.PINK);
        }
        if (cards[i] == 8) {
            buttons[i].setBackground(Color.BLACK);
        }
        chosenCards[i] = true;
    }
    
    public void checkCard(int i) {
        if (firstTurn) {
            buttons[secondCard].setBackground(Color.GRAY);
            buttons[firstCard].setBackground(Color.GRAY);
            firstTurn = false;
            firstCard = i;
        }
        else {
            secondCard = i;
            if (cards[secondCard] == cards[firstCard]) {
                System.out.println("JUIST GERADEN!");
                chosenCards[secondCard] = true;
            }
            else {
                System.out.println("FOUT GERADEN!");
                chosenCards[firstCard] = false;
                chosenCards[secondCard] = false;
            }
            firstTurn = true;
        }
    }
    
    // Initialiseren van de benodigde resources en variabelen :
    public void initXlet ( XletContext context ) {
        this.actueleXletContext = context ;
        
        // Grootte en positie ingeven
        sceneTemplate.setPreference(
                org.havi.ui.HSceneTemplate.SCENE_SCREEN_DIMENSION, 
                new HScreenDimension(1.0f, 1.0f), 
                org.havi.ui.HSceneTemplate.REQUIRED);
        sceneTemplate.setPreference(
                org.havi.ui.HSceneTemplate.SCENE_SCREEN_LOCATION, 
                new HScreenPoint(0.0f, 0.0f), 
                org.havi.ui.HSceneTemplate.REQUIRED);
        
        // Een instantie van een Scene vragen aan de factory
        scene = HSceneFactory.getInstance().getBestScene(sceneTemplate);
        
        // object aanmaken
        tekstLabel = new HStaticText("--- MEMORY ---");
        // eigenschappen van tekstLabel instellen
        tekstLabel.setLocation(20,20);
        tekstLabel.setSize(680,80);
        tekstLabel.setBackground(Color.BLUE);
        tekstLabel.setBackgroundMode(HVisible.BACKGROUND_FILL);
        
        // tekstLabel aan de Scene toevoegen
        scene.add(tekstLabel);
        
        // Knoppen installen
        setupKnoppen();
        setupCards();
    }
    
    // Starten van de Xlet :
    public void startXlet ( ) throws XletStateChangeException {
        // Communicatie ( In? en Uitvoer met de gebruiker )
        System.out.println("Xlet starten");
        
        // Scene zichtbaar maken
        scene.validate();
        scene.setVisible(true);
        
    }
    
    // Methode voor de pause toestand .
    public void pauseXlet ( ) {
        // vrijgeven van niet?nodige resources
    }
    
    // Be�indigen van de Xlet .
    public void destroyXlet ( boolean unconditional ) throws XletStateChangeException {
        if ( unconditional ) {
            // System . out . println geeft debug in weer voor emulatoren .
            System.out.println( "De Xlet moet be�indigd worden" ) ;
        }
        else {
            System.out.println( "De mogelijkheid bestaat "+"door het werpen van een exceptie "+"de Xlet in leven te houden. " ) ;
            throw new XletStateChangeException ( " Laat me leven ! " ) ;
        }
    }
}