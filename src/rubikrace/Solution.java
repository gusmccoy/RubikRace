package rubikrace;

import javax.swing.JFrame;

/**
 *
 * @author Gus McCoy
 */
public class Solution extends JFrame
{
    
    public Solution(Tile[][] passedSolution)
    {
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        solutionGameBoard gameBoard = new solutionGameBoard(passedSolution); 
        add(gameBoard);
        setVisible(true);
    }
}
