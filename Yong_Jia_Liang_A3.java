//This project is done by Yong Jia Liang, UOW ID 7083609
//All codes in this file is my own.
//I did not pass my work to any friend.
//I am willing to accept whatever penalty given to me.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

class Olympic
{
	private int NO = 10;
	private String country;
	private double[] score = new double [10];
	private int rank;
	
	// Default constructor
	public Olympic(String country)
	{
		this.country = country;
	}
	
	// Copy constructor
	public Olympic(Olympic Oly)
	{
		this (Oly.country);
	}
	
	// Creates random score per judge
	public void processScores()
	{
		Random rand = new Random();
		for (int i = 0; i < NO; i++)
		{
			score[i] = 1.0 + rand.nextDouble() * 98.9;
		}
	}
	
	// Sums up every score per country
	public double totalScores()
	{
		double sum = DoubleStream.of(score).sum();
		return sum;
	}
	
	// Sets the rank for every country
	public void set(int rank)
	{
		this.rank = rank;
	}
	
	// Accessor methods
	public int getRank()
	{
		return rank;
	}
	
	public String getName()
	{
		return country;
	}
	
	private double[] getScroreArray()
	{
		return score;
	}
	
	public String toString()
	{
		return String.format("Rank %d: %s (%.2f)%n", rank, country, totalScores());
	}
}

class OlympicFrame extends JFrame
{
	private JButton [] jbArray;
	private final String [] countryArray = {"USA", "SPAIN", "CHINA", "JAPAN", "ITALY", "GERMANY", "FRANCE", "BRAZIL", 
										"NETHERLANDS", "POLAND", "RUSSIA", "UKRAINE"};
	private ArrayList <Olympic> alist = new ArrayList <Olympic>();
	
	public OlympicFrame()
	{
		super ("Olympic 2020");
		setLayout (new GridLayout (3, 4));
		
		double [] scoreArray = new double [countryArray.length];				// declare total score array
		constructAList();														// initiate method
		
		for (int i = 0; i < countryArray.length; i++)							// stores all total scores into scoreArray
		{
			scoreArray[i] = getScores(alist, countryArray[i]);
		}
		
		Arrays.sort(scoreArray); 												// sort array
		double [] temparray = Arrays.stream(scoreArray).boxed()					// reverse the order of the array
									.sorted(Collections.reverseOrder())
									.mapToDouble(Double::doubleValue)
									.toArray();
		scoreArray = temparray;
		
		for (int i = 0; i < alist.size(); i++)									// set the rank for each country
		{
			int rank = getRank(scoreArray, alist.get(i).totalScores());
			alist.get(i).set(rank);
		}
		
		jbArray = new JButton [countryArray.length]; 							// declare array
		Icon trophy = new ImageIcon ("trophy.jpg");								// add trophy icon
		for (int i = 0; i < countryArray.length; i++)							// initialize each button
		{
			int rank = getRank(scoreArray, getScores(alist, countryArray[i])); 	// create integer object for rank
			String showRank = "Rank: " + rank; 									// create String object for rank
			Icon image = new ImageIcon (countryArray[i] + ".png");
			jbArray[i] = new JButton (image);
			jbArray[i].setToolTipText(countryArray[i]);
			int a = i;
			jbArray[i].addActionListener(e -> 									// event where button is clicked
			{
				jbArray[a].setText(showRank);									// show rank
				JOptionPane.showMessageDialog(null, getFinalRanking(), 			// pop up window for final ranking
								"Miss World 2020", JOptionPane.PLAIN_MESSAGE, trophy);
			});
			add (jbArray[i]);
		}
	}
	
	// Creates object for every country in countryArray to add into arrayList alist
	private void constructAList()
	{
		for (int i = 0; i < countryArray.length; i++)
		{
			Olympic oly = new Olympic(countryArray[i]);
			oly.processScores();
			alist.add(oly);
		}
	}
	
	private int getRank(double [] scoreArray, double d)
	{
		int rank = 0;
		for (int i = 0; i < scoreArray.length; i++)
		{
			if (scoreArray[i] == d)
			{
				rank = i+1;
			}
		}
		return rank;
	}
	
	private String getFinalRanking()
	{
		String rank = "FINAL RANKING\n\n";
		for (int i = 0; i < alist.size(); i++)
		{
			for (int k = 0; k < alist.size(); k++)
			{
				if (alist.get(k).getRank() == i+1)
				{
					rank += alist.get(k);
				}
			}
		}
		return rank;
	}
	
	private String getCountry(ArrayList <Olympic> alist, int n)
	{
		String tempname = "";
		for (int i = 0; i < alist.size(); i++)
		{
			if (alist.get(i).totalScores() == n)
			{
				tempname = alist.get(i).getName();
			}
		}
		return tempname;
	}
	
	private double getScores(ArrayList <Olympic> alist, String name)
	{
		double tempscore = 0.0;
		for (int i = 0; i < alist.size(); i++)
		{
			if (alist.get(i).getName() == name)
			{
				tempscore = alist.get(i).totalScores();
			}
		}
		return tempscore;
	}
}

class a3
{
	public static void main (String [] args)
	{
		OlympicFrame oly = new OlympicFrame();
		oly.setSize(1000, 500);
		oly.setVisible(true);
		oly.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
}