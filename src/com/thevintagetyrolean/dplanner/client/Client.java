package com.thevintagetyrolean.dplanner.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Client {

public static void main(String[] args) {
		System.out.println("Welcome to TheVintageTyrolean's Day Planner.");
		System.out.println("Loading primary config...");
		Scanner sc = new Scanner(System.in);
		try {
				ArrayList<String> entryList = new ArrayList<String>();
				ArrayList<String> fileList = new ArrayList<String>();
				ReadFile(fileList, "startup.cfg");
				String username = fileList.get(0);
				
				System.out.println("Welcome " + username);
				if(fileList.size() < 2) {
				System.out.println("No list created.");
				AppendToList(fileList, sc, true);
				SyncList(entryList, "startup.cfg");
				}
				
				for(int i = 1; i < fileList.size(); i++) {
					System.out.println(i + ". " + fileList.get(i));
					
				}
				int pick;
				do {
				System.out.println("Pick a list.");
				pick = sc.nextInt();
				} while(!(pick > 0 && pick < fileList.size()));
				String currentList = fileList.get(pick) + ".list";
				ReadFile(entryList, currentList);
				sc.nextLine();
				System.out.println("Entries read.");
				System.out.println("What will you do? \n \"Generate\" List | \"Add\" entries | \"Modify\" Entries | \"Delete\" Entries | \n\"Add List\" | \"Modify List\" | \"Delete List\" | \"Exit\"");
				String entry;
				
				while(true) {
				entry = sc.nextLine();
				if(entry.toLowerCase().equals("generate")) {
					if(entryList.size() == 0) {
						System.out.println("List is empty.");
					} else {
					GenerateList(entryList, sc);
					SyncList(entryList, currentList);
					SyncList(fileList, "startup.cfg");
					break;
					}
				} else if(entry.toLowerCase().equals("add")) {
					AppendToList(entryList, sc, false);
					System.out.println("Syncing list");
					SyncList(entryList, currentList);
				} else if(entry.toLowerCase().equals("modify")) {
					if(entryList.size() == 0) {
						System.out.println("List is empty.");
					} else {
					ModifyEntry(entryList, sc, false);
					System.out.println("Syncing list");
					SyncList(entryList, currentList);
					}
			
				} else if(entry.toLowerCase().equals("delete")) {
					if(entryList.size() == 0) {
					System.out.println("List is empty.");
					} else {
					DeleteEntry(entryList, sc, false);
					System.out.println("Syncing list");
					SyncList(entryList, currentList);
					}
				
				} else if(entry.toLowerCase().equals("exit")) {
					SyncList(entryList, currentList);
					SyncList(fileList, "startup.cfg");
					break;
				} else if(entry.toLowerCase().equals("add list")) {
					AppendToList(fileList, sc, true);
					SyncList(fileList, "startup.cfg");
					break;
				} if(entry.toLowerCase().equals("modify list")) {
					ModifyEntry(fileList, sc, true);
					System.out.println("Syncing list");
					SyncList(fileList, "startup.cfg");
				} if(entry.toLowerCase().equals("delete")) {
					if(fileList.size() >= 3) {
					DeleteEntry(fileList, sc, true);
					System.out.println("Syncing list");
					SyncList(fileList, "startup.cfg");
					} else {
						System.out.println("Only one list left. Cannot delete.");
					}
				} else {
					System.out.println("Invalid option.");
				}
				}
				return;
			 
			
		} catch (FileNotFoundException e) {
			System.out.println("Primary config file missing.");
			File entry = new File("startup.cfg");
			try {
				entry.createNewFile();
				System.out.println("Primary config file created.");
				System.out.println("Please input your username: ");
				String username = sc.nextLine();
				FileWriter w = new FileWriter("startup.cfg");
				BufferedWriter input = new BufferedWriter(w);
				input.append(username);	
				input.flush(); 
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
static void AppendToList(ArrayList<String> entries, Scanner sc, boolean onFile) throws IOException {
	System.out.println("What will you add?");
	String s = sc.nextLine();
	entries.add(s);
	if(onFile) {
		File f = new File(s + ".list");
		f.createNewFile();
		String ipt;
		FileWriter w = new FileWriter(f);
		BufferedWriter input = new BufferedWriter(w);
		System.out.println("File created. Please input an entry you wish to include in the file and confirm with Enter. To finish, press enter with no input.");
		while(!(ipt = sc.nextLine()).equals("")) {
			input.newLine();
			input.append(ipt);
			input.flush();
		}
		w.close();
	}
	
	
}
static void SyncList(ArrayList<String> entries, String fileName) throws IOException {
	
	FileWriter w = new FileWriter(fileName);
	BufferedWriter input = new BufferedWriter(w);
	for(int i = 0; i < entries.size(); i++) {
			input.write(entries.get(i) + "\n");
			input.flush();
		
	}
	w.close();
		
}
	
static void ModifyEntry(ArrayList<String> entries, Scanner sc, boolean onFile) throws IOException {
	System.out.println("Which entry would you like to modify?");
	for(int i = 0; i < entries.size(); i++) {
		System.out.println(String.format("%d. %s", i+1, entries.get(i)));
	}
	System.out.println("\n");
	int choice = sc.nextInt();
	choice--;
	sc.nextLine();
	System.out.println("Input modified entry:");
	String mod = sc.nextLine();
	if(onFile) {
	File f = new File(entries.get(choice));
	Path source = Paths.get(f.getAbsolutePath());
	Files.move(source, source.resolveSibling(mod));
	}
	entries.set(choice, mod);
	
	
	
}
static void DeleteEntry(ArrayList<String> entries, Scanner sc, boolean onFile) {
	
	for(int i = 0; i < entries.size(); i++) {
		System.out.println(String.format("%d. %s", i+1, entries.get(i)));
	}
	System.out.println("\n");
	int choice;
	do {
	System.out.println("Which would you like to delete?");
	choice = sc.nextInt();
	choice--;
	System.out.println("Write True to confirm or False to cancel.");
	} while(sc.nextBoolean());
	if(onFile) {
		File f = new File(entries.get(choice));		
		System.out.println("Deleting table " + choice + ": " + entries.get(choice));
		System.out.println("Result: " + f.delete());
	}
	entries.remove(choice);
	
	
}
static void ReadFile(ArrayList<String> entryList, String file) throws IOException {
	FileReader f = new FileReader(file);
	BufferedReader output = new BufferedReader(f);
	String entry;
	while((entry = output.readLine()) != null){
		entryList.add(entry);
	}
	f.close();
	
}
}
