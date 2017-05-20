package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Portata;
import Model.Prenotazione;
import Model.Ricevuta;
import Model.Servizio;
import controller.PrenotazioneTable;
import controller.RicevutaTable;
import controller.ServizioTable;
import controller.SharedClass;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class CercaFrame extends AbstractPanel implements ActionListener,ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String>model=new DefaultListModel<>();
	private JLabel labelIncassi;
	
	private JDatePanelImpl datePanel;
	private UtilDateModel modelDate;
	//private Date selectedDate;
	//private static final String DATE_FORMAT="dd/MM/yyyy";
	private MainFrame frame;
	private Date selectedDate;
	private ArrayList<Prenotazione> prenotazioni;
	private ArrayList<Ricevuta> ricevute;
	private JLabel label;
	private static final String DATE_FORMAT="yyyy-MM-dd";
	private String tipoPannello;
	private JComboBox<String> combo;
	

	private JButton buttonCancella;

	public CercaFrame(String panelName, MainFrame mainFrame,String tipo) {
		super(panelName, mainFrame);
		
		
         this.setLayout(null);
         this.frame=mainFrame;
         
         this.setFrameAttributes();
         this.tipoPannello=tipo;
         
         
        modelDate = new UtilDateModel();
 		modelDate.setValue(new Date());
 		modelDate.setSelected(true);
 		datePanel = new JDatePanelImpl(modelDate);
 		//datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		
 		datePanel.addActionListener(this);	
 		datePanel.setBounds(6, 6, 218, 200);
 		this.add(datePanel);
 		
 		//selectedDate=(Date)modelDate.getValue();
 		
 		
 		
 		
         
         if(tipoPannello.equals("incassi")){
             this.frame.setTitle("Incassi");

        	 labelIncassi=new JLabel("");
        	 this.labelIncassi.setBounds(6, 280, 218, 20);
        	 this.labelIncassi.setHorizontalAlignment(SwingConstants.CENTER);
        	 this.add(labelIncassi);
        	 label=new JLabel("");
        	 label.setHorizontalAlignment(SwingConstants.CENTER);
	        label.setBounds(6, 260, 218, 20);
	       	this.add(label);
	       	
	       	this.combo=new JComboBox<>(new String[]{"totale","pranzo","cena"});
	       	this.combo.setBounds(6, 220, 218, 20);
	       	this.add(combo);
        	 
         }else{
             this.frame.setTitle("Prenotazioni");

        	 list = new JList<>(model);
      		list.addListSelectionListener(this);
      		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      		
      		 JScrollPane scroll=new JScrollPane(list);
     		 scroll.setBounds(6, 250, 218, 110);
     		 
     		 this.buttonCancella=new JButton("Disdetta");
     		 this.buttonCancella.setBounds(66, 365, 100, 30);
     		 this.buttonCancella.setEnabled(false);
     		 this.add(buttonCancella);
     		 
     		this.combo=new JComboBox<>(new String[]{"pranzo","cena"});
     		((JLabel)this.combo.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
	       	this.combo.setBounds(6, 220, 218, 20);
	       	this.add(combo);
	       	
	       	
     		 
     		 this.buttonCancella.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					Prenotazione pren=prenotazioni.get(list.getSelectedIndex());
					pren.setStato("disdetta");
					new PrenotazioneTable().aggiornaStatoPrenotazione(prenotazioni.get(list.getSelectedIndex()),"disdetta", SharedClass.getSingleton().getConnection());
					
					model.remove(list.getSelectedIndex());
					System.out.println("get numero tavolo  "+pren.getNumeroTavolo());

					if(pren.getNumeroTavolo()!=0){
						System.out.println("TAVOLO DISDETTO OGGI");
						SharedClass.getSingleton().liberaTavoloDisdetto(pren.getNumeroTavolo());
					}
					
					
					
					
				}
     			 
     		 });
     		 this.add(scroll);
        	 
        	 
         }
         
  
         this.setVisible(true);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		this.buttonCancella.setEnabled(true);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		 if(tipoPannello.equals("incassi")){
			 SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,Locale.ITALIAN);
				selectedDate=(Date)modelDate.getValue();
			final SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyy",Locale.ITALIAN);
	
				
				String dataOra=formatter.format(selectedDate);
	
				if(combo.getSelectedItem().equals("totale")){

					ricevute=new RicevutaTable().cercaPerData(dataOra, SharedClass.getSingleton().getConnection());
				}else if(combo.getSelectedItem().equals("pranzo")){
					
					ArrayList<Servizio> arrService=new ServizioTable().cercaPerData(dataOra, SharedClass.getSingleton().getConnection());
					Servizio s=null;
					
					for(Servizio servizio:arrService){
						if(servizio.getPranzoCena().equals("Pranzo")){
							ricevute=new RicevutaTable().cercaPerServizio(servizio.getIdServizio(), SharedClass.getSingleton().getConnection());
							break;
						}
					}
					
				
					
					

				}else{
					ArrayList<Servizio> arrService=new ServizioTable().cercaPerData(dataOra, SharedClass.getSingleton().getConnection());
					
					for(Servizio servizio:arrService){
						if(servizio.getPranzoCena().equals("Cena")){
							System.out.println("cena");

							ricevute=new RicevutaTable().cercaPerServizio(servizio.getIdServizio(), SharedClass.getSingleton().getConnection());
							break;
						}
					}
									
				}
				
				
				
				double totale=0;
				if(ricevute!=null){
				for(Ricevuta r:ricevute){

					totale+=r.getTotale();
				}
				}
				
				label.setText("Incasso "+combo.getSelectedItem()+" al "+formatter2.format(selectedDate));
				labelIncassi.setText("\u20AC   "+totale);
				
				ricevute=null;
				
			 
		 }else{
				// TODO Auto-generated method stub
				final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				selectedDate=(Date)modelDate.getValue();
				
				
				
				refreshList();
				
				String dataOra=formatter.format(selectedDate);
								
				
				prenotazioni=this.cercaPrenotazioni(dataOra);
				
				
				
				if(prenotazioni!=null){

					riempiLista(prenotazioni);
				}
		 }
		
		
		
		
	}
	
	
	
	private ArrayList<Prenotazione> cercaPrenotazioni(String data){
		String s="15:00:00";
		String dataDiConfronto=data+" "+s;
		
		//confronta la stringa per capire se la prenotazione va aggiunta a pranzo o cena
		
		
		
		ArrayList<Prenotazione> temp=new ArrayList<>();
		
		String pranzoCena=(String)combo.getSelectedItem();
		
		for(Prenotazione p:new PrenotazioneTable().cercaPerDataInAttesa(data, SharedClass.getSingleton().getConnection())){
			
			if(pranzoCena.equals("cena") && p.getDataOra().compareTo(dataDiConfronto) > 0){
				temp.add(p);
			}
			if(pranzoCena.equals("pranzo") && p.getDataOra().compareTo(dataDiConfronto) <= 0){
				temp.add(p);
			}
			

		}
		
		
		return temp;
		
	}
	
	
	
	

	@Override
	public void setFrameAttributes() {

		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());

		this.mainFrame.setSize(235,440);
	}
	
	public void riempiLista(ArrayList<Prenotazione> p){
		
		int i=0;
		
		for(Prenotazione prenotazione:p){
			
			this.model.addElement(prenotazione.getNome()+" "+prenotazione.getDataOra().substring(10,16)+"  x"+prenotazione.getNumeroPosti()+"  "+prenotazione.getStato());
			i++;
			
		}
	}
	
	private void refreshList(){
		if(!model.isEmpty())
			this.model.removeAllElements();
		
		
	}


}
