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
    // The status of the cell [0: empty    1: white    -1: black]
    private int stat;
    // The x of the cell
    private int x;
    // The y of the cell
    private int y;
    // The turn of the players [0: first player(white)  1: second player(black)]
    static int turn;
    // Shows if a cell is selected at this moment [At the time a cell is selected changes to true and after that to false]
    static boolean isSelected = false;
    // The x of selected cell
    static int selectedX;
    // The y of selected cell
    static int selectedY;

    /**
     * Create a new cell with the given coordinate and add a button with an empty icon
     * to it.
     */
    public Cell(int x, int y) {
        turn = 0;
        stat = 0;
        this.x = x;
        this.y = y;

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
     * Swap the cell with another cell by swapping their icon and status.
     * @param other
     */
    public void swap(Cell other) {
        // Swap icons
        Icon tempIcon = this.button.getIcon();
        this.button.setIcon(other.button.getIcon());
        other.button.setIcon(tempIcon);
        // Swap stats
        int tmp = this.stat;
        this.stat = other.stat;
        other.stat = tmp;
    }

    /**
     * Get the button of the cell.
     * @return button field.
     */
    public JButton getButton() {
        return button;
    }

    public static boolean isSelected() {
        return isSelected;
    }

    /**
     * Get the status of the cell.
     * @return stat field.
     */
    public int getStat() {
        return stat;
    }

    /**
     * Get the turn of the players at the moment.
     * @return turn field.
     */
    public static int getTurn() {
        return turn;
    }

    /**
     * Relief the selected button and make it ready for the next
     * action.
     */
    static void roundFinished() {
        turn = 1 - turn;
        isSelected = false;
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
                selectedX = x;
                selectedY = y;
                isSelected = true;
            }
            else
                JOptionPane.showMessageDialog(null, "Please choose another cell.","Occupied Cell Selected" ,JOptionPane.ERROR_MESSAGE);
        }
    }
}
