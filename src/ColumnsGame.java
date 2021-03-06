import enigma.console.Console;
import enigma.console.TextAttributes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import Lists.MultiLevelLinkedList;
import Lists.Nodes.ColumnNode;
import Lists.Nodes.NumNode;
import Lists.Nodes.SingleNode;

public class ColumnsGame {
	public static int keypr; // key pressed?
	public static int rkey; // key (for press/release)
	public static KeyListener klis;
	public static enigma.console.Console eng;
	public static MultiLevelLinkedList columns = new MultiLevelLinkedList();
	private static Box box;
	private static HighScoreTable highScoreTable = new HighScoreTable();
	static int x = 6;
	static int num_x = 6;
	static int num_y = 4;
	static boolean columnSelected = false;
	static SingleNode box_control = null;
	static int selected_box_element = 0;
	static boolean num_selected = false;
	static NumNode num_holder = null;
	static ColumnNode col_holder = null;
	static NumNode from_num_node = null;
	static ColumnNode from_col_node = null;
	static int transferCount = 1;
	static int OrderedSetCount = 0;
	static int score = 0;
	static int finishedSets;

	static double End_Game_Score = 0;
	static String playerName = "";
	static boolean isTransfered = false;

	public ColumnsGame(Console eng) throws InterruptedException {
		ColumnsGame.eng = eng;
		eng.getTextWindow().setCursorPosition(17, 10);
		System.out.print("Enter your name: ");
		Scanner scan = new Scanner(System.in);
		playerName = scan.nextLine();
		eng.getTextWindow().setCursorPosition(17, 10);
		System.out.print("                                  ");
		eng.getTextWindow().setCursorPosition(20, 5);
		System.out.println("          Player : " + playerName + "      ");
		box = new Box(eng);
		box.numberGenerator();
		locateFirstThirty();
		columns.display();
		box_control = box.getSLL().getHead();
		col_holder = columns.getHead();

		initialize_game();
	}

	public static void initialize_game() {
		while (true) {
			white();
			eng.getTextWindow().setCursorPosition(30, 4);
			System.out.println("Transfer Count: " + (transferCount - 1));
			eng.getTextWindow().setCursorPosition(30, 6);
			System.out.println("Score: " + score);
			isTransfered = false;

			red();
			eng.getTextWindow().setCursorPosition(x, 2);
			System.out.print("C" + col_holder.getColumnName());
			String input = keyList().toString();
			if (transferCount == 1) {
				End_Game_Score = score + (score / transferCount);
			} else {
				End_Game_Score = score / 10 + (score / (transferCount - 1));
			}
			if (input.equalsIgnoreCase("B") && selected_box_element == 0 && from_num_node == null
					&& box_control != null) {
				selected_box_element = (int) box.representBoxElement();
				box_control = box_control.getLink();
			} else if (input.equalsIgnoreCase("X")) {
				// null column part
				if (col_holder.getDown() == null) {

					if (num_selected) {
						num_selected = false;
						while (from_num_node != null) {
							columns.addNumber(col_holder.getColumnName().toString(), (int) from_num_node.getNumber());
							from_num_node = from_num_node.getNext();
						}
						if (columns.sizeColumns(col_holder.getColumnName().toString()) == 11) {
							score += find_sequential(col_holder);
						}
						if (columns.sizeColumns(from_col_node.getColumnName().toString()) == 11) {
							score += find_sequential(from_col_node);
						}
					} else if (selected_box_element != 0) {
						columns.addNumber(col_holder.getColumnName().toString(), selected_box_element);
						reset_the_game_coordinate();
						col_holder = columns.getHead();
						box.hideBoxElement();
						selected_box_element = 0;
						if (columns.sizeColumns(col_holder.getColumnName().toString()) == 10) {
							score += find_sequential(col_holder);
						}
						if (columns.sizeColumns(from_col_node.getColumnName().toString()) == 10) {
							score += find_sequential(col_holder);
						}
					}
					transferCount++;
					isTransfered = true;
					col_holder = columns.getHead();
					reset_the_game_coordinate();
					white();
					columns.display();
				}
				// column transfer part
				else if (num_selected) {
					int counter = 1;
					NumNode temp = from_num_node;
					while (temp != null) {
						temp = temp.getNext();
						counter++;
					}
					// trasferred
					if ((Math.abs((int) from_num_node.getNumber() - (int) col_holder.getLastNode()) == 1
							|| (int) from_num_node.getNumber() - col_holder.getLastNode() == 0)
							&& !(columns.sizeColumns(col_holder.getColumnName().toString()) + counter > 22)) {

						while (from_num_node != null) {
							columns.addNumber(col_holder.getColumnName().toString(), (int) from_num_node.getNumber());
							from_num_node = from_num_node.getNext();
						}
						if (columns.sizeColumns(col_holder.getColumnName().toString()) == 11) {
							score += find_sequential(col_holder);
						}
						if (columns.sizeColumns(from_col_node.getColumnName().toString()) == 11) {
							score += find_sequential(from_col_node);
						}

						transferCount++;
						isTransfered = true;

					}
					// not transferred
					else {
						while (from_num_node != null) {

							columns.addNumber(from_col_node.getColumnName().toString(),
									(int) from_num_node.getNumber());
							from_num_node = from_num_node.getNext();
						}

					}
					num_selected = false;
					col_holder = columns.getHead();
					reset_the_game_coordinate();
					white();
					columns.display();
				}

				// box transfer
				else if (selected_box_element != 0 && columns.sizeColumns(col_holder.getColumnName().toString()) < 21) {
					// success
					if ((Math.abs((int) selected_box_element - (int) col_holder.getLastNode()) == 1
							|| (int) selected_box_element - col_holder.getLastNode() == 0)) {
						columns.addNumber(col_holder.getColumnName().toString(), selected_box_element);
						reset_the_game_coordinate();
						if (columns.sizeColumns(col_holder.getColumnName().toString()) == 11) {
							score += find_sequential(col_holder);

						}
						col_holder = columns.getHead();
						box.hideBoxElement();
						selected_box_element = 0;
						transferCount++;
						isTransfered = true;

						white();
						columns.display();
					}
				}

			} else if (input.equalsIgnoreCase("Z")) {
				if (!num_selected && columnSelected) {
					num_selected = true;
					from_num_node = num_holder;
					from_col_node = col_holder;
					columns.remove_transfer_element(col_holder, from_num_node);
					clearConsole();
					col_holder = columns.getHead();
					reset_the_game_coordinate();
					white();
					columns.display();

				}
			}

			else if (input.equalsIgnoreCase("E")) {
				if (!columnSelected && col_holder.getDown() != null) {
					num_holder = col_holder.getDown();
					if (num_holder != null) {
						yellow();
						eng.getTextWindow().setCursorPosition(num_x, num_y);
						System.out.print((int) num_holder.getNumber());
					}
					columnSelected = true;
				}

			}

			else if (input.equalsIgnoreCase("D")) {
				if (num_holder != null && num_holder.getNext() != null) {

					white();
					eng.getTextWindow().setCursorPosition(num_x, num_y);
					System.out.print((int) num_holder.getNumber());
					num_holder = num_holder.getNext();
					num_y++;
					yellow();
					eng.getTextWindow().setCursorPosition(num_x, num_y);
					System.out.print((int) num_holder.getNumber());

				}

			}

			else if (input.equalsIgnoreCase("Esc")) {

				reset_the_game_coordinate();
				col_holder = columns.getHead();
				white();
				columns.display();

			}

			else if (input.equalsIgnoreCase("R")) {
				if (!columnSelected && col_holder.getRight() != null) {

					white();
					eng.getTextWindow().setCursorPosition(x, 2);
					System.out.print("C" + col_holder.getColumnName());
					col_holder = col_holder.getRight();
					x += 5;
					num_x += 5;
				}

			} else if (input.equalsIgnoreCase("Exit")) {
				clearEndConsole();
				eng.getTextWindow().setCursorPosition(0, 0);

				white();
				highScoreTable.addPlayerScore(playerName, End_Game_Score);
				highScoreTable.printScores();
				highScoreTable.writeSortedHighScoreTableToFile();

				break;
			}

			reset_keyList();

			if (OrderedSetCount == 5) {
				clearEndConsole();
				highScoreTable.addPlayerScore(playerName, End_Game_Score);
				eng.getTextWindow().setCursorPosition(3, 3);
				highScoreTable.printScores();
				highScoreTable.writeSortedHighScoreTableToFile();
				break;
			}

		}
		eng.getTextWindow().setCursorPosition(35, 24);
		System.out.print("End Game Score: " + End_Game_Score);
	}

	public static void clearConsole() {
		eng.getTextWindow().setCursorPosition(0, 2);
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 29; j++) {
				eng.getTextWindow().setCursorPosition(0 + j, 2 + i);

				System.out.print(" ");
			}
			System.out.println(" ");
		}

	}


	public static void clearEndConsole() {
		eng.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 80; j++) {
				eng.getTextWindow().setCursorPosition(0 + j, 0 + i);

				System.out.print(" ");
			}
			System.out.println(" ");
		}
		

	}

	public static void locateFirstThirty() {

		SingleNode temp = box.getSLL().getHead();
		for (int i = 1; i < 6; i++) {
			columns.addColumn(String.valueOf(i));
			for (int j = 0; j < 6; j++) {

				columns.addNumber(String.valueOf(i), (int) temp.getData());

				temp = temp.getLink();

			}
		}
		removeFirstThirtyFromBox();

	}

	public static void removeFirstThirtyFromBox() {
		for (int i = 0; i < 30; i++) {
			box.getSLL().pop_front();
		}
	}

	public static Object keyList() {

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}
		};
		eng.getTextWindow().addKeyListener(klis);
		int x = 0;
		int y = 0;
		while (true) {
			eng.getTextWindow().setCursorPosition(x, y);
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}

			if (keypr == 1) { // if keyboard button pressed

				if (rkey == KeyEvent.VK_DOWN)
					return "D";
				if (rkey == KeyEvent.VK_RIGHT && x < 5)
					return "R";
				if (rkey == KeyEvent.VK_B)
					return "B";
				if (rkey == KeyEvent.VK_X)
					return "X";
				if (rkey == KeyEvent.VK_Z)
					return "Z";
				if (rkey == KeyEvent.VK_ENTER)
					return "E";
				if (rkey == KeyEvent.VK_ESCAPE)
					return "Esc";
				if (rkey == KeyEvent.VK_E)
					return "Exit";
				else
					return "N";

			}
		}
	}

	public static void reset_keyList() {
		keypr = 0; // last action
		rkey = 0;

	}

	public static void reset_the_game_coordinate() {
		x = 6;
		num_x = 6;
		num_y = 4;
		num_holder = null;
		columnSelected = false;

	}

	public static int find_sequential(ColumnNode current_column) {
		NumNode current_num = current_column.getDown();
		int value = 0;
		int column_Size = columns.sizeColumns(current_column.getColumnName().toString());
		for (int i = 1; i <= column_Size; i++) {

			if (current_num != null) {
				if (current_num.getNumber().toString().equals("1") && column_Size - i >= 9)
					value = one_to_ten(current_column, current_num, i);
				if (current_num.getNumber().toString().equals("10") && column_Size - i >= 9)
					value = ten_to_one(current_column, current_num, i);
				if (value == 1000) {
					columns.remove_transfer_element(current_column, current_num);
					OrderedSetCount++;
					clearConsole();
					break;
				}
				current_num = current_num.getNext();
			}

		}

		return value;
	}

	public static int one_to_ten(ColumnNode current_column, NumNode current_num, int location) {
		int counter = 1;
		int size = columns.sizeColumns(current_column.getColumnName().toString());
		for (int i = location; i <= size; i++) {
			if (current_num.getNext() != null) {

				if (counter + 1 == (int) current_num.getNext().getNumber()) {
					counter++;
					current_num = current_num.getNext();
				}
			}
		}
		if (counter == 10)
			return 1000;
		else
			return 0;

	}

	public static int ten_to_one(ColumnNode current_column, NumNode current_num, int location) {
		int counter = 10;
		int size = columns.sizeColumns(current_column.getColumnName().toString());

		for (int i = location; i <= size; i++) {
			if (current_num.getNext() != null) {

				if (counter - 1 == (int) current_num.getNext().getNumber()) {
					counter--;
					current_num = current_num.getNext();

				}
			}
		}
		if (counter == 1)
			return 1000;
		else
			return 0;
	}

	public static void blue() {
		TextAttributes write = new TextAttributes(Color.blue);
		eng.setTextAttributes(write);
	}

	public static void white() {
		TextAttributes write = new TextAttributes(Color.white);
		eng.setTextAttributes(write);
	}

	public static void green() {
		TextAttributes write = new TextAttributes(Color.green);
		eng.setTextAttributes(write);
	}

	public static void red() {
		TextAttributes write = new TextAttributes(Color.red);
		eng.setTextAttributes(write);
	}

	public static void yellow() {
		TextAttributes write = new TextAttributes(Color.yellow);
		eng.setTextAttributes(write);
	}

	public static void magenta() {
		TextAttributes write = new TextAttributes(Color.magenta);
		eng.setTextAttributes(write);
	}

}