package com.subtitlor.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SubtitlesHandler {
	private ArrayList<String> originalSubtitles = null;
	private ArrayList<String> translatedSubtitles = null;
	private String name;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/* Read the srt file into an array */
	public SubtitlesHandler(String fileName) throws FileNotFoundException {
		originalSubtitles = new ArrayList<String>();
		translatedSubtitles = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				originalSubtitles.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SubtitlesHandler() {
		
	}
	
	public ArrayList<String> getSubtitles() {
		return originalSubtitles;
	}

	public ArrayList<String> getTranslatedSubtitles() {
		return translatedSubtitles;
	}

	public void setTranslatedSubtitles(ArrayList<String> translatedSubtitles) {
		this.translatedSubtitles = translatedSubtitles;
	}
	
	/* Save the translation into a new srt file and return its name */
	public String saveTranslation(String fileName, String filePath) throws FileNotFoundException
	{
		fileName = fileName.replace(".srt", "-new.srt");
		try {
			PrintWriter pWriter = new PrintWriter(new FileOutputStream(filePath + fileName));
			for (int i = 0; i < this.getTranslatedSubtitles().size(); i++)
			{
				pWriter.println(this.getTranslatedSubtitles().get(i));
			}
			pWriter.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
		return fileName;
	}
	
	/* Copy original subtitles to get the right numbers and timestamps */
	public void copySubtitles()
	{
		ArrayList<String> tmp = this.originalSubtitles;
		this.translatedSubtitles = tmp;
	}
	
	
}
