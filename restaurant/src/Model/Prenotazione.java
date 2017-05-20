package Model;


public class Prenotazione {
	
	private int idPrenotazione;
	private String nome;
	private String dataOra;
	private int numeroPosti;
	private String stato;
	private int idServizio;
	private int numeroTavolo;
	
	public Prenotazione(int id,String n, String d, int nP,String stato,int idServ,int numT){
		this.idPrenotazione=id;
		this.nome=n;
		this.dataOra=d;
		this.numeroPosti=nP;
		this.stato=stato;
		this.idServizio=idServ;
		this.numeroTavolo=numT;
	}

	public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataOra() {
		return dataOra;
	}

	public void setDataOra(String data) {
		this.dataOra = data;
	}


	public int getNumeroPosti() {
		return numeroPosti;
	}

	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(int idServizio) {
		this.idServizio = idServizio;
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}
	
	
	
	
	

}
