import java.util.Random;

import Lists.SinglyLinkedList;
import Lists.Nodes.SingleNode;
import enigma.console.Console;

public class Box {

	private SinglyLinkedList singleLinkedList;
	public static enigma.console.Console eng;
	Random random = new Random();

	public Box(Console eng) {
		Box.eng = eng;
		singleLinkedList = new SinglyLinkedList();
		locateBox();
	}

	public void numberGenerator() {
		int content = 1;
		for (int i = 0; i < 50; i++) {
			singleLinkedList.Add(content);
			content++;
			if (content % 10 == 1)
				content = 1;
		}
/*
		for (int i = 0; i < 1000; i++) {
			shuffle();
		}*/
	}

	public void shuffle() {

		int random_1 = random.nextInt(51);
		int random_2 = random.nextInt(51);
	
		while (!(random_2 - random_1 > 2)) {
			random_2 = random.nextInt(51);
			random_1 = random.nextInt(51);
		}
		SingleNode first_node = singleLinkedList.getHead();
		SingleNode second_node = singleLinkedList.getHead();

		// select first node
		for (int i = 1; i < random_1 - 1; i++) {
			first_node = first_node.getLink();
		}
		// select second node
		for (int i = 1; i < random_2 - 1; i++) {
			second_node = second_node.getLink();
		}
		SingleNode tempNode = singleLinkedList.setNode(first_node.getLink().getData(),
				second_node.getLink().getLink());
		SingleNode tempNode2 = singleLinkedList.setNode(second_node.getLink().getData(),
				first_node.getLink().getLink());
		first_node.setLink(tempNode2);
		second_node.setLink(tempNode);
	}

	public void locateBox() {
		eng.getTextWindow().setCursorPosition(30, 11);
		System.out.print("+--+");
		eng.getTextWindow().setCursorPosition(30, 12);
		System.out.print("|  |");
		eng.getTextWindow().setCursorPosition(30, 13);
		System.out.print("+--+");
		eng.getTextWindow().setCursorPosition(0,0);

	}

	public int representBoxElement() {
		eng.getTextWindow().setCursorPosition(31, 12);
		Object num = singleLinkedList.getHead().getData();
		if ((int) num < 10)
			System.out.print(num.toString()+" ");
		else {
			System.out.print(num.toString());

		}
		singleLinkedList.pop_front();

		return (int)num;
	}
	
	public void hideBoxElement() {
		eng.getTextWindow().setCursorPosition(31, 12);
		System.out.print("  ");
	}

	public SinglyLinkedList getSLL() {
		
		return singleLinkedList;
	}
}
