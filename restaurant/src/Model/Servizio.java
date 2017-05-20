package Model;

public class Servizio {
	
	int idServizio;
	String pranzoCena;
	String data;
	int tavoliTotali;
	
	
	public Servizio(int id,String praCe,String data,int tavoliTot){
		this.idServizio=id;
		this.pranzoCena=praCe;
		this.data=data;
		this.tavoliTotali=tavoliTot;
	}


	public int getIdServizio() {
		return idServizio;
	}


	public void setIdServizio(int idServizio) {
		this.idServizio = idServizio;
	}


	public String getPranzoCena() {
		return pranzoCena;
	}


	public void setPranzoCena(String pranzoCena) {
		this.pranzoCena = pranzoCena;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public int getTavoliTotali() {
		return tavoliTotali;
	}


	public void setTavoliTotali(int tavoliTotali) {
		this.tavoliTotali = tavoliTotali;
	}


	

}
