package com.subtitlor.servlet;
import javax.servlet.http.HttpServlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.daoUser;
import com.subtitlor.utilities.SubtitlesHandler;

public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private daoUser daoUser;

	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.daoUser = daoFactory.getDaoUser();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = getServletContext().getRealPath("WEB-INF/../");		
		String fileName = request.getParameter("file");

		/* start a session and save the srt file name and path in it */
		HttpSession session = request.getSession();
		session.setAttribute("fileName", fileName);
		session.setAttribute("filePath", filePath);

		/* Send the translation if available */
		try {
			List<SubtitlesHandler> translatedFiles = daoUser.list("translated");
			String tmp = fileName.replace(".srt", "-new.srt");
			for (SubtitlesHandler file : translatedFiles) {
				if (file.getName().equals(tmp))
				{
					SubtitlesHandler sub = new SubtitlesHandler();
					sub.readSubtitles(filePath + file.getName());
					sub.copySubtitles();
					request.setAttribute("translation", sub.getTranslatedSubtitles());
				}
			}
		}
		catch (DaoException e) {
			System.out.println(e.getMessage());
		}

		/* Create a subtitles object from the srt file */
		try {
			SubtitlesHandler subtitles = new SubtitlesHandler();
			subtitles.readSubtitles(filePath + fileName);
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
			SubtitlesHandler subtitles = new SubtitlesHandler();
			subtitles.readSubtitles(filePath + fileName);
			subtitles.setName(fileName);
			subtitles.copySubtitles();
			ArrayList<String> translationList = subtitles.getTranslatedSubtitles();

			/* If a line was modified, save it */
			for (int i = 0; i < subtitles.getTranslatedSubtitles().size(); i++) {
				if (request.getParameter("line" + Integer.toString(i)) != null && request.getParameter("line" + Integer.toString(i)) != "")
				{
					translationList.set(i, request.getParameter("line" + Integer.toString(i)));
				}
			}

			/* Save the array in the subtitle object and into a new srt file */
			subtitles.setTranslatedSubtitles(translationList);
			String newFile = subtitles.saveTranslation(fileName, filePath);

			/* Add the file name to the database */
			SubtitlesHandler translation = new SubtitlesHandler();
			translation.readSubtitles(filePath + newFile);
			translation.setName(newFile);
			boolean exists = false;
			try {			
				/* Send the translation if available */
				List<SubtitlesHandler> translatedFiles = daoUser.list("translated");
				String tmp = fileName.replace(".srt", "-new.srt");
				for (SubtitlesHandler file : translatedFiles) {
					if (file.getName().equals(tmp))
					{
						SubtitlesHandler sub = new SubtitlesHandler();
						sub.readSubtitles(filePath + file.getName());
						sub.copySubtitles();
						request.setAttribute("translation", sub.getTranslatedSubtitles());
						exists = true;
						break;
					}
				}
				if (!(exists))
					daoUser.add(translation, "translated");
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}

			request.getSession().setAttribute("newFile", newFile);
			request.setAttribute("subtitles", subs);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/editsubtitles.jsp").forward(request, response);
	}

}
