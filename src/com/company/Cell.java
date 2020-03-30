package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The class Cell represents a cell of the board. Each cell is made of a button, the cell's
 * coordinate ((x, y)), the status of the cell (is empty, a black stone is on it or a white stone is on it)
 * and manages which player's turn it is at the moment.
 * @author Negar Movaghatian
 * @since 2020-03-30
 */
public class Cell{
    // The button which shows the cell and has features, like its
    // icon changes when clicked on
    private JButton button;
    // The turn of the players [0: first player(white)  1: second player(black)]
    static int turn;
    // The x of the cell
    private int x;
    // The y of the cell
    private int y;
    // The status of the cell [0: empty    1: white    -1: black]
    private int stat;

    /**
     * Create a new cell with the given coordinate and add a button with an empty icon
     * to it.
     * @param x the x of the new cell.
     * @param y the y of the new cell.
     */
    public Cell(int x, int y) {
        turn = 0;
        this.x = x;
        this.y = y;
        stat = 0;

        button = new JButton();
        JPanel pnl = new JPanel();
        button.setPreferredSize(new Dimension(52, 52));
        pnl.setPreferredSize(new Dimension(55, 55));
        Icon empty = new ImageIcon(getClass().getResource("EC.png"));
        button.setIcon(empty);
        pnl.setLayout(new BorderLayout());

        ButtonHandler handler = new ButtonHandler();
        button.addActionListener(handler);
    }

    /**
     * Update the cell and change its status to the given color.
     * @param color The color of the disk on the cell.
     */
    public void updateCell(String color) {
        Icon black = new ImageIcon(getClass().getResource("BC.png"));
        Icon white = new ImageIcon(getClass().getResource("WC.png"));
        if (color.equals("B")) {
            button.setIcon(black);
            stat = -1;
        }
        else {
            button.setIcon(white);
            stat = 1;
        }
    }

    /**
     * Get the button of the cell.
     * @return button field.
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Get the status of the cell.
     * @return stat field.
     */
    public int getStat() {
        return stat;
    }

    /**
     * The class ButtonHandler manages the ActionEvent created by
     * the cell's button.
     */
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (stat==0) {
                updateCell((turn==0)? "W" : "B");
                stat = (turn==0)? 1 : -1;
                turn = 1 - turn;
            }
            else
                JOptionPane.showMessageDialog(null, "Please choose another cell.","Occupied Cell Selected" ,JOptionPane.ERROR_MESSAGE);
        }
    }
}
