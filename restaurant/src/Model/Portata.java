package Model;

public class Portata {
	
	private int idPortata;
	private String nome;
	private String tipo;
	private float prezzo;
	private String dettaglio;
	
	public Portata(int c,String n,String t,float p,String d){
		this.idPortata=c;
		this.nome=n;
		this.tipo=t;
		this.prezzo=p;
		this.dettaglio=d;
	}
	
	public void setIdPortata(int c){
		this.idPortata=c;
	}
	
	public int getIdPortata(){
		return this.idPortata;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public String getDettaglio() {
		return dettaglio;
	}
	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}
	
	

}
