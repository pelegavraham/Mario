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
        setSize(1080,768);//Set Frame dimensions to 400 width and 400 height
        setResizable(false);
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        setVisible(true);
        pack();
        try {
            JPanel container = new JPanelWithBackground("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\background.jpg");
            ActionListener startGameListener = e -> {
                setVisible(false);
                Game game=new Game();
                JFrame frame= new JFrame("Mario");
                frame.add(game, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                game.start();
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

            JButton startGameButton = new JButton("Play Now");//Create Button with text.
            startGameButton.setFont(new Font("Courier",Font.BOLD,35));
            startGameButton.setForeground(Color.CYAN);
            startGameButton.setBackground(Color.BLUE);

            JButton exitGameButton  = new JButton("Exit");

            exitGameButton.setBackground(Color.BLUE);
            exitGameButton.setFont(new Font("Courier",Font.BOLD,35));
            exitGameButton.setForeground(Color.CYAN);
            exitGameButton.addActionListener(e -> System.exit(0));//Make Exit button Abort program.
            startGameButton.addActionListener(startGameListener);

            container.setBorder(new EmptyBorder(700,0,0,0));
            container.setLayout(new BorderLayout());
            startGameButton.setPreferredSize(new Dimension(880,68));
            exitGameButton.setPreferredSize(new Dimension(200,68));
            container.add(startGameButton,BorderLayout.WEST);//Add Button.
            container.add(exitGameButton ,BorderLayout.EAST);
            add(container);
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.
}
