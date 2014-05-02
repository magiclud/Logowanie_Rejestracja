package pl.logowanie.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class PrzesylaniePliku extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final String UPLOAD_DIRECTORY = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\pobranePliki\\"; 
    public PrzesylaniePliku() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//process only if its multipart content
//        if(ServletFileUpload.isMultipartContent(request)){
//            try {
//                List<FileItem> multiparts = new ServletFileUpload(
//                                         new DiskFileItemFactory()).parseRequest((RequestContext) request);
//              
//                for(FileItem item : multiparts){
//                    if(!item.isFormField()){
//                        String name = new File(item.getName()).getName();
//                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
//                    }
//                }
//               //File uploaded successfully
//               request.setAttribute("message", "File Uploaded Successfully");
//            } catch (Exception ex) {
//               request.setAttribute("message", "File Upload Failed due to " + ex);
//            }          
//        }else{
//            request.setAttribute("message",
//                                 "Sorry this Servlet only handles file upload request");
//        }
//        request.getRequestDispatcher("/rezultPrzeslaniaPliku.jsp").forward(request, response);
   }
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Jestem w  service method of przesylanie pliku");
		response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    final String path = "D:\\Programy\\eclipseEE\\wokspace\\Logowanie\\pobranePliki";//request.getParameter("destination");
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);
	    System.out.println("File name "+fileName);

	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();

	    try {
	        out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        System.out.println("Nowe miejsce pliku "+ path + File.separator + fileName);
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	            System.out.println("czytam plik");
	        }
	        writer.println("New file " + fileName + " created at " + path);
	        request.setAttribute("message",  "File being uploaded to ..");
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	        request.setAttribute("message", "Problems during file upload. Error: {0}");
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	    request.getRequestDispatcher("/rezultPrzeslaniaPliku.jsp").forward(request, response);
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

}
