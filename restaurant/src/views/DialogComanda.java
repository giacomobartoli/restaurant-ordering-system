package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import controller.SharedClass;
import Model.Comanda;
import Model.Portata;

public class DialogComanda extends JDialog implements ActionListener{

	private final JPanel contentPanel=new JPanel();
	private Portata portata;
	private int numeroTavolo;
	private int indice;
	private FrameComanda frame;
	private int cod;

	public DialogComanda(Portata p,int numeroT,FrameComanda fr,final boolean inModifica,int quantita,String note,int codice,int index){
		
		this.setAlwaysOnTop(true);
		this.setTitle("Dettaglio piatto");
		 setBounds(100, 100, 251, 300);
		this.setLocationRelativeTo(null);
         getContentPane().setLayout(new BorderLayout());
         contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
         getContentPane().add(contentPanel, BorderLayout.CENTER);
         contentPanel.setLayout(null);
         
         this.portata=p;
        this.numeroTavolo=numeroT;
        this.frame=fr;
        this.cod=codice;
        this.indice=index;
        
         JLabel lblQuantit = new JLabel("Quantit\u00E0:");
         lblQuantit.setBounds(6, 23, 77, 21);
         contentPanel.add(lblQuantit);
        
         
         final JLabel label = new JLabel("1");
         label.setBounds(215, 25, 61, 16);
         contentPanel.add(label);
         
         final JButton button_1 = new JButton("-");
			button_1.setEnabled(false);

      button_1.setBounds(150, 20, 44, 29);
      contentPanel.add(button_1);
         
         JButton button = new JButton("+");
         button.setBounds(94, 20, 44, 29);
         contentPanel.add(button);
         button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int temp=Integer.parseInt(label.getText())+1;
				
				if(temp>1)
 					button_1.setEnabled(true);

				label.setText(""+temp);
				
			}
        	 
         });
        
       
        
         button_1.addActionListener(new ActionListener(){

 			@Override
 			public void actionPerformed(ActionEvent e) {
 				
 				
 				int temp=Integer.parseInt(label.getText());
 				if(temp>1){
 					button_1.setEnabled(true);
 					temp--;
 					label.setText(""+temp);
 				}else{
 					button_1.setEnabled(false);

 				}
 				
 				if(temp==1){
 					button_1.setEnabled(false);

 				}
 			}
         	 
          });
        
        
         final JTextPane textPane = new JTextPane();
         
         
         JScrollPane scroll=new JScrollPane(textPane);
		 scroll.setBounds(6, 86, 239, 147);
         
         contentPanel.add(scroll);
        
         JLabel lblNote = new JLabel("Note:");
         lblNote.setBounds(6, 66, 61, 16);
         contentPanel.add(lblNote);
        
         JPanel buttonPane = new JPanel();
         buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
         getContentPane().add(buttonPane, BorderLayout.SOUTH);
                 
         JButton okButton = new JButton("OK");
         buttonPane.add(okButton);
          getRootPane().setDefaultButton(okButton);
                         
          okButton.addActionListener(new ActionListener(){

        	  @Override
        	  public void actionPerformed(ActionEvent e) {

        		  Comanda c=new Comanda(0,portata.getIdPortata(),numeroTavolo,Integer.parseInt(label.getText()),textPane.getText(),false);
        		  
        		  if(!inModifica){
        			  c.setModificato(false);
        			  SharedClass.getSingleton().aggiungiAordine(c);  
        		  }else{
        			  c.setModificato(true);
        			  c.setIdOrdine(SharedClass.getSingleton().getArrayOrdine().get(indice).getIdOrdine());
        			  SharedClass.getSingleton().aggiornaPortataAllIndice(indice, c);
        		  }

        	  DialogComanda.this.frame.aggiornaListaOrdine(c,portata,indice);
        		  
        		  dispose();
        	  }

          });
	
	
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
			}

		});

         if(inModifica){
        	 label.setText(""+quantita);
        	 textPane.setText(note);
        	 
        	 if(quantita!=1){
        		 button_1.setEnabled(true);
        	 }
         }
         
         this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
