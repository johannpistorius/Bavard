import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FenetreConnexion extends JFrame{
	private JPanel pane;
	private JTextField textLogin;
	private JLabel label;
	private JButton button;
	private Concierge c;
	public FenetreConnexion(Concierge c) {
		this.c=c;
		this.pane=new JPanel();
		init();
	}
	/**
	 * Initializes JPanel to login page
	 */
	public final void init() {
		this.pane.removeAll();
		this.setJMenuBar(null);
		label = new JLabel();
		label.setText("Login");
		textLogin = new JTextField(20);
		this.button=new JButton("Connexion");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean exist = false;
				for(PapotageListener pl:c.bavards) {
					if(pl instanceof Bavard) {
						if(((Bavard) pl).getLogin().equals(textLogin.getText())) {
							exist = true;
						}
					}
				}
				if(exist == false) {
					Bavard b = new Bavard(textLogin.getText(),c);
					c.addListener(b);
					b.createPapotageEvent("OnLineBavardEvent", "User "+ b.getLogin()+" has joined the channel!");
					Fenetre window=new Fenetre(c,b);	
				}
			}
		});
		button.setEnabled(false);
		textLogin.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}	
			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}
			public void changed() {
			     if (textLogin.getText().length()<3){
			         button.setEnabled(false);
			       }
			       else {
			         button.setEnabled(true);
			      }
			}
		});	
		this.pane.add(this.label);
		this.pane.add(this.textLogin);
		this.pane.add(this.button);

		setResizable(true);
		setContentPane(pane);
		setTitle("Connexion");
		pane.setBackground(null);
		setSize(300,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
