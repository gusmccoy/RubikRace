/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubikrace;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Gus McCoy
 */
public class solutionGameBoard  extends JPanel
{
    public solutionGameBoard(Tile[][] passedSolution)
    {
        setLayout(new GridLayout(3,3));
        
        for(int row = 0; row < 3; row++)
        {
            for(int column = 0; column < 3;
                column++)
            {
                add(passedSolution[row][column]);
            }
        }
    }
}
