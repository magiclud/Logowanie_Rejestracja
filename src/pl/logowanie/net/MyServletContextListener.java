package pl.logowanie.net;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//servletContextListener umozliwa m inasluchwianie zdarzeñ kontekstu
public class MyServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO kod zamykajacy polaczenie z baza danych 
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO kod inicjalizujacy polaczenie z baza danych i umieszczajacy odpowiedni obiekt w atrybucei kontekstu
	}

}
