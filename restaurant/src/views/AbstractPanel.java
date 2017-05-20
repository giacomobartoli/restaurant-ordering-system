package views;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel{


	private static final long serialVersionUID = 1L;

	protected MainFrame mainFrame;
	protected String panelName;
	
	public AbstractPanel(final String panelName,final MainFrame mainFrame){
		super();
		this.panelName=panelName;
		this.mainFrame=mainFrame;
		this.setLayout(null);
	}
	

	//il metodo viene implementato dai pannelli che estendono questa classe 
	public abstract void setFrameAttributes();
	
}