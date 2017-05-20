package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Comanda;
import Model.Portata;
import Model.Ricevuta;
import Model.Tavolo;
import controller.ComandaTable;
import controller.PortataTable;
import controller.RicevutaTable;
import controller.SharedClass;
import controller.TavoloTable;

public class Cassa extends AbstractPanel implements ActionListener,ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String>model=new DefaultListModel<>();
	private JLabel labelDataNumero;
	private JLabel labelTotale;
	private JButton buttonSconto;
	private JTextField textFieldSconto;
	private JTextField textFieldTavolo;
	private double totale;
	
	private ArrayList<Comanda> arrayComanda=new ArrayList<>();

	public Cassa(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);
		
		this.setLayout(null);
		this.setFrameAttributes();
		this.mainFrame.setTitleText("Cassa");	
		
		list = new JList<>(model);
		list.addListSelectionListener(this);
		JScrollPane scroll=new JScrollPane(list);
		scroll.setBounds(0, 50, 500, 270);
		this.add(scroll);
	
		JLabel labelTavolo=new JLabel("N.tavolo: ");
		labelTavolo.setBounds(4, 20, 70, 20);
		this.add(labelTavolo);
		
		 textFieldTavolo=new JTextField();
		textFieldTavolo.setBounds(75, 20, 30, 20);
		this.add(textFieldTavolo);
		
		JButton buttonCerca=new JButton("Ricevuta");
		buttonCerca.setBounds(115, 15, 90, 30);
		this.add(buttonCerca);
		
	
		
		this.labelDataNumero=new JLabel("");
		this.labelDataNumero.setHorizontalAlignment(SwingConstants.RIGHT);
		this.labelDataNumero.setBounds(120, 30, 270, 16);
		this.add(this.labelDataNumero);

		this.labelTotale=new JLabel("Totale \u20AC 0.0");
		this.labelTotale.setHorizontalAlignment(SwingConstants.RIGHT);
		this.labelTotale.setBounds(255, 320,138, 30);
		this.add(this.labelTotale);
		
		
		this.buttonSconto=new JButton("Sconto ");
		this.buttonSconto.setBounds(4, 320,100, 30);
		this.add(this.buttonSconto);
		
		this.textFieldSconto=new JTextField("0");
		this.textFieldSconto.setBounds(110, 320,50, 30);
		this.add(this.textFieldSconto);

		buttonCerca.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				try{
					Integer.parseInt(textFieldTavolo.getText());

					
				}catch(IllegalArgumentException e1){
					
					new Dialog("Dati inseriti non corretti");
					return;
					
				}
				
				
				
				
				labelDataNumero.setText("");
				model.removeAllElements();
				
				// TODO Auto-generated method stub
				arrayComanda= new ComandaTable().cercaComanda(Integer.parseInt(textFieldTavolo.getText()), SharedClass.getSingleton().getConnection());
				
				if(arrayComanda==null){
					new Dialog("Comanda non trovata");
					return;
				}
				
				
				 totale=0.00;
				for(Comanda c:arrayComanda){
					Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
					model.addElement(p.getNome()+" "+p.getDettaglio()+"    x "+c.getQuantita()+"       \u20AC "+p.getPrezzo());
					totale+=(p.getPrezzo()*c.getQuantita());
				}
				Tavolo t=new TavoloTable().cercaTavolo(arrayComanda.get(0).getTavolo(), SharedClass.getSingleton().getConnection());
				double costoCoperti=t.getNumeroCoperti()*1.0;  // 1.0 euro coperto
				totale+=costoCoperti;
				
				model.addElement("Coperti   x  "+t.getNumeroCoperti()+"   \u20AC "+costoCoperti);
				
				labelTotale.setText("Totale \u20AC "+totale);
				
			}
			
		});
		
		
		JButton buttonSalva=new JButton("Stampa");
		buttonSalva.setBounds(130, 355, 140, 30);
		buttonSalva.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				try{
					Integer.parseInt(textFieldTavolo.getText());
					Integer.parseInt(textFieldSconto.getText());

					
				}catch(IllegalArgumentException e1){
					
					new Dialog("Dati inseriti non corretti");
					return;
					
				}
				
				System.out.println("passato catch");
			
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String s=dateFormat.format(date);
						
				Ricevuta r=new Ricevuta(0,s,totale,Integer.parseInt(textFieldSconto.getText()),SharedClass.getSingleton().getServizio().getIdServizio());
				new RicevutaTable().inserisci(r, SharedClass.getSingleton().getConnection());
				
				impostaLabel();
				
				for(Comanda c: arrayComanda){
					new ComandaTable().cancellaComanda(c, SharedClass.getSingleton().getConnection());
				}
				
				Tavolo t=new TavoloTable().cercaTavolo(arrayComanda.get(0).getTavolo(), SharedClass.getSingleton().getConnection());
				new TavoloTable().cancellaTavolo(t, SharedClass.getSingleton().getConnection());
				
				SharedClass.getSingleton().liberaTavoli(t.getNumeroTavolo());
				
			}
			
		});
		
		
		
		
		this.buttonSconto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try{
					Integer.parseInt(textFieldSconto.getText());

					
				}catch(IllegalArgumentException e1){
					
					new Dialog("Dati inseriti non corretti");
					return;
					
				}
				
				
				
				int sconto=Integer.parseInt(textFieldSconto.getText());
				
				if(sconto>100||sconto<0){
					new Dialog("Sconto non applicabile");
					return;
				}
				
				double totSconto=(totale/100)*sconto;
				totale-=totSconto;
				labelTotale.setText("Totale \u20AC "+(totale));

			}
			
		});
		
		
		
		
		this.add(buttonSalva);

	}

	
	public void impostaLabel(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String s=dateFormat.format(date);
		
		
		int num=new RicevutaTable().ultimaRicevuta(SharedClass.getSingleton().getConnection());
		
		this.labelDataNumero.setText("n."+num+"  del  "+s);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void setFrameAttributes() {
		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());

		this.mainFrame.setSize(400,440);
	}





	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
