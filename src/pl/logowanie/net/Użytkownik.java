package pl.logowanie.net;

import java.io.Serializable;

public class U¿ytkownik implements Serializable {

	private String login;
	private String email;
	private int id;
	private int numerTelefonu;

	public U¿ytkownik(String nm, String em, int nr, int i) {
		this.login = nm;
		this.id = i;
		this.numerTelefonu = nr;
		this.email = em;
	}

	public void setLogin(String nazwa) {
		this.login = nazwa;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNrTelefonu(int tel) {
		this.numerTelefonu = tel;
	}

	public String getUzytkownik() {
		return login;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public int getNrTel() {
		return numerTelefonu;
	}

	@Override
	public String toString() {
		return "Nazwa=" + this.login + ", Email=" + this.email + ", Telefon="
				+ this.numerTelefonu;
	}
}
