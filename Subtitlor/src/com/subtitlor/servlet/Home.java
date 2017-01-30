package com.subtitlor.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.daoUser;

public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int BUFFER_SIZE = 10240;
	private daoUser daoUser;
	
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.daoUser = daoFactory.getDaoUser();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Set the file attribute to empty so that the page doesn't display a null object */
		request.setAttribute("file", "");
		
		/* Send the list of subtitles availables */
		try {
            request.setAttribute("srtFiles", daoUser.list("original"));
        }
        catch (DaoException e) {
            request.setAttribute("erreur", e.getMessage());
        }
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String SAVE_PATH = getServletContext().getRealPath("WEB-INF/../");
		Subtitle sub = new Subtitle();
		
		/* Open, read and save the srt file */
		Part part = request.getPart("file");
		String fileName = getFileName(part);
		
		if (fileName != null && !fileName.isEmpty())
		{
			String fieldName = part.getName();
			// Fixing IE bug
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
			
			/* Add the file name to the database */
			sub.setName(fileName);
			try {
				daoUser.add(sub, "original");
				request.setAttribute("srtFiles", daoUser.list("original"));
			} catch (DaoException e1) {
				request.setAttribute("error", e1.getMessage());
			}
			
			try {
				writeFile(part, fileName, SAVE_PATH);
				request.setAttribute(fieldName, fileName);
			} catch (IOException e) {
				// TODO: handle exception
			}	
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
	
	private void writeFile(Part part, String fileName, String filePath) throws IOException {
		BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(part.getInputStream(), BUFFER_SIZE);
            output = new BufferedOutputStream(new FileOutputStream(new File(filePath + fileName)), BUFFER_SIZE);

            byte[] tampon = new byte[BUFFER_SIZE];
            int longueur;
            while ((longueur = input.read(tampon)) > 0) {
                output.write(tampon, 0, longueur);
            }
        } finally {
            try {
                output.close();
            } catch (IOException ignore) {
            	
            }
            try {
                input.close();
            } catch (IOException ignore) {
            	
            }
        }
	}
	
	private static String getFileName(Part part)
	{
		for (String contentDisposition : part.getHeader("content-disposition").split(";"))
		{
			if (contentDisposition.trim().startsWith("filename"))
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
		}
		return null;
	}

}
