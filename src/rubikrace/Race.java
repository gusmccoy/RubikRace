package rubikrace;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Gus McCoy
 */
public class Race extends JFrame
{
    GameBoard gameBoard = new GameBoard();
    JMenuBar MenuBar = new JMenuBar();
    
    public Race()
    {     
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenu Game = new JMenu("Game");
        MenuBar.add(Game);
        
        JMenuItem startOver = new JMenuItem("Start Over");
        Game.add(startOver);
        JMenuItem viewHighScores = new JMenuItem("View High Scores");
        Game.add(viewHighScores);

        startOver.addActionListener((ActionEvent ae) -> {
                gameBoard.startTime = gameBoard.getTime();
                gameBoard.InitializeGameBoard();
                gameBoard.InitializeSolutionGameBoard();      
        });
        
        viewHighScores.addActionListener((ActionEvent ae) -> {
            gameBoard.showHighScores();
        });
        
        setJMenuBar(MenuBar);
        add(gameBoard);
        setVisible(true);
    }
    
    
}
