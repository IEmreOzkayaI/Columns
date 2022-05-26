import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Lists.DoublyLinkedList;
import Lists.Nodes.DoubleNode;

public class HighScoreTable {
    private DoublyLinkedList data = new DoublyLinkedList();
    
    public HighScoreTable() {
        this.data = readHighScoreTableFile();
        sortHighScoreTable();
    }

    private DoublyLinkedList readHighScoreTableFile() {
        DoublyLinkedList highScoreTable = new DoublyLinkedList();
        File reader = new File("HighScoreTable.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String name = currentLine.split("-")[0];
                String score = currentLine.split("-")[1];
                HighScoreData scoreData = new HighScoreData(name, Double.parseDouble(score));
                DoubleNode newScore = new DoubleNode(scoreData);
                highScoreTable.insertLast(newScore);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return highScoreTable;
    }

    private void sortHighScoreTable() {
    	DoublyLinkedList sortedList = new DoublyLinkedList();
        sortedList.insert(data.getIndex(0).getData(), 0); // Insert first value initially
       
        for (int i = 1; i < data.size(); i++) {
            HighScoreData highScoreData = (HighScoreData) data.getIndex(i).getData();

            for (int j = 0; j < i; j++) { 
                if (j == i -1) { // if score is not greater than the other scores until the last, put it in last 
                    sortedList.insertLast(new DoubleNode(highScoreData));                   
                    break;
                }

                HighScoreData prevHighScoreData = (HighScoreData) sortedList.getIndex(j).getData(); // if score is greater than prev, insert it in front of that score
                if (highScoreData.getScore() >= prevHighScoreData.getScore()) {
                    sortedList.insert(highScoreData, j);
                    break;
                }
            }
        }
                    
      //  sortedList.deleteLast();
                 
        this.data = sortedList;
    }
    
    

    public void printScores() {
        System.out.println("High Score Table");
        DoubleNode temp = data.getHead();
        for (int i = 0; i < data.size() ; i++) {
        	
            HighScoreData scoreData = (HighScoreData) temp.getData();

            System.out.print(scoreData.getName());
            for (int j = 0; j < 15 - scoreData.getName().length(); j++)
                System.out.print(" ");
            System.out.print(scoreData.getScore() + "\n");
            temp = temp.getNext();
        }
    }

    public void addPlayerScore(String playerName, double score) {
        HighScoreData scoreData = new HighScoreData(playerName, score);
        DoubleNode playerScore = new DoubleNode(scoreData);
        data.insertLast(playerScore);
        sortHighScoreTable();
    }
    
    public void writeSortedHighScoreTableToFile() {
        try {
            FileWriter writer = new FileWriter("highScoreTable.txt");
            for (int i = 0; i < data.size(); i++) {
                HighScoreData scoreData = (HighScoreData) data.getIndex(i).getData();
                writer.write(scoreData.getName() + "-" + scoreData.getScore() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
