package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Model.Portata;
import controller.PortataTable;
import controller.SharedClass;

public class NuovoPiattoFrame extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	
	
	public NuovoPiattoFrame(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);
        
		this.setBounds(100,100,230,360);
		
		this.setFrameAttributes();;
		
		
		 JLabel lblNomePiatto = new JLabel("Nome piatto:");
         lblNomePiatto.setBounds(6, 6, 98, 16);
         this.add(lblNomePiatto);
        
         final JTextField  textField = new JTextField();
         textField.setBounds(6, 22, 218, 28);
         this.add(textField);
         textField.setColumns(10);
        
         
        
         JLabel lblPiatto = new JLabel("Piatto:");
         lblPiatto.setBounds(6, 62, 98, 16);
         this.add(lblPiatto);
        
         JLabel lblDescrizione = new JLabel("Descrizione:");
         lblDescrizione.setBounds(6, 118, 98, 16);
         this.add(lblDescrizione);
        
         JLabel label_3 = new JLabel("Prezzo:");
         label_3.setBounds(6, 200, 98, 16);
         this.add(label_3);
        
         final JTextField textFieldPrezzo = new JTextField();
         textFieldPrezzo.setColumns(10);
         textFieldPrezzo.setBounds(6, 215, 218, 28);
         this.add(textFieldPrezzo);
         
        // final JComboBox<String> comboBox = new JComboBox<>(new String[]{"Piatto","Bevanda"});
        // comboBox.setBounds(6, 79, 218, 27);
       //  this.add(comboBox);
         
         
        
        final JComboBox<String> comboBox = new JComboBox<>(new String[]{"Antipasto","Primo","Secondo","Contorno","Dolce","Bevanda"});
         comboBox.setBounds(6, 80, 218, 27);
         this.add(comboBox);
         
         
         final JTextPane textPane = new JTextPane();
         
         JScrollPane scroll=new JScrollPane(textPane);
		 scroll.setBounds(6, 140, 218, 50);
		 
         this.add(scroll);
        
         JButton btnNewButton = new JButton("Salva");
         btnNewButton.setBounds(59, 303, 117, 29);
         btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				try{
					Float.parseFloat(textFieldPrezzo.getText());
				}catch(IllegalArgumentException e1){
					new Dialog("Prezzo non valido");
					return;
				}
				
				if(textField.getText().equals("")){
					new Dialog("Inserire nome piatto!");
					return;
				}
				
				
				PortataTable p=new PortataTable();
				if(! SharedClass.getSingleton().isPiattoInModifica()){
					p.aggiungiPortata(new Portata(0,textField.getText(),(String)comboBox.getSelectedItem(),Float.parseFloat(textFieldPrezzo.getText()),textPane.getText()));
					
					new Dialog("Piatto inserito correttamente");

					
					textField.setText("");
					textFieldPrezzo.setText("");
					comboBox.setSelectedIndex(0);
					textPane.setText("");
					
				}else{
					//aggiorniamo
					
					new PortataTable().aggiornaPiatto(new Portata(SharedClass.getSingleton().getPortata().getIdPortata(),textField.getText(),(String)comboBox.getSelectedItem(),Float.parseFloat(textFieldPrezzo.getText()),textPane.getText()), SharedClass.getSingleton().getConnection());
					
					
					
					 SharedClass.getSingleton().setPiattoInModifica(false);
		        	 SharedClass.getSingleton().setPortata(null);
		        	 
		        	 NuovoPiattoFrame.this.mainFrame.removePanel("main");
				}
				
				
				
				
	        	 
				
			}
        	 
         });
         
         
         this.add(btnNewButton);
        
        
         
         
         comboBox.addActionListener(new ActionListener(){

 			@Override
 			public void actionPerformed(ActionEvent e) {

 				
 			}
         	 
          });
         
         this.setVisible(true);
         
      
         if(SharedClass.getSingleton().isPiattoInModifica()){
        	 

        	 
        	 Portata p=SharedClass.getSingleton().getPortata();
        	 
        	 textField.setText(p.getNome());
        	 textPane.setText(p.getDettaglio());
        	 textFieldPrezzo.setText(""+p.getPrezzo());
        	 
        	 for(int i=0;i<6;i++){
        		 if(comboBox.getModel().getElementAt(i).equals(p.getTipo())){
        			 comboBox.setSelectedIndex(i);
        			 break;
        		 }
        	 }
        	 
        	
        	 
        	 
         }
 }


	@Override
	public void setFrameAttributes() {

		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());

		this.mainFrame.setSize(235,390);
		
		
	}

}
