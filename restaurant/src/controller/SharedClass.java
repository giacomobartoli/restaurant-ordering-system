package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;

import DbConnection.DBConnection;
import Model.Comanda;
import Model.Portata;
import Model.Prenotazione;
import Model.Servizio;
import Model.TavoliUniti;

import com.mysql.jdbc.Connection;

public class SharedClass {

	private static SharedClass singleton;
	private Connection connection;
	private boolean piattoInModifica;
	private Portata portata;
	private static final String DATE_FORMAT="yyyy-MM-dd";

	private boolean modificaComanda;
	private Servizio servizio;
	
	private ArrayList<TavoliUniti> tavoliDisponibili;
	private ArrayList<TavoliUniti> tavoliOccupati=new ArrayList<>();
	


	private ArrayList<Comanda> arrayOrdine=new ArrayList<>();
	
	
	private ArrayList<Comanda> ricercaComanda=new ArrayList<>();

	
	private SharedClass() { };
	
	public static synchronized SharedClass getSingleton(){
		if (singleton == null) {
			singleton = new SharedClass();
		}
		return singleton;
	}
	
	public void setup(){
		DBConnection dataSource = new DBConnection();
		 connection= (Connection) dataSource.getMsSQLConnection();
		 
		 new ServizioTable().creaTabella(this.connection);
		 new ComandaTable().creaTabella(this.connection);
		 new PrenotazioneTable().creaTabella(this.connection);
		 new RicevutaTable().creaTabella(this.connection);
		 new TavoloTable().creaTabella(this.connection);
		 new PortataTable().creaTabella(this.connection);	 
	}
	
	public void impostaServizio(){
		DateFormat temp=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String stringa=temp.format(date);
		
		
		ArrayList<Servizio>servizi=new ServizioTable().cercaPerData(stringa, SharedClass.getSingleton().getConnection());
		this.servizio= servizi.get(servizi.size()-1);
		this.tavoliDisponibili=new ArrayList<>();
		
		ArrayList<Prenotazione>prenotazioni=new PrenotazioneTable().cercaPerDataInAttesa(stringa, this.connection);
		
		prenotazioni=this.prenotazioniPerServizio(prenotazioni, stringa);
		
		int nTavolo=0;
		
		for(Prenotazione p:prenotazioni){
			
			new PrenotazioneTable().prenotazioneInServizio(this.servizio,p, this.connection);
			
			int numeroTavoli=this.calcoloTavoliPerPrenotazione(p);
			ArrayList<Integer> arr=new ArrayList<>();
			
			int numeroTemp=nTavolo+1;
			
			p.setNumeroTavolo(numeroTemp);
			new PrenotazioneTable().tavoloInPrenotazione(p, SharedClass.getSingleton().getConnection());
			
			nTavolo++;
			for(int i=0;i<(numeroTavoli-1);i++){

				arr.add(nTavolo+1);
				nTavolo++;
			}
			
			TavoliUniti tav=new TavoliUniti(numeroTemp,arr,true);
			tav.setNomePrenotazione(p.getNome());
			this.tavoliDisponibili.add(tav);
			
			
			
			
		}
		
		
		for(int i=nTavolo;i<servizio.getTavoliTotali();i++){
			this.tavoliDisponibili.add(new TavoliUniti(i+1,new ArrayList<Integer>(),false));
		}
		
		
	}
	
	
	public void aggiornaPrenotazione(){
		this.tavoliDisponibili=null;

		DateFormat temp=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String stringa=temp.format(date);
		
		
		
		this.tavoliDisponibili=new ArrayList<>();
		
		ArrayList<Prenotazione>prenotazioni=new PrenotazioneTable().cercaPerDataInAttesa(stringa, this.connection);
		
		prenotazioni=this.prenotazioniPerServizio(prenotazioni, stringa);
		
		int nTavolo=0;
		
		for(Prenotazione p:prenotazioni){
			
			new PrenotazioneTable().prenotazioneInServizio(this.servizio,p, this.connection);


			int numeroTavoli=this.calcoloTavoliPerPrenotazione(p);

			ArrayList<Integer> arr=new ArrayList<>();
			
			int numeroTemp=nTavolo+1;
			
			p.setNumeroTavolo(numeroTemp);
			new PrenotazioneTable().tavoloInPrenotazione(p, SharedClass.getSingleton().getConnection());
			

			nTavolo++;
			
			
			for(int i=0;i<(numeroTavoli-1);i++){
				System.out.println("tavolo aggiunto numero "+i);
				arr.add(nTavolo+1);
				nTavolo++;
			}
			
			TavoliUniti tav=new TavoliUniti(numeroTemp,arr,true);
			tav.setNomePrenotazione(p.getNome());
			this.tavoliDisponibili.add(tav);
			
			
			
			
		}
		
		
		for(int i=nTavolo;i<servizio.getTavoliTotali();i++){
			this.tavoliDisponibili.add(new TavoliUniti(i+1,new ArrayList<Integer>(),false));
		}
		
	}
	
	private ArrayList<Prenotazione> prenotazioniPerServizio(ArrayList<Prenotazione>pre,String data){
		
		String s="15:00:00";
		String dataDiConfronto=data+" "+s;
		
		//confronta la stringa per capire se la prenotazione va aggiunta a pranzo o cena
		
		
		
		ArrayList<Prenotazione> temp=new ArrayList<>();
		
		String pranzoCena=servizio.getPranzoCena();
		
		for(Prenotazione p:pre){
			
			if(pranzoCena.equals("Cena") && p.getDataOra().compareTo(dataDiConfronto) > 0){
				System.out.println("CENA");
				temp.add(p);
			}
			if(pranzoCena.equals("Pranzo") && p.getDataOra().compareTo(dataDiConfronto) <= 0){
				temp.add(p);
			}
		
		}
		
		
		return temp;
	}
	
	
	private int calcoloTavoliPerPrenotazione(Prenotazione p){
		
		int temp=(p.getNumeroPosti()-2)/2;

		if(p.getNumeroPosti()%2!=0){
			temp+=1;
		}
		
		
		
		return temp;
	}
	
	public Connection getConnection(){
		return this.connection;
	}

	public boolean isPiattoInModifica() {
		return piattoInModifica;
	}

	public void setPiattoInModifica(boolean piattoInModifica) {
		this.piattoInModifica = piattoInModifica;
	}

	public Portata getPortata() {
		return portata;
	}

	public void setPortata(Portata portata) {
		this.portata = portata;
	}
	
	public void setArrayOrdine(ArrayList<Comanda> arrayOrdine) {
		this.arrayOrdine = arrayOrdine;
	}

	public ArrayList<Comanda> getArrayOrdine() {
		return arrayOrdine;
	}

	public void aggiornaPortataAllIndice(int i,Comanda c){
		this.arrayOrdine.set(i, c);
	}
	
	public void aggiungiAordine(Comanda c){
		this.arrayOrdine.add(c);
	}
	
	public Comanda dammiComandaAllIndice(int i){
		return this.arrayOrdine.get(i);
	}
	public Comanda dammiLulitmoOrdine(){
		return this.arrayOrdine.get(this.arrayOrdine.size()-1);
	}
	
	public void svuotaArrayOrdine(){
		this.arrayOrdine.clear();
	}
	
	
	public void setRicercaComanda(ArrayList<Comanda> ricercaComanda) {
		this.ricercaComanda = ricercaComanda;
	}

	public ArrayList<Comanda> getRicercaComanda() {
		return ricercaComanda;
	}
	
	public boolean isModificaComanda() {
		return modificaComanda;
	}

	public void setModificaComanda(boolean modificaComanda) {
		this.modificaComanda = modificaComanda;
	}

	
	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	
	
	public ArrayList<TavoliUniti> getTavoliDisponibili() {
		return tavoliDisponibili;
	}

	public void setTavoliDisponibili(ArrayList<TavoliUniti> tavoliDisponibili) {
		this.tavoliDisponibili = tavoliDisponibili;
	}
	
	public void aggiungiTavoloDisponibile(TavoliUniti tavolo){
		this.tavoliDisponibili.add(tavolo);
	}
	
	
	public void impostaTavoliOccupati(int tavolo,int numeroTavoli){

		boolean prenotato=false;
		TavoliUniti t=null;
		
		for(TavoliUniti i:this.tavoliDisponibili){


			if(i.getNumeroTavolo()==tavolo){

				if(i.isPrenotato()){
					prenotato=true;
				}
				t=i;
				
				this.tavoliDisponibili.remove(i);
				break;
			}

		}

		if(!prenotato){

			ArrayList<Integer>arrTemp=new ArrayList<>();





			for(int i=0;i<this.tavoliDisponibili.size() && i<numeroTavoli;i++){
				arrTemp.add(this.tavoliDisponibili.get(i).getNumeroTavolo());

			}



			for(int i=0;i<numeroTavoli;i++){

				this.tavoliDisponibili.remove(0);
			}
			
			this.aggiungiOccupato(new TavoliUniti(tavolo, arrTemp,false));


		}else{
			this.aggiungiOccupato(t);
		}


	}
	
	

	public ArrayList<TavoliUniti> getTavoliOccupati() {
		return this.tavoliOccupati;
	}

	public void setTavoliOccupati(ArrayList<TavoliUniti> tavoliIndisponibili) {
		this.tavoliOccupati = tavoliIndisponibili;
	}
	
	
	private void aggiungiOccupato(TavoliUniti t){
		this.tavoliOccupati.add(t);
	}
	
	public void liberaTavoli(int numeroTavolo){
		TavoliUniti tavolo=null;
		
		int index=0;
		
		for(TavoliUniti t:this.tavoliOccupati){
			if(t.getNumeroTavolo()==numeroTavolo){
				tavolo=t;
				this.tavoliOccupati.remove(index);
				break;
			}
			index++;
		}
		
		this.tavoliDisponibili.add(new TavoliUniti(tavolo.getNumeroTavolo(),new ArrayList<Integer>(),false));
		
		for(Integer i: tavolo.getTavoliUniti()){
			this.tavoliDisponibili.add(new TavoliUniti(i,new ArrayList<Integer>(),false));
		}
		
		
	}
	
	
	public void liberaTavoloDisdetto(int numeroTavolo){
		
		int index=0;
		TavoliUniti t=null;
		
		for(TavoliUniti tavolo:this.tavoliDisponibili){
			if(tavolo.getNumeroTavolo()==numeroTavolo){
				
				t=this.tavoliDisponibili.get(index);
				
				
				this.tavoliDisponibili.remove(index);
				
				
				this.tavoliDisponibili.add(new TavoliUniti(numeroTavolo,new ArrayList<Integer>(),false));
				break;
			}
			
			index++;
		}
		
		for(Integer i: t.getTavoliUniti()){
			this.tavoliDisponibili.add(new TavoliUniti(i,new ArrayList<Integer>(),false));
			
		}
		
	
		
		
		
	}
	

}
