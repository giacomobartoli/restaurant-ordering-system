package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Comanda;
import Model.Portata;
import Model.Prenotazione;
import Model.TavoliUniti;
import Model.Tavolo;
import controller.ComandaTable;
import controller.PortataTable;
import controller.PrenotazioneTable;
import controller.SharedClass;
import controller.TavoloTable;


public class FrameComanda extends AbstractPanel implements ActionListener,ListSelectionListener {

	
	private static final long serialVersionUID = -5737139130391994172L;

	private JList<String> list;
	private JList<String> list2;

	private DefaultListModel<String>model=new DefaultListModel<>();
	private DefaultListModel<String>model2=new DefaultListModel<>();
	private ArrayList<Portata> arrayPortate;
	private JTextField textfieldCoperti;
	private JTextField textFieldTavoliUsati;

	private JComboBox<String> combo;
	private boolean inModifica;
	private JComboBox<String>  comboTavoli;
	private JTextField textFieldTavolo;
	private int numeroTavolo;
	private JButton buttonConferma;
	
	
	
	public FrameComanda(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);
		
		
		this.setLayout(null);
		this.setFrameAttributes();
		
		
		
		
		JLabel labelTavolo=new JLabel("Num.tavolo");
		labelTavolo.setBounds(270,10,80,30);
		this.add(labelTavolo);

		
		
		JLabel labelCoperti=new JLabel("Numero coperti");
		labelCoperti.setBounds(270,50,100,30);
		this.add(labelCoperti);
		
		 textfieldCoperti=new JTextField();
		textfieldCoperti.setColumns(2);
		textfieldCoperti.setBounds(380,50,30,30);
		this.add(textfieldCoperti);
		
		JLabel labelTavoliUsati=new JLabel("Tavoli usati");
		labelTavoliUsati.setBounds(270,90,100,30);
		this.add(labelTavoliUsati);
		
		textFieldTavoliUsati=new JTextField();
		textFieldTavoliUsati.setColumns(2);
		textFieldTavoliUsati.setBounds(380,90,30,30);
		this.add(textFieldTavoliUsati);
		
		final JButton buttonAdd=new JButton("+");
		buttonAdd.setBounds(270, 180, 160, 30);
		this.add(buttonAdd);
		buttonAdd.setEnabled(false);
		
		
		
		 combo=new JComboBox<>(new String[]{"Antipasto","Primo","Secondo","Contorno","Dolce","Bevanda"});
		combo.setBounds(270, 150, 160, 30);
		this.add(combo);
		
		combo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				refreshList();
				
				arrayPortate=new ComandaTable().cercaPerTipo((String)combo.getSelectedItem(), SharedClass.getSingleton().getConnection());
				riempiLista(arrayPortate);
				
				buttonAdd.setEnabled(false);
			
			}
			
		});
		
		
	
		
		buttonAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				for(Comanda c:SharedClass.getSingleton().getArrayOrdine()){
					Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
				

					String temp=p.getNome()+" "+p.getDettaglio();
					if(temp.equals(list.getSelectedValue())){
						new Dialog("Piatto gia' presente nell'ordine");
						return;
					}
				}
				
				
				inModifica=false;
				
				
				
				
			
			
				
				
				

			if(SharedClass.getSingleton().isModificaComanda()){
				new DialogComanda(arrayPortate.get(list.getSelectedIndex()),Integer.parseInt(textFieldTavolo.getText()),FrameComanda.this, false, 1,null, -1,-1);

			}else{
				int stringaParsata=sottoStringa();
				new DialogComanda(arrayPortate.get(list.getSelectedIndex()),stringaParsata,FrameComanda.this, false, 1,null, -1,-1);

			}
			
			}
				
			
		});
		
		
		
		 buttonConferma=new JButton("Conferma");
		buttonConferma.setBounds(270,210,160,30);
		buttonConferma.addActionListener(this);
		buttonConferma.setEnabled(false);
		this.add(buttonConferma);
		
		
		list = new JList<>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				buttonAdd.setEnabled(true);

			}
			
		});
		
		
		
		
		list2=new JList<>(model2);
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list2.addListSelectionListener(this);

		
		 JScrollPane scroll=new JScrollPane(list);
		 scroll.setBounds(10,10,250,230);

		 JScrollPane scroll2=new JScrollPane(list2);
		 scroll2.setBounds(440,10,250,230);

		 
		this.add(scroll);
		this.add(scroll2);  
		

		this.setVisible(true);
		
		
		
		
		if(SharedClass.getSingleton().isModificaComanda()){
			ArrayList<Comanda>arrayTemp=SharedClass.getSingleton().getArrayOrdine();
			
			Tavolo tavolo=new TavoloTable().cercaTavolo(arrayTemp.get(0).getTavolo(), SharedClass.getSingleton().getConnection());
			textfieldCoperti.setText(""+tavolo.getNumeroCoperti());
			
			model2.removeAllElements();
			for(Comanda c:arrayTemp){
				Portata p= new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
				model2.addElement(p.getNome()+"  "+p.getDettaglio()+"  x "+c.getQuantita());
			}
			
			this.textFieldTavoliUsati.setText(""+tavolo.getTavoliUsati());
			this.textFieldTavoliUsati.setEnabled(false);
			this.textFieldTavolo=new JTextField();
			this.textFieldTavolo.setText(""+tavolo.getNumeroTavolo());
			this.textFieldTavolo.setBounds(380, 10, 30, 30);
			this.add(textFieldTavolo);
			this.textFieldTavolo.setEnabled(false);			
			
		}else{
			 comboTavoli=new JComboBox<>(new String[]{"-"});
			 ArrayList<TavoliUniti> tavoliTemp=SharedClass.getSingleton().getTavoliDisponibili();
			   
			for(TavoliUniti i : tavoliTemp){
				if(i.isPrenotato()){
					Prenotazione pren=new PrenotazioneTable().cercaPrenotazioniPerTavolo(i.getNumeroTavolo(), SharedClass.getSingleton().getConnection());
					comboTavoli.addItem(i.getNumeroTavolo()+"   x"+pren.getNumeroPosti()+"  "+i.getNomePrenotazione()+" "+(1+i.getTavoliUniti().size())+" tavoli usati");

				}else{
					comboTavoli.addItem(""+i.getNumeroTavolo());

				}
			}
			   
			   
			comboTavoli.setBounds(370,10,70,30);
			((JLabel)comboTavoli.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

			this.add(comboTavoli);
		}
		
	}




	@Override
	public void setFrameAttributes() {
		// TODO Auto-generated method stub
		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());
		this.mainFrame.setSize(700,300);
	}




	@Override
	public void valueChanged(ListSelectionEvent e) {

		
		if(!e.getValueIsAdjusting()){
			
			if(!model2.isEmpty()){
			
				this.inModifica=true;
				Comanda c= SharedClass.getSingleton().dammiComandaAllIndice(list2.getSelectedIndex());
				Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
				if(!SharedClass.getSingleton().isModificaComanda()){
					new DialogComanda(p,Integer.parseInt((String)comboTavoli.getSelectedItem()),FrameComanda.this, true,c.getQuantita(),c.getNote(),c.getCodicePortata(),list2.getSelectedIndex());
				}else{
					new DialogComanda(p,Integer.parseInt(textFieldTavolo.getText()),FrameComanda.this, true,c.getQuantita(),c.getNote(),c.getCodicePortata(),list2.getSelectedIndex());

				}
				}
		

			
		}

	}




	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			Integer.parseInt(textFieldTavoliUsati.getText());

			int i=Integer.parseInt(textfieldCoperti.getText());

		}catch(NumberFormatException e1){
			new Dialog("Dati inseriti non corretti!");
			return;

		}


			ArrayList<Comanda>modificati=new ArrayList<>();
			ArrayList<Comanda>nonModificati=new ArrayList<>();

			
		

				for(Comanda c:SharedClass.getSingleton().getArrayOrdine()){
					

					if(!SharedClass.getSingleton().isModificaComanda()){

						new ComandaTable().aggiungiComanda(c, SharedClass.getSingleton().getConnection());
						nonModificati.add(c);
					


					}else{

						if(c.isModificato()){
							new ComandaTable().aggiornaComanda(c, SharedClass.getSingleton().getConnection());
							modificati.add(c);
						}else{
							new ComandaTable().aggiungiComanda(c, SharedClass.getSingleton().getConnection());
							nonModificati.add(c);
						}
						
					
					}

				}
		

		

		if(!SharedClass.getSingleton().isModificaComanda()){
			

			int stringaParsata=sottoStringa();
			
			
			new TavoloTable().aggiungiTavolo(new Tavolo(stringaParsata,Integer.parseInt(textfieldCoperti.getText()),Integer.parseInt(textFieldTavoliUsati.getText())), SharedClass.getSingleton().getConnection());
			textfieldCoperti.setText("");
			combo.setSelectedIndex(0);

			model2.removeAllElements();

		}else{



			Tavolo t=new TavoloTable().cercaTavolo(Integer.parseInt(textFieldTavolo.getText()), SharedClass.getSingleton().getConnection());
			t.setNumeroCoperti(Integer.parseInt(textfieldCoperti.getText()));
			new TavoloTable().aggiornatTavolo(t, SharedClass.getSingleton().getConnection());



			FrameComanda.this.mainFrame.removePanel("main");
		}

		for(Comanda c:SharedClass.getSingleton().getArrayOrdine()){
			c.setModificato(false);

		}
		
		
		
		SharedClass.getSingleton().svuotaArrayOrdine();	
		
		if(!SharedClass.getSingleton().isModificaComanda()){
			
			int stringaParsata =sottoStringa();
			
			SharedClass.getSingleton().impostaTavoliOccupati(stringaParsata,Integer.parseInt((String)textFieldTavoliUsati.getText())-1);

		
			textfieldCoperti.setText("");
			textFieldTavoliUsati.setText("");
			

			comboTavoli.removeAllItems();
			comboTavoli.addItem("-");
			
			 ArrayList<TavoliUniti> tavoliTemp=SharedClass.getSingleton().getTavoliDisponibili();
			   
			 for(TavoliUniti i : tavoliTemp){
					if(i.isPrenotato()){
						Prenotazione pren=new PrenotazioneTable().cercaPrenotazioniPerTavolo(i.getNumeroTavolo(), SharedClass.getSingleton().getConnection());
						comboTavoli.addItem(i.getNumeroTavolo()+"   x"+pren.getNumeroPosti()+"  "+i.getNomePrenotazione()+" "+(1+i.getTavoliUniti().size())+" tavoli usati");

					}else{
						comboTavoli.addItem(""+i.getNumeroTavolo());

					}
				}
				
				comboTavoli.setSelectedIndex(0);
			
		}
		
		SharedClass.getSingleton().setModificaComanda(false);
		
		
			
			Prenotazione p=new PrenotazioneTable().cercaPrenotazioniPerTavolo(numeroTavolo, SharedClass.getSingleton().getConnection());
			if(p!=null){
				new PrenotazioneTable().aggiornaStatoPrenotazione(p,"completata", SharedClass.getSingleton().getConnection());

			}
		
			
		
		

		
		new Dialog("Ordine preso correttamente");


	}
	
	
public void riempiLista(ArrayList<Portata> p){
		
		int i=0;
		
		for(Portata port:p){
			
			this.model.addElement(port.getNome()+" "+port.getDettaglio());
			i++;
			
		}
	}
	
	private void refreshList(){
		if(!model.isEmpty())
			this.model.removeAllElements();
		
		
	}
	


	private int sottoStringa(){
		String s=(String)comboTavoli.getSelectedItem();
		int x=0;
		try{
			s=s.substring(0,2);
		}catch(Exception e){
			s=s.substring(0, 1);
		}
		
		try{
			x=Integer.parseInt(s);
			
		}catch(Exception e){

			s=s.substring(0,1);

			x=	Integer.parseInt(s);

		}
	
		this.numeroTavolo=x;
		
		return x;

		
		
	}
	
	
	
	public void aggiornaListaOrdine(Comanda c,Portata p,int index){
		
		if(!buttonConferma.isEnabled())
			buttonConferma.setEnabled(true);

		
		if(!this.inModifica){

			model2.addElement(p.getNome()+"  "+p.getDettaglio()+"  x "+c.getQuantita());
		}else{
			
			model2.setElementAt(p.getNome()+"  "+p.getDettaglio()+"  x "+c.getQuantita(),index);
			

			this.inModifica=false;
		}
		
		
		
		
	}
}
