package Model;


public class Ricevuta {
	
	private int idRicevuta;
	private String data;
	private double totale;
	private int sconto;
	private int idServizio;
	
	public Ricevuta(int n,String d,double t,int s,int service){
		this.data=d;
		this.idRicevuta=n;
		this.totale=t;
		this.sconto=s;
		this.idServizio=service;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getIdRicevuta() {
		return idRicevuta;
	}

	public void setIdRicevuta(int numero) {
		this.idRicevuta = numero;
	}


	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public int getSconto() {
		return sconto;
	}

	public void setSconto(int sconto) {
		this.sconto = sconto;
	}

	public int getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(int idServizio) {
		this.idServizio = idServizio;
	}
	
	

}
