package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import Model.Comanda;
import Model.Portata;
import controller.ComandaTable;
import controller.PortataTable;
import controller.SharedClass;



public class MainPanel extends AbstractPanel implements ActionListener {

	
	
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBox;
	final private JButton loginButton;

	
	
	public MainPanel(final String panelName,final MainFrame mainFrame){

		//pannello del login
		super(panelName,mainFrame);
		
		mainFrame.setTitleText("Ristorante");

		this.setFrameAttributes();
		
	
		
		this.comboBox=new JComboBox<String>(new String[]{"Sala","Cucina","Bar","Cassa","Prenotazione","Impostazioni"});
		this.comboBox.setBounds(45, 20, 140, 30);
		
		this.add(this.comboBox);

		loginButton = new JButton("Entra");
		loginButton.setBounds(215, 20, 140, 30);
		this.add(loginButton);
		this.mainFrame.getRootPane().setDefaultButton(loginButton);
		
		
		loginButton.addActionListener(this);
	}
	

	
	
	

	@Override
	public void actionPerformed(final ActionEvent e) {
	
		int indice=	this.comboBox.getSelectedIndex();
		
		this.caricaPannello(indice);
		
			
	}
	
	
	@Override
	public final void setFrameAttributes() {
		this.mainFrame.setJMenuBar(null);
		this.mainFrame.setSize(400,100);
		
	}
	
	
	
	private void caricaPannello(int i){
		switch(i){

		case 0:
		//	this.mainFrame.removePanel("sala");
			new DialogSala(this.mainFrame);
			break;
		case 1:
			
			if(this.controlloCucinaBar("cucina")){
				this.mainFrame.removePanel("cucina");

			}else{
			
				new Dialog("Non ci sono comande");
			}

			break;
		case 2:
			if(this.controlloCucinaBar("bar")){
				this.mainFrame.removePanel("bar");

			}else{
				new Dialog("Non ci sono comande");
			}
			
			

			break;
		case 3:
			this.mainFrame.removePanel("cassa");
			break;
		case 4:
			this.mainFrame.removePanel("prenotazione");
			break;
		case 5:
			new DialogImpostazioni(this.mainFrame);
			//this.mainFrame.removePanel("impostazioni");
			break;
		
		}
	}


	
	private boolean controlloCucinaBar(String s){
		
		ArrayList<Comanda> listaComande=new ComandaTable().caricaComande(SharedClass.getSingleton().getConnection()) ;
		ArrayList<Comanda> temp=new ArrayList<>();

		if(listaComande==null){
			return false;
		}
		
		for(Comanda c:listaComande){
			
			Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
			if(s.equals("cucina")){
				if(!p.getTipo().equals("Bevanda")){
				temp.add(c);
				}
			}
			
			if(s.equals("bar")){
				if(p.getTipo().equals("Bevanda")){
					temp.add(c);
					}
			}
				
	}
		
		if(temp.size()!=0){
			return true;
		}
			
		return false;
		
		
	}

}
