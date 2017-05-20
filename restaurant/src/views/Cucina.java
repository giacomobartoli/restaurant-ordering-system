package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ComandaTable;
import controller.PortataTable;
import controller.SharedClass;
import Model.Comanda;
import Model.Portata;



public class Cucina extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	
	private ArrayList<Comanda> listaComande=new ArrayList<>();
	private ArrayList<Comanda> comandeScremate=new ArrayList<>();

	private DefaultListModel<String>model=new DefaultListModel<>();
	private DefaultListModel<String>model2=new DefaultListModel<>();

	
	private JList<String> list;
	
	private JList<String> list2;




	
	public Cucina(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);
		
		this.setFrameAttributes();
		this.mainFrame.setTitleText("Cucina");
		
		
		this.setLayout(null);

		
		this.listaComande=new ComandaTable().caricaComande(SharedClass.getSingleton().getConnection()) ;

		
		
		for(Comanda c:this.listaComande){
			
			Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
			if(!p.getTipo().equals("Bevanda")){
				this.comandeScremate.add(c);
			}
		}
			
			
		
		this.impostaArrayTavoli();
		
		list= new JList<String>(model); 

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && list.getSelectedValue()!=null){
					model2.removeAllElements();

						for(Comanda c:comandeScremate){
						
						if(c.getTavolo()==Integer.parseInt(list.getSelectedValue().substring(8))){
							Portata p=new PortataTable().cercaPortata(c.getCodicePortata(), SharedClass.getSingleton().getConnection());
							model2.addElement(p.getNome()+"  "+p.getDettaglio()+"  x "+c.getQuantita()+" "+c.getNote());
							
						}
					}
				}
				
			}
			
		});
		
		list2= new JList<String>(model2); 

		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 

		
		this.setEnabled(false);

		JScrollPane  scroll=new JScrollPane(list);
		
		
		JScrollPane  scroll1=new JScrollPane(list2);

		scroll.setBounds(0, 0, 180, 390);
		this.add(scroll); 
		
		scroll1.setBounds(200, 0,400, 390);
		this.add(scroll1); 
			
			
			
	
	}
	

	

	public void impostaArrayTavoli(){
		int i=-1;
		this.model.removeAllElements();

		
		for(Comanda c:this.comandeScremate){

			if(c.getTavolo()!=i){
				i=c.getTavolo();
				this.model.addElement("Tavolo  "+i);				
			}
		}
		
	}
	

	

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setFrameAttributes() {
		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());

		this.mainFrame.setSize(600,440);
	}
	 

}
