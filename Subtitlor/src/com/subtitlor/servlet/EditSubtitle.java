package com.subtitlor.servlet;
import javax.servlet.http.HttpServlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.subtitlor.utilities.SubtitlesHandler;

public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = getServletContext().getRealPath("WEB-INF/../");
		String fileName = request.getParameter("file");
		
		/* start a session and save the srt file name and path in it */
		HttpSession session = request.getSession();
		session.setAttribute("fileName", fileName);
		session.setAttribute("filePath", filePath);
		
		/* Create a subtitles object from the srt file */
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler(filePath + fileName);
			request.setAttribute("subtitles", subtitles.getSubtitles());
			request.getSession().setAttribute("subtitles", subtitles.getSubtitles());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = (String) request.getSession().getAttribute("filePath");
		String fileName = (String) request.getSession().getAttribute("fileName");
		
		/* Get the original subtitle array from the session */
		@SuppressWarnings("unchecked")
		ArrayList<String> subs = (ArrayList<String>) request.getSession().getAttribute("subtitles");
		
		/* Create a new subtitle object and copy the original subtitles in it */
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler(filePath + fileName);				
			subtitles.copySubtitles();
			ArrayList<String> translation = subtitles.getTranslatedSubtitles();
			
			/* If a line was modify, save it */
			for (int i = 0; i < subtitles.getTranslatedSubtitles().size(); i++) {
				if (request.getParameter("line" + Integer.toString(i)) != null && request.getParameter("line" + Integer.toString(i)) != "")
				{
					translation.set(i, request.getParameter("line" + Integer.toString(i)));
				}
			}
			
			/* Save the array in the subtitle object and into a new srt file */
			subtitles.setTranslatedSubtitles(translation);
			String newFile = subtitles.saveTranslation(fileName, filePath);
			
			request.getSession().setAttribute("newFile", newFile);
			request.setAttribute("subtitles", subs);
			request.setAttribute("translation", subtitles.getTranslatedSubtitles());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}

}
