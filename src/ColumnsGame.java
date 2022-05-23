import enigma.console.Console;
import enigma.console.TextAttributes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

	static int x = 6;
	static int num_x = 6;
	static int num_y = 4;
	static boolean columnSelected = false;

	static int selected_box_element = 0;
	static boolean num_selected = false;
	static NumNode num_holder = null;
	static ColumnNode col_holder = null;
	static NumNode from_num_node = null;

	public ColumnsGame(Console eng) throws InterruptedException {
		ColumnsGame.eng = eng;
		box = new Box(eng);
		box.numberGenerator();
		locateFirstThirty();
		columns.display();
		col_holder = columns.getHead();
		initialize_game();
	}

	public static void initialize_game() {
		while (true) {
			red();
			eng.getTextWindow().setCursorPosition(x, 2);
			System.out.print("C" + col_holder.getColumnName());
			String input = keyList().toString();

			if (input.equalsIgnoreCase("B") && selected_box_element == 0) {
				selected_box_element = (int) box.representBoxElement();

			} else if (input.equalsIgnoreCase("X")) {

				if (selected_box_element != 0) {
					columns.addNumber(col_holder.getColumnName().toString(), selected_box_element);
					reset_the_game_coordinate();
					col_holder = columns.getHead();
					box.hideBoxElement();
					selected_box_element = 0;

					white();
					columns.display();
				}

				if (selected_box_element == 0 && num_selected) {
					if (Math.abs((int) from_num_node.getNumber() - (int) col_holder.getLastNode()) == 1
							|| (int) from_num_node.getNumber() - col_holder.getLastNode() == 0) {
						while (from_num_node != null) {
							columns.addNumber(col_holder.getColumnName().toString(), (int) from_num_node.getNumber());
							from_num_node = from_num_node.getNext();
						}
					}
					white();
					columns.display();	
				}

			} else if (input.equalsIgnoreCase("Z")) {
				if (!num_selected) {
					num_selected = true;
					from_num_node = num_holder;
					columns.remove_transfer_element(col_holder, from_num_node);
//					clearConsole();
					col_holder = columns.getHead();
					reset_the_game_coordinate();
					white();
					columns.display();
					
					
				}
			}

			else if (input.equalsIgnoreCase("E")) {
				if (!columnSelected) {
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

			else if (input.equalsIgnoreCase("Ex")) {

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

			}

			reset_keyList();

		}
	}
	
	public static void clearConsole() {
		eng.getTextWindow().setCursorPosition(10, 2);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 29; j++) {
				eng.getTextWindow().setCursorPosition(10+j, 2+i);

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

				columns.addNumber(String.valueOf(i),(int) temp.getData());

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
					return "Ex";
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
