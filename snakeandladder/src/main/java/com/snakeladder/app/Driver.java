package com.snakeladder.app;

import java.util.Scanner;

import com.app.snakeandladder.exception.UserThresholdBreachedException;
import com.app.snakeandladder.model.SnakeAndLadderGame;
import com.app.snakeandladder.model.User;

public class Driver {

	public static void main(String[] args) throws UserThresholdBreachedException {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Enter board Size");
        int boardSize = scanner.nextInt();
        System.out.println("Enter number of players");
        int numberOfPlayers = scanner.nextInt();
        System.out.println("Enter number num of dice");
        int diceCount = scanner.nextInt();
        System.out.println("Enter number of snakes");
        int numSnakes = scanner.nextInt();
        System.out.println("Enter number of ladders");
        int numLadders = scanner.nextInt();

        SnakeAndLadderGame game = new SnakeAndLadderGame(numberOfPlayers, boardSize, diceCount, 1, numSnakes, numLadders, 6);
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter player name");
            String pName = scanner.next();
            User player = new User(pName);
            game.addUsers(player);
        }
        game.gameStart();

	}

}
