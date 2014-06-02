package pl.logowanie.net;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class ReCaptchaValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReCaptchaValidation() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String remoteAddr = request.getRemoteAddr();
		// out.println(remoteAddr);
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		// out.println(reCaptcha);
		reCaptcha.setPrivateKey("6Lfge_QSAAAAAM2UICmv7mb_8eNd7V4yDwetUSgC");

		String challenge = request.getParameter("recaptcha_challenge_field");
		// out.println(challenge);
		String uresponse = request.getParameter("recaptcha_response_field");
		// out.println(uresponse);
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				challenge, uresponse);

		if (reCaptchaResponse.isValid()) {
			System.out.print("Answer was entered correctly!");
		} else {
			System.out.print("Answer is wrong");

		}
	}

}
