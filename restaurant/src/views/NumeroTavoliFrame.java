package views;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import Model.Servizio;

import controller.ServizioTable;
import controller.SharedClass;


public class NumeroTavoliFrame extends JFrame {
	
	private String s;
	
	
	public NumeroTavoliFrame(){
		
		super();
		
		this.setTitle("Impostazioni servizio");
		this.setResizable(false);
		this.setSize(400,200);
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		JButton button=new JButton("Inizia servizio");
		button.setBounds(100, 145, 200, 30);
		this.getContentPane().add(button);
		
		
		JLabel label=new JLabel("Numero tavoli:  ");
		label.setBounds(100, 90, 150, 30);
		this.getContentPane().add(label);
		
		final JTextField textField=new JTextField();
		
		textField.setBounds(260, 90, 50, 30);

		this.getContentPane().add(textField);
		
		final JComboBox<String> combo=new JComboBox<>(new String[]{"Pranzo","Cena"}); 
		combo.setBounds(100, 50, 210, 30);
		this.getContentPane().add(combo);
		
		DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		s=dateFormat.format(date);
		
		JLabel labelData=new JLabel(s);
		labelData.setBounds(100, 10, 200, 30);
		labelData.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(labelData);
		
		dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		s=dateFormat.format(date);
		dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		
		ArrayList<Servizio>servizi=new ServizioTable().cercaPerData(dateFormat.format(date), SharedClass.getSingleton().getConnection());

		if(servizi.size()>0){
		Servizio service=servizi.get(servizi.size()-1);
		
			textField.setText(""+service.getTavoliTotali());
		}
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i=0;
				try{
					Integer.parseInt(textField.getText());

					 i=Integer.parseInt(textField.getText());

				}catch(NumberFormatException e1){
					textField.setText("");
					new Dialog("Dati inseriti non corretti!");
					return;

				}
				
				DateFormat temp=new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();
				String stringa=temp.format(date);
				
				
				ArrayList<Servizio>servizi=new ServizioTable().cercaPerData(stringa, SharedClass.getSingleton().getConnection());
				
			
				temp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime=temp.format(date);	

				if(servizi.size()==0){
					
					
					
					new ServizioTable().inserisciServizio(new Servizio(0,(String)combo.getSelectedItem(),s,i), SharedClass.getSingleton().getConnection());
					SharedClass.getSingleton().impostaServizio();
					dispose();

					new MainFrame();
					
				}else if(servizi.size()==1){
					
					Servizio servizio=servizi.get(0);
				
					if(servizio.getPranzoCena().equals("Cena")){
						new Dialog("Servizi del giorno completati");
						dispose();

					}else{
						
						String pranzo=(String)combo.getSelectedItem();
						if(servizio.getPranzoCena().equals("Pranzo") && pranzo.equals("Pranzo")){
							new Dialog("Servizio pranzo gia' passato!");
							return;
						}
						
						
						new ServizioTable().inserisciServizio(new Servizio(0,(String)combo.getSelectedItem(),s,i), SharedClass.getSingleton().getConnection());
						SharedClass.getSingleton().impostaServizio();

						dispose();

						new MainFrame();
					}
				
					
				}else{
					new Dialog("Servizi del giorno completati");
					dispose();

				}
		
			}
			
		});
	
		this.setVisible(true);

	}
	

}
