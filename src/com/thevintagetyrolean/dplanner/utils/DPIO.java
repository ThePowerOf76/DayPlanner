package com.thevintagetyrolean.dplanner.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DPIO {
	public static void AppendToList(ArrayList<String> entries, String s, boolean onFile) throws IOException {
		//Append + create file if adding new storage.
		entries.add(s);
		if(onFile) {
			File f = new File(s + ".list");
			f.createNewFile();
			
		}
		
		
	}
	public static void TextDBPopulate(String filename, Scanner sc) throws IOException {
		//Multi append to list upon new storage creation.
		File f = new File(filename + ".list");
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
	public static void TGenerateList(ArrayList<String> entries, Scanner sc) {
		//Text mode list generation.
		System.out.println("End generation on what hour?");
		int maxHR = sc.nextInt();
		System.out.println("Use Pomodoro technique?");
		boolean usePom = sc.nextBoolean();//Pomodoro 
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
	public static void SyncList(ArrayList<String> entries, String fileName) throws IOException {
		//Update file
		FileWriter w = new FileWriter(fileName);
		BufferedWriter input = new BufferedWriter(w);
		for(int i = 0; i < entries.size(); i++) {
				input.write(entries.get(i) + "\n");
				input.flush();
			
		}
		w.close();
			
	}
	public static void ModifyEntry(ArrayList<String> entries, String mod, int choice, boolean onFile) throws IOException {
	//Change filename if working on storage and change name in the list of storages
	if(onFile) {
	File f = new File(entries.get(choice) + ".list");
	Path source = Paths.get(f.getAbsolutePath());
	Files.move(source, source.resolveSibling(mod+".list"));
	}
	entries.set(choice, mod);
	
	
	
}
	public static void DeleteEntry(ArrayList<String> entries, int choice, boolean onFile) {
	//Delete file if working on storage/delete entry.
	
	if(onFile) {
		File f = new File(entries.get(choice)+".list");		
		System.out.println("Deleting table " + choice + ": " + entries.get(choice));
		System.out.println("Result: " + f.delete());
	}
	entries.remove(choice);
	
	
}
	public static void ReadFile(ArrayList<String> entryList, String file) throws IOException {
	//Read file and append to ArrayList of choice.
	FileReader f = new FileReader(file);
	BufferedReader output = new BufferedReader(f);
	String entry;
	while((entry = output.readLine()) != null){
		entryList.add(entry);
	}
	f.close();
	
}
	public static boolean[] ReadArgs(String[] args) {
		boolean[] pargs = {false, false};
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("--help") || args[i].equals("-h")) {
				//TODO make this display the manual.
				System.exit(0);
			} else if(args[i].equals("--no-gui") || args[i].equals("-n")) {
				//Launch in text mode
				pargs[0] = true;
			} else if(args[i].equals("--verbose") || args[i].equals("-v")) {
				//Additional debug information.
				pargs[1] = true;
			}
		}
		return pargs;
	} 
}
