package views;

import javax.swing.*;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String MAIN = "main";
	private MenuBar menuBar;


	public MainFrame(){
		super();


		
		this.setTitle("Ristorante");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(450,300);
		final AbstractPanel panel=new MainPanel(MAIN,this);
		this.getContentPane().add(panel);
		this.setLocationRelativeTo(null);
	
		//imposto il frame senza barra
		this.menuBar=new MenuBar(this);
		this.setJMenuBar(null);
		this.setVisible(true);
	}

	
	public void removePanel(final String panelName){
		
		this.getContentPane().removeAll();

		AbstractPanel panel=new MainPanel(MAIN,this);
		
		//a seconda della stringa passata instanzio il pannello
		if(panelName.equals(MAIN)){
			panel=new MainPanel(MAIN,this);
		}else if(panelName.equals("sala")){
			panel=new FrameComanda("sala",this);
		}else if(panelName.equals("cucina")){
			panel=new Cucina("cucina",this);
		}else if(panelName.equals("bar")){
			panel=new Bar("bar",this);
		}else if(panelName.equals("cassa")){
			panel=new Cassa("cassa",this);
		}else if(panelName.equals("prenotazione")){
			panel=new PrenotazionePanel("prenotazione",this);

		}else if(panelName.equals("nuovoPiatto")){
			panel=new NuovoPiattoFrame("nuovoPiatto",this);
		}else if(panelName.equals("cercaPiatto")){
			panel=new CercaPiattoFrame("cercaPiatto",this);
		}else if(panelName.equals("visualizzaIncassi")){
			panel=new CercaFrame("visualizzaIncassi",this,"incassi");
		}else if(panelName.equals("prenotazioni")){
			panel=new CercaFrame("visualizzaIncassi",this,"prenotazioni");

		}
		
		this.setLocationRelativeTo(null);
		this.getContentPane().add(panel);		
		this.repaint();	
		panel.updateUI();
	}

	public MenuBar getMyMenuBar(){
		return this.menuBar;
	}

	public void setTitleText(final String text){
		this.setTitle(text);
	}

	


	
}