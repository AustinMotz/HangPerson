package HangPerson;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;


public class HangPerson {
	//window variables
	private static JFrame 				window;
	private static JPanel 				panel;
	private static JButton				enter;
	private static JFormattedTextField 	tries;
	private static JFormattedTextField 	length;
	private static JFormattedTextField 	word;
	private static JFormattedTextField  remain;
	private static JLabel 				info;
	private static JLabel 				end;
	private static JButton				play;
	private static JButton[] alphabet;
	private static ImageIcon man;
	private static JLabel LMan;

	//variables for function of game
	private static ArrayList<String> remaining;
	private static ArrayList<String> lettersLeft;
	private static int attempt;
	private static int maxTries;
	private static int wordLength;
	
	public static void main(String[] args) throws Exception {
		play();
	}
	
	/*
	 * sets up the game so it can be played again
	 */
	public static void play() throws Exception {
		windowSetUp();
		buttons();
		inputs();
		labels();
		photos();
		panel.repaint();
		panel.revalidate();
	}
	
	/*
	 * creates the initial hangman photo
	 */
	private static void photos() throws Exception {
		man = new ImageIcon("./Images/hangman"+attempt+".png");
		LMan = new JLabel(man);
		LMan.setBounds(425,20,193,292);
		panel.add(LMan);
	}

	/*
	 * initializes all the variables 
	 */
	private static void windowSetUp(){
		
		window	= new JFrame();
		panel 	= new JPanel();
		enter	= new JButton("Enter");
		tries 	= new JFormattedTextField("Tries");
		length 	= new JFormattedTextField("Length");
		word 	= new JFormattedTextField("");
		remain  = new JFormattedTextField("");
		info	= new JLabel("Info: Enter your word length and guesses!");
		end		= new JLabel("");
		play	= new JButton("Play Again!");

		remaining = new ArrayList<String>();
		lettersLeft = new ArrayList<String>();
		
		attempt = 0;
		alphabet = new JButton[26];
		man = new ImageIcon("./Images/hangman"+attempt+".png");
		LMan = new JLabel(man);
		
		window.setTitle("Hang Person 2.0");
		window.pack();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setSize(700, 500);
		panel.setLayout(null);
		window.add(panel);
		panel.removeAll();
		panel.repaint();
		panel.revalidate();
		attempt = 0;
	}
	
	/*
	 * creates the JLabels
	 */
	private static void labels(){
		info.setBounds(20, 0, 660, 30);
		panel.add(info);
	}
	
	/*
	 * makes the tries and length fields to enter
	 */
	private static void inputs() {
		tries.setText("tries");
		length.setText("length");
		tries.setEditable(true);
		length.setEditable(true);
		tries.setBounds(410, window.getHeight() - 110, 50, 50);
		length.setBounds(475, window.getHeight() - 110, 50, 50);
		panel.add(tries);
		panel.add(length);

	}
	/*
	 * makes the letters of the alphabet to be able to play the game
	 */
	private static void letters(){
		int space = 55;
		int marginX = 10;
		int marginY = 30;
		int maxRow = 6;
		
		for(int col = 0; col < Math.round((26.0/maxRow)+0.34); col++) {
			for(int row = 0; row < maxRow; row++) {
				if(row + col*maxRow < 26) {
					alphabet[row + col*maxRow] = new JButton((char)(65 + row + col*maxRow) + "");
					lettersLeft.add((char)(65 + row + col*maxRow) + "");
					letterSetUp(alphabet[row + col*maxRow], marginX+space*row, marginY+space*col);
				}
			}
		}
	}
	
	/*
	 * adds a JButton letter to the screen
	 * 
	 * @Param letter JButton to be added to the window
	 * @Param x integer coordinate within the window of where letter is
	 * @Param y integer coordinate within the window of where letter is
	 */
	private static void letterSetUp(JButton letter, int x, int y){
		letter.setBounds(x, y, 50, 50);
		panel.add(letter);
		window.revalidate();
		window.repaint();
		letter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(letter.isEnabled()){
					chooseLetter(letter);
				}
			}
		});
	}
	/*
	 *  guesses the letter and updates the GUI accordingly
	 * 
	 * @Param letter - JButton being chosen to guess
	 */
	private static void chooseLetter(JButton letter) {
		lettersLeft.remove(letter.getText());
		panel.remove(letter);
		String theWord = remaining.get(0);
		remaining = findBestFamily(letter.getText().toLowerCase());
		if(!word.getText().contains(letter.getText().toLowerCase()) && remaining != null)
			attempt++;
		updateMan();
		remain.setText("You have " + (maxTries - attempt) + " tries left");
		if(maxTries - attempt <= 0)
			lose();
		else if(!word.getText().contains("_")) {
			word.setText(theWord);
			win();
		}
		window.revalidate();
		window.repaint();
	}	
	
	/*
	 * Updates the photo of the hangman
	 * 
	 */
	private static void updateMan(){
		panel.remove(LMan);
		try {
			photos();
		} catch (Exception e) {
		e.printStackTrace();
		}
		panel.add(LMan);
	}
	
	/*
	 * make enter button to start the game
	 */
	private static void buttons(){
		enter.setBounds(window.getWidth() - 160, window.getHeight() - 110, 100, 50);
		panel.add(enter);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(enter.isEnabled()){
					initiateGame();
				}
			}
		});
	}
	
	/*
	 * enter the length and guesses from text box and remake the screen to the letters.
	 * Then Let guessing begin
	 */
	private static void initiateGame() {
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
	
	/*
	 * finds the best pattern to pursue to make the player lose
	 * 
	 * @Param letter String of letter being guessed
	 * @return arraylist of Strings of remaining posssible words that fit the pattern
	 */
	private static ArrayList<String> findBestFamily(String letter) {
		HashMap<String, ArrayList<String>> families = new HashMap<String, ArrayList<String>>();
		String pattern = "";
		String bestKey = "";
		for(String guess: remaining){
			pattern = toPattern(guess);
			ArrayList<String> value;
			if(families.containsKey(pattern)){
				value = (ArrayList<String>)families.get(pattern);
			} else {
				value = new ArrayList<String>();
			}
			value.add(guess);
			families.put(pattern, value);
		}
		bestKey = findFamilies(families);
		word.setText(bestKey);
		return families.get(bestKey);
	}
	
	/*
	 * finds the best pattern to use based on the number of letters remaining
	 * in the remaining words and the average unique letters per word
	 * 
	 * @Param families - hashmap of words to fit a string pattern
	 * @return String of best pattern
	 */
	private static String findFamilies(HashMap<String, ArrayList<String>> families){
		String bestPattern = "";
		int highest = 0;
		double highestAvg = 0;
		for(String key: families.keySet()) {
			int numKeys = 0;
			int numWords = 0;
			int lettersInWord = 0;
			for(String value: families.get(key)) {
				numWords++;
				for(int i = 0; i < value.length(); i++) {
					if(lettersLeft.contains((""+value.charAt(i)).toUpperCase())){
							lettersInWord++;
					}
				}
			}
			if(numKeys > highest || (numKeys == highest && highestAvg < (double)lettersInWord/numWords)){
				bestPattern = key;
				highest = numKeys;
				highestAvg = (double)lettersInWord/numWords;
			}
		}
		return bestPattern;
	}
		
	
	/*
	 * Converts a word to a pattern with underscores in place of unguessed letters with a space following it
	 * @Param word to convert into the pattern
	 * 
	 * @return the word converted to a pattern with underscores as undiscovered letters
	 */
	private static String toPattern(String convert){
		String pattern = "";
		for(int i = 0; i < convert.length(); i++) {
			if(lettersLeft.contains((""+convert.charAt(i)).toUpperCase())) {
				pattern += "_ ";
			}
			else {
				pattern += convert.charAt(i) + " ";
			}
		}
		return pattern;
	}
	
	/* Populates an arraylist of the letters in the dictionary at a given length
	 * 
	 * @Param length  - integer of word length to get from dictionary
	 * 
	 * @return Arraylist of Strings of word in the dictionary at the given length
	 */
	private static ArrayList<String> getDictionary(int length) throws Exception{
		Scanner dictionary = new Scanner(new File("Dictionary.txt"));
		ArrayList<String> words = new ArrayList<String>();
		
		while(dictionary.hasNext()) {
			words.add(dictionary.nextLine());
			if(words.get(words.size()-1).length() != length)
				words.remove(words.size()-1);
		}
		String start = "";
		for(int i = 0; i < length; i++)
			start += "_ ";
		word.setText(start);
		return words;
	}
	
	
	/*
	 * Reset the game to play again without launching for a second time
	 */
	private static void again(){
		play.setBounds(250, 300, 100, 50);
		panel.add(play);
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(enter.isEnabled()){
					window.dispose();
					try {
						play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/*
	 * display the win screen
	 */
	private static void win(){
		panel.removeAll();
		updateMan();
		end.setText("You Win!");
		end.setBounds(300, 50, 300, 300);
		word.setBounds(200, 250, 200, 50);
		again();
		panel.add(end);
		panel.add(word);
		window.revalidate();
		window.repaint();
	}
	
	/*
	 * display the lose screen
	 */
	private static void lose(){
		panel.removeAll();
		attempt = 26;
		updateMan();
		again();
		word.setText(remaining.get((int)(Math.random()*remaining.size())));
		end.setText("TAKE THE L!");
		end.setBounds(300, 50, 300, 300);
		word.setBounds(200, 250, 200, 50);
		panel.add(end);
		panel.add(word);
		window.revalidate();
		window.repaint();
	}
}
