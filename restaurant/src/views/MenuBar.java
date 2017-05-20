package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private static final String MENU_ITEM1 = "Informazioni";
	private static final String MENU_ITEM2 = "Esci";
	public static MainFrame fr;
	
	
	public MenuBar(MainFrame frame){
		super();
		final JMenu menu=new JMenu("Ristorante");
		final JMenuItem infoItem=new JMenuItem(MENU_ITEM1);
		 JMenuItem exitItem=new JMenuItem(MENU_ITEM2);
		this.fr=frame;
		
		menu.add(infoItem);		
		menu.add(exitItem);
		this.add(menu);
		
		
		
		
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {

				MenuBar.fr.removePanel("main");
			}
		});
		
		infoItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});	
	}
		
	
	

}
