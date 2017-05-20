package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class DialogImpostazioni extends JDialog implements ActionListener {

	private JButton buttonAggiungi;
	private JButton buttonModificaCancella;
	private JButton buttonIncassi;
	private JButton buttonPrenotazioni;


	private MainFrame frame;
	
	public DialogImpostazioni(MainFrame fr){
		super();
		
		setBounds(100, 100, 230, 260);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setLayout(null);
		this.setTitle("Impostazioni");
		this.frame=fr;
		
		this.buttonAggiungi=new JButton("Aggiungi");
		this.buttonAggiungi.setBounds(40, 10, 150, 30);
		this.buttonAggiungi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DialogImpostazioni.this.frame.removePanel("nuovoPiatto");
			}
			
		});

		this.buttonModificaCancella=new JButton("Modifica/Cancella");
		this.buttonModificaCancella.setBounds(40, 60, 150, 30);
		this.buttonModificaCancella.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();


				DialogImpostazioni.this.frame.removePanel("cercaPiatto");

			}

		});

		this.buttonIncassi=new JButton("Visualizza incassi");
		this.buttonIncassi.setBounds(40, 110, 150, 30);
		this.buttonIncassi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DialogImpostazioni.this.frame.removePanel("visualizzaIncassi");

			}

		});



		this.buttonPrenotazioni=new JButton("Prenotazioni");
		this.buttonPrenotazioni.setBounds(40, 160, 150, 30);
		this.buttonPrenotazioni.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DialogImpostazioni.this.frame.removePanel("prenotazioni");
			//	new CercaFrame("prenotazioni");

			}

		});


		this.add(buttonAggiungi);		
		this.add(buttonModificaCancella);		
		this.add(buttonIncassi);
		this.add(buttonPrenotazioni);



		this.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
