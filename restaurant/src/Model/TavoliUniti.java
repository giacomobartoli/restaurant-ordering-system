package Model;

import java.util.ArrayList;

public class TavoliUniti {
	
	private int numeroTavolo;
	private ArrayList<Integer>tavoliUniti=new ArrayList<>();
	private boolean prenotato;
	private String nomePrenotazione;
	
	
	
	
	public TavoliUniti(int numTavolo,ArrayList<Integer> arr,boolean pren){
			this.numeroTavolo=numTavolo;
			this.tavoliUniti=arr;
			this.prenotato=pren;
	}


	public int getNumeroTavolo() {
		return numeroTavolo;
	}


	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}


	public ArrayList<Integer> getTavoliUniti() {
		return tavoliUniti;
	}


	public void setTavoliUniti(ArrayList<Integer> tavoliUniti) {
		this.tavoliUniti = tavoliUniti;
	}


	public boolean isPrenotato() {
		return prenotato;
	}


	public void setPrenotato(boolean prenotato) {
		this.prenotato = prenotato;
	}


	public String getNomePrenotazione() {
		return nomePrenotazione;
	}


	public void setNomePrenotazione(String nomePrenotazione) {
		this.nomePrenotazione = nomePrenotazione;
	}
	
	
	
	

}
