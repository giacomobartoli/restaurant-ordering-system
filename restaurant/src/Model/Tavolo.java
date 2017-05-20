package Model;

public class Tavolo {
	
	private int numeroTavolo;
	private int numeroCoperti;
	private int tavoliUsati;
	
	public Tavolo(int nT,int nC,int tU){
		this.numeroTavolo=nT;
		this.numeroCoperti=nC;
		this.tavoliUsati=tU;
		
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	public int getNumeroCoperti() {
		return numeroCoperti;
	}

	public void setNumeroCoperti(int numeroCoperti) {
		this.numeroCoperti = numeroCoperti;
	}

	public int getTavoliUsati() {
		return tavoliUsati;
	}

	public void setTavoliUsati(int tavoliUsati) {
		this.tavoliUsati = tavoliUsati;
	}
	
	

}
