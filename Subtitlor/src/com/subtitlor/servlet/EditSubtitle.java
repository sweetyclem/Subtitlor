package com.subtitlor.servlet;
import javax.servlet.http.HttpServlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.subtitlor.utilities.SubtitlesHandler;

public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = getServletContext().getRealPath("WEB-INF/../") + request.getParameter("file");
		HttpSession session = request.getSession();
		session.setAttribute("fileName", filePath);
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
			request.setAttribute("subtitles", subtitles.getSubtitles());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = (String) request.getSession().getAttribute("fileName");
		String filePath = getServletContext().getRealPath("WEB-INF/../");
		
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler(filePath);
			request.setAttribute("subtitles", subtitles.getSubtitles());
			subtitles.copySubtitles();
			ArrayList<String> translation = subtitles.getTranslatedSubtitles();			
			for (int i = 0; i < subtitles.getSubtitles().size(); i++) {
				if (request.getParameter("line" + Integer.toString(i)) != null && request.getParameter("line" + Integer.toString(i)) != "")
					translation.set(i, request.getParameter("line" + Integer.toString(i)));
			}
			subtitles.setTranslatedSubtitles(translation);
			fileName = fileName.replace(".srt", "-new.srt");
			PrintWriter pWriter = new PrintWriter(new FileOutputStream(fileName));
			for (int i = 0; i < subtitles.getSubtitles().size(); i++)
			{
				pWriter.println(translation.get(i));
			}
			pWriter.close();
			System.out.println(filePath + fileName);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}

}
