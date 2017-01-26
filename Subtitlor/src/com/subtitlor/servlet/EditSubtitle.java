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
		HttpSession session = request.getSession();
		session.setAttribute("fileName", fileName);
		session.setAttribute("filePath", filePath);
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
		
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler(filePath + fileName);	
			ArrayList<String> subs = (ArrayList<String>) request.getSession().getAttribute("subtitles");
			subtitles.copySubtitles();
			ArrayList<String> translation = subtitles.getTranslatedSubtitles();
						
			for (int i = 0; i < subtitles.getTranslatedSubtitles().size(); i++) {
				if (request.getParameter("line" + Integer.toString(i)) != null && request.getParameter("line" + Integer.toString(i)) != "")
				{
					translation.set(i, request.getParameter("line" + Integer.toString(i)));
				}
			}
			subtitles.setTranslatedSubtitles(translation);
			subtitles.saveTranslation(fileName, filePath);
			request.setAttribute("subtitles", subs);
			request.setAttribute("translation", subtitles.getTranslatedSubtitles());
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}

}
