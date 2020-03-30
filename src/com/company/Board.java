package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * The Board class simulates a board for Pentago game. It holds a list of the board
 * cells which itself holds a stone for player 1, player 2 or is empty.
 * The first player plays white and the second player plays black.
 * Also this class is a child class of JFrame.
 * @author Negar Movaghatian
 * @since 2020-03-30
 */
public class Board extends JFrame{

    // The list of the board's cells
    protected Cell[][] cells;

    /**
     * Creat a new board with adding 36 cells to it.
     */
    public Board() {

        cells = new Cell[6][6];

        setLayout(new FlowLayout());

        for (int i=0; i<6; i++) {
            for (int j = 0; j < 6; j++) {
                Cell c = new Cell();
                if (j == 3)
                    add(new JLabel(" "));
                add(c.getButton());
                cells[i][j] = c;
            }
            if (i == 2) {
                JLabel space = new JLabel();
                for (int k=0; k<50; k++)
                    space.setText(space.getText() + "       ");
                space.setFont(new Font(space.getName(), Font.PLAIN, 4));
                add(space);
            }
        }
        add(new JLabel("[Player 1]"));
    }

    /**
     * Swap the given section 90 degrees clockwise or anticlockwise.
     * @param section The section of the board to rotate.
     * @param direction The direction of the rotation. [CW: clockwise, ACW: anticlockwise]
     */
    private void rotate(int section, String direction) {
        System.out.println("Am rotating block " + section + " " + direction);
        int[] dSection = {0, 3, 18, 21}, edge, corner;
        if (direction=="ACW") {
            edge = new int[] {1, 8, 13, 6};
            corner = new int[] {0, 2, 14, 12};
        }
        else {
            edge = new int[] {6, 13, 8, 1};
            corner = new int []{12, 14, 2, 0};
        }
        for (int i=0; i<3; i++) {
            int x = (edge[i] + dSection[section])/6, y = (edge[i] + dSection[section])%6;
            int xp = (edge[(i + 1)] + dSection[section])/6 , yp = (edge[(i + 1)] + dSection[section])%6;
            cells[x][y].swap(cells[xp][yp]);
        }
        for (int i=0; i<3; i++) {
            int x = (corner[i] + dSection[section])/6, y = (corner[i] + dSection[section])%6;
            int xp = (corner[(i + 1)] + dSection[section])/6 , yp = (corner[(i + 1)] + dSection[section])%6;
            cells[x][y].swap(cells[xp][yp]);
        }
    }

    /**
     * Run a multi-player Pentago game
     */
    public void runGame() {
        while (true) {
            while (!Cell.isSelected());
            Integer[] sectionOptions = {1, 2, 3, 4};
            int section = JOptionPane.showOptionDialog(null, "Which section of the board you want to rotate?",
                    "Section to Rotate", 0, 3, null, sectionOptions, sectionOptions[0]);
            String[] directionOptions = {"Clockwise", "Anticlockwise"};
            int direction = JOptionPane.showOptionDialog(null, "In which direction you want to rotate the block?",
                    "CW or ACW", 0, 3, null, directionOptions, directionOptions[0]);
            rotate(section, (direction == 0) ? "CW" : "ACW");
            Cell.roundFinished();
        }
    }
}