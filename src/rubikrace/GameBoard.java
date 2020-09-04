package rubikrace;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Gus McCoy
 */

public class GameBoard extends JPanel implements ITileListener
{
    private final int ROWS = 5;
    private final int COLUMNS = 5;
    private final int SOLUTIONROWS = 3;
    private final int SOLUTIONCOLUMNS = 3;
    static long startTime = 0;
    private long endTime = 0;
    private long timePlayed = 0;
    private int colorIndex;
    private int[] colorCounter = {0, 0, 0, 0, 0, 0};
    private int[] SolutionColorCounter = {0, 0, 0, 0, 0, 0};
    private final Color[] colorOptions = {Color.green, Color.blue, Color.red, Color.white, Color.yellow, Color.orange};
    private ArrayList<Long> highscores = new ArrayList();    
    private Tile[][] cells = new Tile[ROWS][COLUMNS];
    private Tile[][] solution = new Tile[SOLUTIONROWS][SOLUTIONCOLUMNS];
    private boolean gameOver = false;
    JMenuBar MenuBar = new JMenuBar();
    
    public GameBoard() 
    {          

        setLayout(new GridLayout(ROWS,COLUMNS));
        InitializeGameBoard();        
        InitializeSolutionGameBoard();
        Solution GameSolutionWindow = new Solution(solution);
    }
    
    public void InitializeGameBoard()
    {
        for(int row = 0; row < ROWS; row++)
        {
            for(int column = 0; column < COLUMNS;
                column++)
            {
                cells[row][column] = new Tile("", row, column);
            }
        }
        
        Color TileColor = Color.black;
        
        for(int row = 0; row < ROWS; row++)
        {
            for(int column = 0; column < COLUMNS;
                column++)
            {
                if(row == 0 && column == 0)
                {
                    
                }
                else
                {
                  colorIndex = (int) (Math.random() * 6 + 0);
                
                  while(colorCounter[colorIndex] >= 4)
                  {
                    colorIndex = (int) (Math.random() * 6 + 0);
                  }
                  
                  colorCounter[colorIndex]++;
                  TileColor = colorOptions[colorIndex];
                  
                }
                
                cells[row][column].setBackground(TileColor);
                cells[row][column].addCardClickedListener(this, row, column);
                add(cells[row][column]);
             }
        }
        startTime = getTime();
//            swapEmptySlot(cells);
    }
    
    public void InitializeSolutionGameBoard()
    {
        for(int row = 0; row < SOLUTIONROWS; row++)
        {
            for(int column = 0; column < SOLUTIONCOLUMNS;
                column++)
            {
                solution[row][column] = new Tile("", row, column);
            }
        }
        
        Color TileColor = Color.black;
        
        for(int row = 0; row < SOLUTIONROWS; row++)
        {
            for(int column = 0; column < SOLUTIONCOLUMNS;
                column++)
            {
                colorIndex = (int) (Math.random() * 6 + 0);

                while(SolutionColorCounter[colorIndex] >= 2)
                {
                  colorIndex = (int) (Math.random() * 6 + 0);
                }

                SolutionColorCounter[colorIndex]++;
                TileColor = colorOptions[colorIndex];

                solution[row][column].setBackground(TileColor);
            }
        }
    }

    @Override
    public void tileClicked(Tile tile) 
    {
        boolean moveMade = false;
        for(int i = 0; i <= 4; i++)
        {
            if(cells[tile.tileRow][i].getBackground() == Color.black)
            {
                shift(false, i, tile.tileColumn, tile.tileRow);
                moveMade = true;
            }
        }
        if(!moveMade)
        {
            for(int i = 0; i <= 4; i++)
            {
                if(cells[i][tile.tileColumn].getBackground() == Color.black)
                {
                    shift(true, i, tile.tileRow, tile.tileColumn);
                    moveMade = true;
                }
            }
        } 
        
        if(moveMade)
        { 
            if(checkForError())
            {
                for (int i = 0; i < 3; i++)
                {
                    rotateSolution();
                    
                    if(!checkForError())
                    {
                        i = 3;
                        endGame();
                        gameOver = true;
                    }
                }
                if(!gameOver)
                {
                    rotateSolution();
                }
            }
            else
            {
                if(!gameOver)
                {
                    endGame();
                    gameOver = true;
                }
                 
            }
        }
    }
    
    private void shift(boolean cycleRow, int blackTileIndex, int clickedTileIndex, int constDim)
    {   
        if (!cycleRow)
        {
            if(clickedTileIndex > blackTileIndex)
            {
                for(int i = blackTileIndex; i < clickedTileIndex; i++)
                {
                    Color tempColor = cells[constDim][i].getBackground();
                    cells[constDim][i].setBackColor(cells[constDim][i + 1].getBackground());
                    cells[constDim][i + 1].setBackColor(tempColor);
                }
                cells[constDim][clickedTileIndex].setBackColor(Color.black);
            }
            else
            {
                for(int i = blackTileIndex; i > clickedTileIndex; i--)
                {
                    Color tempColor = cells[constDim][i].getBackground();
                    cells[constDim][i].setBackColor(cells[constDim][i - 1].getBackground());
                    cells[constDim][i - 1].setBackColor(tempColor);
                }
                cells[constDim][clickedTileIndex].setBackColor(Color.black);
            }
        }
        else
        {
            if(clickedTileIndex > blackTileIndex)
            {
                for(int i = blackTileIndex; i < clickedTileIndex; i++)
                {
                    Color tempColor = cells[i][constDim].getBackground();
                    cells[i][constDim].setBackColor(cells[i+1][constDim].getBackground());
                    cells[i+1][constDim].setBackColor(tempColor);
                }
                cells[clickedTileIndex][constDim].setBackColor(Color.black);
            }
            else
            {
                for(int i = blackTileIndex; i > clickedTileIndex; i--)
                {
                    Color tempColor = cells[i][constDim].getBackground();
                    cells[i][constDim].setBackColor(cells[i-1][constDim].getBackground());
                    cells[i-1][constDim].setBackColor(tempColor);
                }
                cells[clickedTileIndex][constDim].setBackColor(Color.black);     
            }
        }
    }

    private boolean checkForError() 
    {
        boolean faultFound = false;
        
        for(int row = 1; row <= 3; row++)
        {
            for(int column = 1; column <= 3; column++)
            {
                if(cells[row][column].getBackground() !=
                        solution[row-1][column-1].getBackground())
                {
                    faultFound = true;
                }
            }
        }
        return faultFound;
    }
    
    private void endGame()
    {
        endTime = getTime();
        timePlayed = endTime - startTime;
        highscores.add(timePlayed);
        Collections.sort(highscores);
        
        JOptionPane.showMessageDialog(null,
        "Memory Game Completed in " + timePlayed + " seconds",
          "Highscores",JOptionPane.PLAIN_MESSAGE);
        
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fout = new FileOutputStream("highscores.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(highscores);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public long getTime()
    {
        long result;
        result = System.currentTimeMillis()/1000;
        
        return result;
    }
    
    public void showHighScores()
    {
        File f = new File("highscores.ser");
        if(f.exists()) { 

            //https://www.tutorialspoint.com/java/java_serialization.htm

            try
            {
                try (FileInputStream fileIn = new FileInputStream("highscores.ser"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    highscores = (ArrayList<Long>) in.readObject();
                }
            } 
            catch (IOException e) 
            {
             return;
            } 
            catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(GameBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        String scoreOutput = "";

        for(int c = 0; c < highscores.size(); c++)
        {
           scoreOutput = scoreOutput.concat((c+1) + ". " + highscores.get(c) + "\n");
        }

        JOptionPane.showMessageDialog(null,
            scoreOutput, "Highscores",JOptionPane.PLAIN_MESSAGE);
    }

    private void rotateSolution() 
    {
       Color tempColor = solution[0][2].getBackground();
       solution[0][2].setBackColor(solution[0][0].getBackground());
       
       Color tempColor2 = solution[2][2].getBackground();
       solution[2][2].setBackColor(tempColor);
       
       tempColor = solution[2][0].getBackground();
       solution[2][0].setBackColor(tempColor2);
       
       solution[0][0].setBackColor(tempColor);
       
       tempColor = solution[1][2].getBackground();
       solution[1][2].setBackColor(solution[0][1].getBackground());
       
       tempColor2 = solution[2][1].getBackground();
       solution[2][1].setBackColor(tempColor);
       
       tempColor = solution[1][0].getBackground();
       solution[1][0].setBackColor(tempColor2);
       
       solution[0][1].setBackColor(tempColor);
       
    }
}
