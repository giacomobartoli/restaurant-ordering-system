package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import Model.Comanda;
import controller.ComandaTable;
import controller.SharedClass;

public class DialogSala extends JDialog implements ActionListener {

	
	private JButton buttonNuova;
	private JButton buttonCerca;
	private JTextField textFieldTavolo;
	private MainFrame frame;
	
	
	
	public DialogSala(MainFrame fr){
		setBounds(100, 100, 400, 100);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setLayout(null);
		this.setTitle("Comande");
		
		this.frame=fr;
		
		this.buttonNuova=new JButton("Nuovo ordine");
		this.buttonNuova.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DialogSala.this.frame.removePanel("sala");
			}
			
		});
		
		this.buttonCerca=new JButton("Cerca ordine");
		buttonCerca.setEnabled(false);

		
		this.buttonCerca.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			
			
			try{
				Integer.parseInt(textFieldTavolo.getText());
			}catch(IllegalArgumentException e1){
				new Dialog("Dati inseriti non corretti!");
			}
			
			
			ArrayList<Comanda>arrayTemp=new ComandaTable().cercaComanda(Integer.parseInt(textFieldTavolo.getText()), SharedClass.getSingleton().getConnection());
			if(arrayTemp!=null){
				SharedClass.getSingleton().setArrayOrdine(arrayTemp);
				SharedClass.getSingleton().setModificaComanda(true);
				DialogSala.this.frame.removePanel("sala");
			}else{
				new Dialog("Nessuna comanda per il tavolo inserito!");

			
			}
				
			
			}
			
		});
		this.buttonNuova.setBounds(16, 20,140, 30);
		this.buttonCerca.setBounds(180, 20,140, 30);

		this.textFieldTavolo=new JTextField();
		this.textFieldTavolo.setBounds(330, 20, 40, 30);
		this.textFieldTavolo.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				buttonCerca.setEnabled(true);
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				//buttonCerca.setEnabled(false);

				
			}
			
		});
		
		
		this.getContentPane().add(buttonNuova);
		this.getContentPane().add(buttonCerca);
		this.getContentPane().add(textFieldTavolo);

		this.setVisible(true);
		
		//(6, 6, 345, 56)
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	

}
