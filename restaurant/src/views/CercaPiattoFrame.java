package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Portata;
import controller.PortataTable;
import controller.SharedClass;

public class CercaPiattoFrame extends AbstractPanel implements ListSelectionListener{

	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String>model=new DefaultListModel<>();
	private  JButton btnCancella;
	private  JButton button;
	private ArrayList<Portata> portate;
	private MainFrame fr;
	
	public CercaPiattoFrame(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);

		this.fr=mainFrame;
		
   

         this.setLayout(null);
         
         this.setFrameAttributes();
        
       final JTextField  textField = new JTextField();
         textField.setBounds(6, 27, 218, 28);
         this.add(textField);
         textField.setColumns(10);
         
         textField.addActionListener(new ActionListener(){

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.removeAllElements();

				portate=(ArrayList<Portata>) new PortataTable().cercaPerNome(textField.getText(), SharedClass.getSingleton().getConnection());
				if(portate!=null)
					riempiLista(portate);
				textField.setText("");
				
			}
        	 
         });
        
         JLabel lblCercaPiatto = new JLabel("Cerca piatto:");
         lblCercaPiatto.setBounds(6, 6, 119, 16);
         this.add(lblCercaPiatto);
        
         btnCancella = new JButton("Cancella");
         btnCancella.setEnabled(false);
         btnCancella.setBounds(6, 303, 110, 29);
         this.add(btnCancella);
         
         btnCancella.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Portata portata=portate.get(list.getSelectedIndex());
				portate.remove(list.getSelectedIndex());
				
				new PortataTable().cancellaPortata(portata, SharedClass.getSingleton().getConnection());
				refreshList();
			}
        	 
         });
         
        
        button = new JButton("Modifica");
        button.setEnabled(false);
         button.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                	 SharedClass.getSingleton().setPiattoInModifica(true);
                	 SharedClass.getSingleton().setPortata(portate.get(list.getSelectedIndex()));
                	 fr.removePanel("nuovoPiatto");
                 }
         });
         button.setBounds(114, 303, 110, 29);
         this.add(button);
         
         
         list = new JList<>(model);
 		list.addListSelectionListener(this);
 		
 		 JScrollPane scroll=new JScrollPane(list);
		 scroll.setBounds(6, 60, 218, 230);
		 
		 this.add(scroll);
         
         this.setVisible(true);
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		 btnCancella.setEnabled(true);
		 button.setEnabled(true);
		
	}


	@Override
	public void setFrameAttributes() {

		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());

		this.mainFrame.setSize(235,390);
	}

	public void riempiLista(ArrayList<Portata> l){
		int i=0;
		
		for(Portata p:l){
			
			this.model.addElement(l.get(i).getNome()+"  "+p.getDettaglio());
			i++;
			
		}
	}
	
	private void refreshList(){
		this.model.removeAllElements();
		
		for(Portata p:portate){
			model.addElement(p.getNome());
		}
	}
	
}
