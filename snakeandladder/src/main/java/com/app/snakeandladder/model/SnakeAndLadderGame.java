package com.app.snakeandladder.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;

import com.app.snakeandladder.exception.UserThresholdBreachedException;
import com.app.snakeandladder.service.DiceService;

public class SnakeAndLadderGame {
	
	private Queue<User> users = null;
	private Board board = null;
	private Integer lowerBoundThresholdUserCount = null;
	private Integer numberOfDice = 0;
	private List<Dice> dice = null;
	private List<User> winResult = null;
	
	public SnakeAndLadderGame(int maxUsers,
			int boardSize,
			int diceCount,
			int userLowerBoundCounts,
			int numOfSnakes, int numOfLadders,
			int diceFaceCount)
	{
		this.users = new ArrayDeque<>(maxUsers);
		this.winResult = new ArrayList<>(maxUsers-1);
		this.lowerBoundThresholdUserCount = userLowerBoundCounts;
		this.numberOfDice = diceCount;
		initBoard(boardSize, numOfSnakes, numOfLadders);
		initDice(diceCount, diceFaceCount);
	}

	private void initDice(int diceCount, int diceFaceCount) {
		this.dice = new ArrayList<Dice>(diceCount);
		for(int i = 0; i<diceCount; i++) {
			this.dice.add(Dice.createInstance(diceFaceCount));
		}
	}
	
	private void initBoard(int boardSize, int numOfSnakes, int numOfLadders) {
		
		int barrierMin = 1; int barrierMax = boardSize-1;
		Set<String> snlId = new HashSet<>();
		Map<Integer, Snake> snakeMap = new HashMap<>();
		for(int i = 0; i<numOfSnakes; i++) {
			while(true) {
				
				int start = RandomUtils.nextInt(barrierMin, barrierMax);
				int end = RandomUtils.nextInt(barrierMin, barrierMax);
				
				if(end >= start)
					continue;
				
				String barrierID = String.format("%d_%d", start, end);
				if(!snlId.contains(barrierID)) {
					snlId.add(barrierID);
					Snake snake = new Snake(start, end);
					snakeMap.put(start, snake);
					break;
				}
				
			}
		}
		
		Map<Integer, Ladder> ladderMap = new HashMap<>();
		for(int i = 0; i<numOfLadders; i++) {
			while(true) {
				
				int start = RandomUtils.nextInt(barrierMin, barrierMax);
				int end = RandomUtils.nextInt(barrierMin, barrierMax);
				
				if(start >= end) {
					continue;
				}
				
				String barrierID = String.format("%d_%d", start, end);
				if(!snlId.contains(barrierID)) {
					snlId.add(barrierID);
					Ladder ladder = new Ladder(start, end);
					ladderMap.put(start, ladder);
					break;
				}
			}
		}
		
		this.board = new Board(boardSize, snakeMap, ladderMap);
	}

	public void addUsers(User user) throws UserThresholdBreachedException {
		try {
			this.users.add(user);
		} catch (IllegalStateException e) {
			throw new UserThresholdBreachedException("Max User Size reached", e);
		}
	}
	
	public void gameStart() {
		int i = 0;
		while(users.size() > lowerBoundThresholdUserCount) {
			i++;
			User user = users.poll();
			System.out.print("User "+user.getName()+"'s turn. CurrPos - "+user.getCurrentPos());
			
			int currPos = user.getCurrentPos();
			int diceRollResult = DiceService.getInstance().rollDice(this.dice);
			int newPos = currPos+diceRollResult;
			if(newPos > board.getEnd()) {
				users.offer(user);
			} else if(newPos == board.getEnd()) {
				this.winResult.add(user);
				System.out.println("User "+user.getName()+" won. Position - "+this.winResult.size());
			} else {
				
				if(board.getSnakes().containsKey(newPos)) {
					Snake snake = board.getSnakes().get(newPos);
					System.out.print(" | Snake bit User "+snake);
					newPos = snake.getTail();
				} else if(board.getLadderes().containsKey(newPos)) {
					Ladder ladder = board.getLadderes().get(newPos);
					System.out.print(" | Ladder in Curr Pos for User "+ladder);
					newPos = ladder.getEnd();
				}
				user.updateCurrPos(newPos);
				users.offer(user);
			}
			System.out.println("| User is now at "+user.getCurrentPos());
			System.out.println("***************************  TURN Count - "+i+"  *****************************");
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Final Result = "+this.winResult);
		User last = users.poll();
		System.out.println("Lost User = "+last.getId()+" curr pos - "+last.getCurrentPos());
	}
}
