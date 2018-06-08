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
    
    private HStaticText tekstLabel;
    
    private HTextButton knop1, knop2;
   
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
        tekstLabel = new HStaticText("TEKST");
        
        // eigenschappen van tekstLabel instellen
        tekstLabel.setLocation(100,100);
        tekstLabel.setSize(400,250);
        tekstLabel.setBackground(new DVBColor(255,255,255,179));
        tekstLabel.setBackgroundMode(HVisible.BACKGROUND_FILL);
        
        // tekstLabel aan de Scene toevoegen
        scene.add(tekstLabel);
        
        // eigenschappen van knoppen instellen
        knop1 = new HTextButton("KNOP1");
        knop1.setLocation(100,400);
        knop1.setSize(100,50);
        knop1.setBackground(new DVBColor(0,0,0,179));
        knop1.setBackgroundMode(HVisible.BACKGROUND_FILL);
        
        knop2 = new HTextButton("KNOP2");
        knop2.setLocation(300,400);
        knop2.setSize(100,50);
        knop2.setBackground(new DVBColor(0,0,0,179));
        knop2.setBackgroundMode(HVisible.BACKGROUND_FILL);
        
        // pijltjes beweging toevoegen
        knop1.setFocusTraversal(null, null, null, knop2); // op, neer, links, rechts
        knop2.setFocusTraversal(null, null, knop1, null);
        
        // knoppen aan de Scene toevoegen
        scene.add(knop1);
        scene.add(knop2);
        
        // Startpunt selectie
        knop1.requestFocus();
        
        // acties koppelen aan knoppen
        knop1.setActionCommand("knop1_actioned");
        knop2.setActionCommand("knop2_actioned");
        knop1.addHActionListener(this);
        knop2.addHActionListener(this);
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

    public void actionPerformed (ActionEvent e){
        System.out.println(e.getActionCommand());
    }
}