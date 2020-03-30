package com.company;

import javax.swing.*;

/**
 * The main class runs a Pentago game with creating a new board.
 * @author Negar Movaghatian
 * @since 2020-03-30
 */
public class Main {

    public static void main(String[] args) {

        // Create a new Board to test the program
        Board board = new Board();
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setSize(400, 420);
        board.setResizable(false);
        board.setVisible(true);
        board.repaint();
        board.runGame();
    }
}
