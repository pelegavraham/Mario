package com.mario.gfx.frames;

import com.mario.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame{

    public MainFrame() {
        super("Mario");//Create Frame
        setSize(883,590);//Set Frame dimensions to 400 width and 400 height
        setResizable(false);
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        pack();
        try {
            JPanel container = new JPanelWithBackground("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\background.jpg");
            ActionListener startGameListener = e -> {
                setVisible(false);
                //Game.playing=true;
            };
            ActionListener finishListener = e -> System.exit(0);

            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            JMenu game=new JMenu("Game");
            menuBar.add(game);

            JMenuItem start=new JMenuItem("Start Game");
            game.add(start);

            JMenuItem exit=new JMenuItem("Exit");
            game.add(exit);

            start.addActionListener(startGameListener);
            exit.addActionListener(finishListener);

            ImageIcon icon1=new ImageIcon("/playNow.png");
            JButton startGameButton = new JButton("",icon1);//Create Button with text.
            startGameButton.setBackground(Color.BLACK);

            ImageIcon icon2=new ImageIcon("/exit.png");
            JButton exitGameButton  = new JButton("",icon2);
            exitGameButton.setBackground(Color.BLACK);

            exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
            startGameButton.addActionListener(startGameListener);

            container.setBorder(new EmptyBorder(455,0,0,0));
            container.setLayout(new BorderLayout());
            startGameButton.setPreferredSize(new Dimension(310,100));
            exitGameButton.setPreferredSize(new Dimension(100,100));
            container.add(startGameButton,BorderLayout.WEST);//Add Button.
            container.add(exitGameButton ,BorderLayout.EAST);
            add(container);
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.
}
