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
	
	public ArrayList<String> getSubtitles() {
		return originalSubtitles;
	}

	public ArrayList<String> getTranslatedSubtitles() {
		return translatedSubtitles;
	}

	public void setTranslatedSubtitles(ArrayList<String> translatedSubtitles) {
		this.translatedSubtitles = translatedSubtitles;
	}
	
	public void saveTranslation(String fileName, String filePath) throws FileNotFoundException
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
		
	}
	
	public void copySubtitles()
	{
		ArrayList<String> tmp = this.originalSubtitles;
		this.translatedSubtitles = tmp;
	}
	
	
}
