package pl.logowanie.net;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DaneUzytkownika extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DaneUzytkownika() {
        super();
    }
  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  System.out.println("Jestem w  service method w DaneUzytkownika");

			String uzytkownik =  (String) request.getSession().getAttribute("userZalogowany");; //TODO
			System.out.println("Uzytkownik "+ uzytkownik); 
			request.setAttribute("login", uzytkownik);
			
//		    PreparedStatement updateUserInfo = (PreparedStatement) conn  
//                    .prepareStatement("insert into userInfo ( UserName, userPW) values(?,?)");  
//            //since first column is  AI, no need to include that for updates  
//            updateUserInfo.setString(1, nUser);  
//            updateUserInfo.setString(2, nPW);  
//  
//            updateUserInfo.executeUpdate();  
//            
//            lub 
//            
//            
//            String sql ="update names set first_name=?, last_name=? where id=?";
//            PrearedStatement ps = oon.prepareStatement(sql);
//            Connnection.prepareStatement();
//            ps.setString(1,"firstName");
//            ps.setString(2,"lastName");
//            ps.setId(3,1);
//            ps.executeUpdate();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
