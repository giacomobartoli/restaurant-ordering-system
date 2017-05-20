package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import Model.Prenotazione;
import controller.PrenotazioneTable;
import controller.SharedClass;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;



public class PrenotazionePanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	 private JDatePanelImpl datePanel;
	 private UtilDateModel model;
	private Date selectedDate;
	private JTextField textFieldData;
	private static final String DATE_FORMAT="yyyy-MM-dd";
	
	public PrenotazionePanel(String panelName, MainFrame mainFrame) {
		super(panelName, mainFrame);
		
		this.setLayout(null);
		this.setFrameAttributes();
		this.mainFrame.setTitleText("Prenotazione");
				
		model = new UtilDateModel();
		model.setValue(new Date());
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model);
		//datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());		
		datePanel.addActionListener(this);	
		datePanel.setBounds(10, 10, 200, 200);
		this.add(datePanel);
		selectedDate=(Date)model.getValue();
		
		
		
		JLabel label=new JLabel("Nome");
		label.setBounds(230, 28, 120, 20);
		
		final JTextField textField=new JTextField();
		textField.setBounds(330, 28, 150, 20);
		this.add(textField);

		this.add(label);
		
		JLabel label1=new JLabel("Numero posti");
		label1.setBounds(230, 68, 120, 20);
		
		final JTextField textField1=new JTextField();
		textField1.setBounds(330, 68, 30, 20);
		this.add(textField1);
		this.add(label1);
		
		JLabel label2=new JLabel("Ora");
		label2.setBounds(230, 108, 120, 20);
		
		final JSpinner spinner=new JSpinner();
		spinner.setModel(new SpinnerDateModel());
		spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
		spinner.setBounds(330, 108, 70, 25);
		this.add(spinner);
		this.add(label2);
		
		
		JLabel label3=new JLabel("Data");
		label3.setBounds(230, 148, 120, 20);
		
		this.textFieldData=new JTextField();
		this.textFieldData.setBounds(330, 148, 150, 20);
		this.add(this.textFieldData);
		this.add(label3);

		
		
		
		
		JButton buttonPrenota=new JButton("Prenota");
		buttonPrenota.setBounds(175, 215, 150, 30);
		
		buttonPrenota.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				try{
					Integer.parseInt(textField1.getText());
				}catch(IllegalArgumentException e1){
					new Dialog("Dati inseriti non corretti!");
					return;
				}
				
				
				
				
				Date d=(Date)spinner.getValue();
				
				final JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm:ss");
				de.getTextField().getText();
				
				final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,Locale.ITALIAN);
				selectedDate=(Date)model.getValue();
				
			
				
				
				String dataOra=formatter.format(selectedDate)+" "+de.getTextField().getText();
				
				if(controllaTavoliPrenotati(dataOra,formatter.format(selectedDate),textField.getText(),Integer.parseInt(textField1.getText()))){
					new PrenotazioneTable().aggiungiPrenotazione(new Prenotazione(0,textField.getText(),dataOra,Integer.parseInt(textField1.getText()),"inAttesa",SharedClass.getSingleton().getServizio().getIdServizio(),0), SharedClass.getSingleton().getConnection());
					
					new Dialog("Prenotazione effettuata");
					
					SharedClass.getSingleton().aggiornaPrenotazione();
				}
				
				
			}
			
		});
		
		this.add(buttonPrenota);

		
	}
	
	
	private boolean controllaTavoliPrenotati(String dataOra,String data,String nome,int numeroPosti){
		
		
		String s="15:00:00";
		String dataDiConfronto=data+" "+s;
		boolean isPranzo=false;
		
		
		//confronta la stringa per capire se la prenotazione va aggiunta a pranzo o cena
		if(dataOra.compareTo(dataDiConfronto) > 0){
			System.out.println("CENA");

		}else{
			isPranzo=true;
			System.out.println("PRANZO");

		}
		
		
		ArrayList<Prenotazione> temp=new ArrayList<>();
		
		
		for(Prenotazione p:new PrenotazioneTable().cercaPerDataInAttesa(dataOra, SharedClass.getSingleton().getConnection())){
			
			
			if(isPranzo){
				temp.add(p);
			}else{
				temp.add(p);

			}
			
			
		}
		
		for(Prenotazione p:temp){
			if(p.getNome().equals(nome)){
				new Dialog("Nome prenotazione esistente");

				return false;
			}	
		}
		
		
		
		int tavoliOccupati=this.tavoliOccupati(temp);
		
		System.out.println("tavoli occupati prima della nuova prenotazione "+tavoliOccupati);

		
		
		int numeroTavoli=(numeroPosti-2)/2;
		if(temp.size()==0){
			
			if(numeroPosti%2!=0){
			numeroTavoli+=1;
			}
		}else{
			for(Prenotazione p:temp){
				
				if(numeroPosti%2!=0){
				numeroTavoli+=1;
				}
			}
		}
		
		tavoliOccupati+=numeroTavoli;
		
		System.out.println("tavoli occupati + nuova prenotazione "+tavoliOccupati);


		if(tavoliOccupati>SharedClass.getSingleton().getServizio().getTavoliTotali()){
			new Dialog("Posti disponibili rimasti sono "+(SharedClass.getSingleton().getServizio().getTavoliTotali()-tavoliOccupati));

			return false;
		}
		

		
		return true;
	}

	
	private int tavoliOccupati(ArrayList<Prenotazione>arr){
		
		int tavoli=0;

		for(Prenotazione p:arr){
			tavoli+=(p.getNumeroPosti()-2)/2;
			if(p.getNumeroPosti()%2!=0){
				tavoli+=1;
				}
		}
		
		return tavoli;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		final Object buttonPressed = e.getSource();
		if(buttonPressed.equals(datePanel)){
			final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,Locale.ITALIAN);
			selectedDate=(Date)model.getValue();
			this.textFieldData.setText(formatter.format(selectedDate));
		
		}

	}

	@Override
	public void setFrameAttributes() {
		this.mainFrame.setJMenuBar(this.mainFrame.getMyMenuBar());
		
		this.mainFrame.setSize(500,300);
	}
	

}
