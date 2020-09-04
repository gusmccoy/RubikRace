package rubikrace;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Gus McCoy
 */
public class Tile extends Button
{
    private final int ROWS = 5;
    private final int COLUMNS = 5;
    public final int tileRow;
    public final int tileColumn;
    private boolean isValid;
    public Color tileColor;
    private ArrayList<ITileListener> clickListeners = new ArrayList<ITileListener>();
    
    public Tile(String label, int row, int column)   
    {
        super(label);
        tileRow = row;
        tileColumn = column;
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                for(int row = 0; row < ROWS; row++)
                    {
                        for(int column = 0; column < COLUMNS;
                            column++)
                        {
//                            ITileListener clickListener = clickListeners[row][column];
//                            clickListener.tileClicked((Tile)e.getSource());
                            for(ITileListener clickListener : clickListeners)
                            {
                                clickListener.tileClicked((Tile)e.getSource());
                            }
                        }
                    }
            }
        });
        
    } // End of Constructor

    public void addCardClickedListener(ITileListener listener, int row, int column)
    {
//        clickListeners[row][column] = listener;
        clickListeners.add(listener);
    }
    
    public void setBackColor(Color newBackColor)
    {
        setBackground(newBackColor);
    }
    
    public Color getTileColor()
    {
        return tileColor;
    }
    
    }; // End of File
