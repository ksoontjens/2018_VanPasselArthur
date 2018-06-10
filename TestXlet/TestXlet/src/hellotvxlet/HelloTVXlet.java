package hellotvxlet;

// Om javax . tv . xlet . Xlet verkort als Xlet te
// schrijven importeren we javax . tv . xlet . ?

import javax.tv.xlet.*;
import org.havi.ui.*;
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
    private HTextButton submitButton;
   
    // Variabelen 
    private int cards[] = new int[16];
    private boolean firstTurn = true;
    private boolean check = false;
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

    // Stel de nummers van de kaarten in (later vervangen door kleuren)
    public void setupCards() {
        // Maak een randomgenerator aan
        Random rand = new Random();
        // Maak een lijst met alle mogelijke opties
        int cardsOpVolgorde[] = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};
        boolean cardAssigned = false;
        int i = 0;
        // for lus om kaarten op random plaats te zetten
        for(i=0;i<16;i++) {
            // chosenCards is een variabele die later, tijdens het spel gebruikt wordt.
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
            // Print kaarten uit (kan je mee valsspelen)
            System.out.println("Kaart "+i+": "+cards[i]);
        }
    }
    
    // Functie om te bepalen wat er gebeurt als een knop wordt ingedrukt
    public void actionPerformed (ActionEvent e){
        System.out.println("knop" + e.getActionCommand() + "_actioned");
        
        // Als 2 kaarten gekozen zijn -> controleer of ze hetzelfde zijn.
        if (check) {
            System.out.println("check de kaarten");
            checkCards();
        }   
        else { 
            // e.getActionCommand is een string van i -> zet terug om naar int
            int i = Integer.parseInt(e.getActionCommand());
            if (chosenCards[i] == false) {  // check of kaart al omgedraaid of al gepaard is
                //System.out.println("knop" + e.getActionCommand() + " marcheert");
                giveColor(i);
            }
            else {
                System.out.println("knop" + e.getActionCommand() + " is al geactiveerd");
            }
        }
    }
    
    // 'Draai een kaart om' -> geef een kleur aan de buttons
    public void giveColor(int i) {
        
        if (cards[i] == 1) {
            buttons[i].setBackground(Color.BLUE);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 2) {
            buttons[i].setBackground(Color.RED);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 3) {
            buttons[i].setBackground(Color.YELLOW);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 4) {
            buttons[i].setBackground(Color.ORANGE);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 5) {
            buttons[i].setBackground(Color.GREEN);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 6) {
            buttons[i].setBackground(Color.WHITE);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        
        if (cards[i] == 7) {
            buttons[i].setBackground(Color.PINK);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        if (cards[i] == 8) {
            buttons[i].setBackground(Color.BLACK);
            System.out.println("knop" + i + " is veranderd van kleur");
        }
        // Kaart is nu 'omgedraaid'
        chosenCards[i] = true;
        if (firstTurn) {
            firstTurn = false; 
            firstCard = i;
        }
        else {
            secondCard = i;
            firstTurn = true;
            check = true;   // 2 kaarten zijn nu gekozen -> we kunnen checken
            submitButton.requestFocus();    // submit button voor beter overzicht
        }
    }
    
    // Controleer of de kleuren overeen komen (eigl de nummers)
    public void checkCards() {
        if (cards[secondCard] == cards[firstCard]) {
            System.out.println("JUIST GERADEN!");
        }
        else {
            System.out.println("FOUT GERADEN!");
            chosenCards[firstCard] = false;
            chosenCards[secondCard] = false;
            try {
                //wacht 2 seconden
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // draai de kaarten terug om
            buttons[secondCard].setBackground(Color.GRAY);
            buttons[secondCard].setBackgroundMode(HVisible.BACKGROUND_FILL);
            buttons[firstCard].setBackground(Color.GRAY);
            buttons[firstCard].setBackgroundMode(HVisible.BACKGROUND_FILL);
            buttons[firstCard].requestFocus();  // nodig om de achtergrond ook echt te veranderen (geen idee waarom)
        }
        check = false;
        buttons[secondCard].requestFocus();
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
        
        // Submit button toevegen
        submitButton = new HTextButton("SUBMIT GUESS");
        submitButton.setLocation(460,330);
        submitButton.setSize(210,210);                        
        submitButton.setBackground(Color.GRAY);
        submitButton.setBackgroundMode(HVisible.BACKGROUND_FILL);  
        
        submitButton.setActionCommand("-voor-submitten");
        submitButton.addHActionListener(this);
        
        scene.add(submitButton);
        
        
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
    
    // Beëindigen van de Xlet .
    public void destroyXlet ( boolean unconditional ) throws XletStateChangeException {
        if ( unconditional ) {
            // System . out . println geeft debug in weer voor emulatoren .
            System.out.println( "De Xlet moet beëindigd worden" ) ;
        }
        else {
            System.out.println( "De mogelijkheid bestaat "+"door het werpen van een exceptie "+"de Xlet in leven te houden. " ) ;
            throw new XletStateChangeException ( " Laat me leven ! " ) ;
        }
    }
}