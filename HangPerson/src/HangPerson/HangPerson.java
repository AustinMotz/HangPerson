package HangPerson;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;


public class HangPerson {

	private static JFrame 				window	= new JFrame();
	private static JPanel 				panel 	= new JPanel();
	private static JButton				enter	= new JButton("Enter");
	private static JFormattedTextField 	tries 	= new JFormattedTextField("Tries");
	private static JFormattedTextField 	length 	= new JFormattedTextField("Length");
	private static JFormattedTextField 	word 	= new JFormattedTextField("");
	private static JFormattedTextField  remain  = new JFormattedTextField("");
	private static JLabel 				info	= new JLabel("Info: Enter your word length and guesses!");
	private static JLabel 				end		= new JLabel("");

	
	private static ArrayList<String> remaining = new ArrayList<String>();
	
	
	private static int attempt = 0;
	private static int maxTries;
	private static int wordLength;
	private static JButton[] alphabet = new JButton[26];
	private static ImageIcon man = new ImageIcon("Images\\hangman"+attempt+".png");
	private static JLabel LMan = new JLabel(man);
	
	
	
	public static void main(String[] args) throws Exception {
		windowSetUp();
		buttons();
		inputs();
		labels();
		
		photos();
		
		
		
		//info.getText();
		//info.setText("Hang Person!");
	}
	
	private static void photos() throws Exception {
		man = new ImageIcon("Images\\hangman"+attempt+".png");
		LMan = new JLabel(man);
		LMan.setBounds(425,20,193,292);
		panel.add(LMan);
	}

	private static void windowSetUp(){
		window.setTitle("Hang Person");
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setSize(700, 500);
		panel.setLayout(null);
		window.add(panel);
	}
	
	private static void labels(){
		info.setBounds(20, 0, 660, 30);
		panel.add(info);
	}
	
	private static void letters(){
		int space = 55;
		int marginX = 10;
		int marginY = 30;
		int maxRow = 6;
		
		for(int col = 0; col < Math.round((26.0/maxRow)+0.34); col++)
			for(int row = 0; row < maxRow; row++){
				if(row + col*maxRow < 26){
					alphabet[row + col*maxRow] = new JButton((char)(65 + row + col*maxRow) + "");
					letterSetUp(alphabet[row + col*maxRow], marginX+space*row, marginY+space*col);
					}
				}
	}
	
	private static void letterSetUp(JButton letter, int x, int y){
		letter.setBounds(x, y, 50, 50);
		panel.add(letter);
		window.revalidate();
		window.repaint();
		letter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(letter.isEnabled()){
					attempt++;
					if(maxTries - attempt <= 0){
						lose();
					} else {
					updateMan();
					remaining = findBestFamily(letter.getText());
					panel.remove(letter);
					remain.setText("You have " + (maxTries - attempt) + " tries left");					
					window.revalidate();
					window.repaint();
					}
				}}});
	}
	
	private static void updateMan(){
		panel.remove(LMan);
		try {
			photos();
		} catch (Exception e) {
		e.printStackTrace();
		}
		panel.add(LMan);
	}
	
	
	private static void buttons(){
		
		enter.setBounds(window.getWidth() - 160, window.getHeight() - 110, 100, 50);
		
		panel.add(enter);
		enter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(enter.isEnabled()){
					tries.setEditable(false);
					length.setEditable(false);
					maxTries = Integer.parseInt(tries.getText(),10);
					wordLength = Integer.parseInt(length.getText(),10);
					panel.remove(tries);
					panel.remove(length);
					panel.remove(enter);
					panel.remove(info);
					
					remain.setBounds(50, window.getHeight() - 110, 200, 50);
					remain.setEditable(false);
					remain.setText("You have " + (maxTries - attempt) + " tries left");
					word.setBounds(300, window.getHeight() - 110, 200, 50);
					word.setEditable(false);
					panel.add(remain);
					panel.add(word);
					letters();
					window.revalidate();
					window.repaint();
					try {
						remaining = getDictionary(wordLength);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}});
	}
	
	private static ArrayList<String> findBestFamily(String letter) {
		HashMap<String, ArrayList<String>> families = new HashMap<String, ArrayList<String>>();
		String pattern = "";
		String bestKey = "";
		for(String guess: remaining){
			pattern = toPattern(guess, letter);
			ArrayList<String> value;
			if(families.containsKey(pattern)){
				value = (ArrayList<String>)families.get(pattern);
			} else {
				value = new ArrayList<String>();
			}
			value.add(guess);
			families.put(pattern, value);
			bestKey = pattern;
		}
		word.setText(bestKey);
		return families.get(bestKey);
	}
		
	private static void inputs() {
		tries.setEditable(true);
		length.setEditable(true);
		tries.setBounds(410, window.getHeight() - 110, 50, 50);
		length.setBounds(475, window.getHeight() - 110, 50, 50);
		panel.add(tries);
		panel.add(length);

	}
	
	private static String toPattern(String word, String letter){
		String pattern = "";
		for(int i = 0; i < word.length(); i++) {
			if(word.substring(i,i+1).equals(letter))
				pattern += letter;
			else
				pattern += "_";	
		}
		return pattern;
	}
	
	private static ArrayList<String> getDictionary(int length) throws Exception{
		Scanner dictionary = new Scanner(new File("Dictionary.txt"));
		ArrayList<String> words = new ArrayList<String>();
		
		while(dictionary.hasNext()) {
			if(dictionary.nextLine().length() == length)
				words.add(dictionary.nextLine());
		}
		return words;
	}
	private static void lose(){
		panel.removeAll();
		attempt = 26;
		updateMan();
		end.setText("YOU LOSE!");
		end.setBounds(300, 50, 300, 300);
		word.setBounds(200, 250, 200, 50);
		panel.add(end);
		panel.add(word);
		window.revalidate();
		window.repaint();
	}
}
