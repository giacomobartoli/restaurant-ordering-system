package Model;

public class Comanda {

	private int idOrdine;
	private int codicePortata;
	private int tavolo;
	private int quantita;
	private String note;
	private boolean modificato;

	
	public Comanda(int idOrdine,int np,int t,int q,String n,boolean mod){
		this.codicePortata=np;
		this.tavolo=t;
		this.quantita=q;
		this.note=n;
		this.idOrdine=idOrdine;
		this.modificato=mod;
	}
	
	
	
	public boolean isModificato() {
		return modificato;
	}


	public void setModificato(boolean modificato) {
		this.modificato = modificato;
	}




	public void setIdOrdine(int i){
		this.idOrdine=i;
	}
	
	
	public int getIdOrdine() {
		return idOrdine;
	}


	public int getCodicePortata() {
		return codicePortata;
	}

	public void setNomePortata(int codicePortata) {
		this.codicePortata = codicePortata;
	}

	public int getTavolo() {
		return tavolo;
	}

	public void setTavolo(int tavolo) {
		this.tavolo = tavolo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	
}
