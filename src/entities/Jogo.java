package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Jogo {

	private Long id;
	private Date date;
	private List<String> numeros = new ArrayList<String>();

	public Jogo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getNumeros() {
		return numeros;
	}

	public void addNumber(String number) {
		this.numeros.add(number);
	}

	@Override
	public String toString() {
		return "Jogo [id=" + id + ", date=" + date + ", numeros=" + numeros + "]";
	}

}
