package com.thevintagetyrolean.dplanner.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Client {

public static void main(String[] args) {
		System.out.println("Welcome to TheVintageTyrolean's Day Planner.");
		System.out.println("Loading primary config...");
		FileReader r;
		BufferedReader output;
		FileWriter w;
		BufferedWriter input;
		Scanner sc = new Scanner(System.in);
		try {
			r = new FileReader("startup.cfg");
			output = new BufferedReader(r);
			String username = output.readLine();
			System.out.println("Welcome user " + username);
			String entry;
			if((entry = output.readLine()) == null) {
				System.out.println("No entries detected. Please input an entry you wish to include in the file and confirm with Enter. To finish, press enter with no input.");
				w = new FileWriter("startup.cfg");
				input = new BufferedWriter(w);
				while(!(entry = sc.nextLine()).equals("")) {
					input.append(entry);
					input.flush();
				}
				w.close();
				r.close();
				sc.close();
				return;
				
			} else {
				ArrayList<String> entryList = new ArrayList<String>();
				do {
					entryList.add(entry);
				} while((entry = output.readLine()) != null);
				System.out.println("Entries read.");
				System.out.println("What will you do? \n \"Generate\" List | \"Add\" entries | \"Modify\" Entries | \"Delete\" Entries | \"Exit\"");
				w = new FileWriter("startup.cfg");
				while(true) {
				entry = sc.nextLine();
				if(entry.toLowerCase().equals("generate")) {
					GenerateList(entryList, sc);
					SyncList(entryList, username, w, "startup.cfg");
					break;
				} else if(entry.toLowerCase().equals("add")) {
					AppendToList(entryList, sc);
					System.out.println("Syncing list");
					SyncList(entryList, username, w, "startup.cfg");
				} else if(entry.toLowerCase().equals("modify")) {
					ModifyEntry(entryList, sc);
					System.out.println("Syncing list");
					SyncList(entryList, username, w, "startup.cfg");
				} else if(entry.toLowerCase().equals("delete")) {
					DeleteEntry(entryList, sc);
					System.out.println("Syncing list");
					SyncList(entryList, username, w, "startup.cfg");
				} else if(entry.toLowerCase().equals("exit")) {
					SyncList(entryList, username, w, "startup.cfg");
					break;
				} else {
					System.out.println("Invalid option.");
				}
				}
				r.close();
				w.close();
				return;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Primary config file missing.");
			File entry = new File("startup.cfg");
			try {
				entry.createNewFile();
				System.out.println("Primary config file created.");
				System.out.println("Please input your username: ");
				String username = sc.nextLine();
				w = new FileWriter("startup.cfg");
				input = new BufferedWriter(w);
				input.append(username);	
				input.flush();
				String ipt;
				System.out.println("File created. Please input an entry you wish to include in the file and confirm with Enter. To finish, press enter with no input.");
				while(!(ipt = sc.nextLine()).equals("")) {
					input.newLine();
					input.append(ipt);
					input.flush();
				}
				w.close();
				return;
			} catch (IOException e1) {
		
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		

	}
static void GenerateList(ArrayList<String> entries, Scanner sc) {
	System.out.println("End generation on what hour?");
	int maxHR = sc.nextInt();
	System.out.println("Use Pomodoro technique?");
	boolean usePom = sc.nextBoolean();
	int breakLength, activityLength;
	if(!usePom) {
		System.out.println("Input activity length(in minutes)");
		breakLength = sc.nextInt();
		System.out.println("Input break length(in minutes)");
		activityLength = sc.nextInt();
		
	} else {
		breakLength = 5;
		activityLength = 20;
	}
	
	Random gen = new Random();
	LocalTime t = LocalTime.now();
	for(int i = 0; ;i++) {
		if(t.getHour() + (i*(activityLength + breakLength)/60) >= maxHR) {
			break;
		} else {
			System.out.println(String.format("%s to %s: %s", t.plusMinutes((activityLength + breakLength)*i).format(DateTimeFormatter.ofPattern("hh:mm a")),t.plusMinutes((i+1)*activityLength + i * breakLength).format(DateTimeFormatter.ofPattern("hh:mm a")), entries.get(gen.nextInt(entries.size()))));
			System.out.println(String.format("%s to %s: break", t.plusMinutes(activityLength*(i+1) + breakLength*i).format(DateTimeFormatter.ofPattern("hh:mm a")),t.plusMinutes((i+1)*(activityLength + breakLength)).format(DateTimeFormatter.ofPattern("hh:mm a"))));
		}
	}
}
static void AppendToList(ArrayList<String> entries, Scanner sc) {
	System.out.println("What would you like to add to the list?");
	String s = sc.nextLine();
	entries.add(s);
	
	
}
static void SyncList(ArrayList<String> entries, String username, FileWriter w, String fileName) throws IOException {
	w.close();
	w = new FileWriter(fileName);
	BufferedWriter input = new BufferedWriter(w);
	for(int i = 0; i <= entries.size(); i++) {
		if(i == 0) {
			input.write(username + "\n");
			input.flush();
		} else {
			input.write(entries.get(i-1) + "\n");
			input.flush();
		}
	}
	input.close();
		
}
	
static void ModifyEntry(ArrayList<String> entries, Scanner sc) {
	System.out.println("Which entry would you like to modify?");
	for(int i = 0; i < entries.size(); i++) {
		System.out.println(String.format("%d. %s", i+1, entries.get(i)));
	}
	System.out.println("\n");
	int choice = sc.nextInt();
	sc.nextLine();
	System.out.println("Input modified entry:");
	String mod = sc.nextLine();
	entries.set(choice-1, mod);
	
	
}
static int DeleteEntry(ArrayList<String> entries, Scanner sc) {
	System.out.println("Which entry would you like to delete?");
	for(int i = 0; i < entries.size(); i++) {
		System.out.println(String.format("%d. %s", i+1, entries.get(i)));
	}
	System.out.println("\n");
	int choice = sc.nextInt();
	entries.remove(choice);
	
	return entries.get(entries.size()-1).length();
}
}
