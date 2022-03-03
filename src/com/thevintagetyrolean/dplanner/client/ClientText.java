package com.thevintagetyrolean.dplanner.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.thevintagetyrolean.dplanner.utils.DPIO;

public class ClientText {
	public static void DTextMode(boolean verbose) {
		System.out.println("Welcome to TheVintageTyrolean's Day Planner.");
		System.out.println("Loading primary config...");
		Scanner sc = new Scanner(System.in);
		try {
			ArrayList<String> entryList = new ArrayList<String>();
			ArrayList<String> fileList = new ArrayList<String>();
			DPIO.ReadFile(fileList, "startup.cfg");
			String username = fileList.get(0);

			System.out.println("Welcome " + username);
			if(fileList.size() < 2) {
				System.out.println("No list created. \nInput name for database: ");
				String name = sc.nextLine();
				DPIO.AppendToList(fileList, name, true);
				DPIO.TextDBPopulate(name, sc);
				DPIO.SyncList(entryList, "startup.cfg");
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
			DPIO.ReadFile(entryList, currentList);
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
						DPIO.TGenerateList(entryList, sc);
						DPIO.SyncList(entryList, currentList);
						DPIO.SyncList(fileList, "startup.cfg");
						break;
					}
				} else if(entry.toLowerCase().equals("add")) {
					System.out.println("What entry would you like to add?");
					entry = sc.nextLine();
					DPIO.AppendToList(entryList, entry, false);
					System.out.println("Syncing list");
					DPIO.SyncList(entryList, currentList);
				} else if(entry.toLowerCase().equals("modify")) {
					if(entryList.size() == 0) {
						System.out.println("List is empty.");
					} else {
						System.out.println("Which entry would you like to modify?");

						for( int i = 0; i < entryList.size(); i++) {
							System.out.println(String.format("%d. %s", i+1, entryList.get(i)));
						}
						System.out.println("\n");
						int choice = sc.nextInt();
						choice--;
						sc.nextLine();
						System.out.println("Input modified entry:");
						String mod = sc.nextLine();
						DPIO.ModifyEntry(entryList, mod, choice, false);
						System.out.println("Syncing list");
						DPIO.SyncList(entryList, currentList);
					}

				} else if(entry.toLowerCase().equals("delete")) {
					if(entryList.size() == 0) {
						System.out.println("List is empty.");
					} else {
						for(int i = 0; i < fileList.size(); i++) {
							System.out.println(String.format("%d. %s", i+1, fileList.get(i)));
						}
						System.out.println("\n");
						int choice;
						do {
							System.out.println("Which would you like to delete?");
							choice = sc.nextInt();
							choice--;
							System.out.println("Write True to confirm or False to change entry.");
						} while(sc.nextBoolean());
						DPIO.DeleteEntry(entryList, choice, false);
						System.out.println("Syncing list");
						DPIO.SyncList(entryList, currentList);
					}

				} else if(entry.toLowerCase().equals("exit")) {
					DPIO.SyncList(entryList, currentList);
					DPIO.SyncList(fileList, "startup.cfg");
					break;
				} else if(entry.toLowerCase().equals("add list")) {
					System.out.println("What list would you like to add?");
					entry = sc.nextLine();
					DPIO.AppendToList(fileList, entry, true);
					DPIO.SyncList(fileList, "startup.cfg");
					break;
				} if(entry.toLowerCase().equals("modify list")) {
					System.out.println("Which entry would you like to modify?");

					for( int i = 0; i < fileList.size(); i++) {
						System.out.println(String.format("%d. %s", i+1, fileList.get(i)));
					}
					System.out.println("\n");
					int choice = sc.nextInt();
					choice--;
					sc.nextLine();
					System.out.println("Input modified entry:");
					String mod = sc.nextLine();
					DPIO.ModifyEntry(fileList, mod, choice, true);
					System.out.println("Syncing list");
					DPIO.SyncList(fileList, "startup.cfg");
				} if(entry.toLowerCase().equals("delete list")) {
					if(fileList.size() > 3) {
						for(int i = 0; i < fileList.size(); i++) {
							System.out.println(String.format("%d. %s", i+1, fileList.get(i)));
						}
						System.out.println("\n");
						int choice;
						do {
							System.out.println("Which would you like to delete?");
							choice = sc.nextInt();
							choice--;
							System.out.println("Write True to confirm or False to change entry.");
						} while(sc.nextBoolean());
						DPIO.DeleteEntry(fileList, choice, true);
						System.out.println("Syncing list");
						DPIO.SyncList(fileList, "startup.cfg");
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
} 
