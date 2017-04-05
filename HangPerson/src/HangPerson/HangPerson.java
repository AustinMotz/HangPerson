package HangPerson;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class HangPerson {

	private static JFrame window = new JFrame();
	private static JPanel panel = new JPanel();
	private static JButton enter = new JButton("Enter");
	private static JButton A = new JButton("A");
	private static JFormattedTextField input = new JFormattedTextField("");
	private static JLabel info = new JLabel("Info: ");
	private static ArrayList<String> remaining = new ArrayList<String>();
	
	private static JButton a = new JButton("A");
	
	
	public static void main(String[] args) throws Exception {
		getDictionary();
		windowSetUp();
		buttons();
		inputs();
		labels();
		letters(a, 200, 200);
		info.getText();
		info.setText("Hang Person!");
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
		info.setBounds(20, 20, 660, 30);
		panel.add(info);
	}
	
	private static void letters(JButton letter, int x, int y){
		letter.setBounds(x, y, 50, 50);
		panel.add(letter);
		letter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(letter.isEnabled()){
					findBestFamily(letter.getText());
				}
				
			}
			
		});
	}
	
	private static void buttons(){
		
		enter.setBounds(window.getWidth() - 160, window.getHeight() - 110, 100, 50);
		panel.add(enter);
		enter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(enter.isEnabled()){
					
				}
				
			}});
	}
	
	private static ArrayList<String> findBestFamily(String letter) {
		HashMap<String, ArrayList<String>> families = new HashMap<String, ArrayList<String>>();
		
		return null;
	}
		
	private static void inputs() {
		input.setEditable(true);
		input.setBounds(10, window.getHeight() - 100, 200, 50);
		panel.add(input);
		
	}
	
	private static ArrayList<String> getDictionary() throws Exception{
		Scanner dictionary = new Scanner(new File("Dictionary.txt"));
		ArrayList<String> words = new ArrayList<String>();
		
		while(dictionary.hasNext()) {
			words.add(dictionary.nextLine());
		}
		return words;
	}
}
