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
public class Client {

public static void main(String[] args) {
		System.out.println("Welcome to TheVintageTyrolean's Day Planner.");
		DPIO.ReadArgs(args);
		System.out.println("Loading primary config...");
		Scanner sc = new Scanner(System.in);
		JFrame window = new JFrame();
		try {
				ArrayList<String> entryList = new ArrayList<String>();
				ArrayList<String> fileList = new ArrayList<String>();
				DPIO.ReadFile(fileList, "startup.cfg");
				String username = fileList.get(0);
				
				System.out.println("Welcome " + username);
				if(fileList.size() < 2) {
				System.out.println("No list created.");
				DPIO.AppendToList(fileList, sc, true);
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
						DPIO.GenerateList(entryList, sc);
						DPIO.SyncList(entryList, currentList);
						DPIO.SyncList(fileList, "startup.cfg");
					break;
					}
				} else if(entry.toLowerCase().equals("add")) {
					DPIO.AppendToList(entryList, sc, false);
					System.out.println("Syncing list");
					DPIO.SyncList(entryList, currentList);
				} else if(entry.toLowerCase().equals("modify")) {
					if(entryList.size() == 0) {
						System.out.println("List is empty.");
					} else {
						DPIO.ModifyEntry(entryList, sc, false);
					System.out.println("Syncing list");
					DPIO.SyncList(entryList, currentList);
					}
			
				} else if(entry.toLowerCase().equals("delete")) {
					if(entryList.size() == 0) {
					System.out.println("List is empty.");
					} else {
						DPIO.DeleteEntry(entryList, sc, false);
					System.out.println("Syncing list");
					DPIO.SyncList(entryList, currentList);
					}
				
				} else if(entry.toLowerCase().equals("exit")) {
					DPIO.SyncList(entryList, currentList);
					DPIO.SyncList(fileList, "startup.cfg");
					break;
				} else if(entry.toLowerCase().equals("add list")) {
					DPIO.AppendToList(fileList, sc, true);
					DPIO.SyncList(fileList, "startup.cfg");
					break;
				} if(entry.toLowerCase().equals("modify list")) {
					DPIO.ModifyEntry(fileList, sc, true);
					System.out.println("Syncing list");
					DPIO.SyncList(fileList, "startup.cfg");
				} if(entry.toLowerCase().equals("delete list")) {
					if(fileList.size() >= 3) {
						DPIO.DeleteEntry(fileList, sc, true);
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
