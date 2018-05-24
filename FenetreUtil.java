import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class FenetreUtil extends JFrame{
	private JPanel pane;
	private JLabel label;
	private Concierge c;
	private JPanel messages;
	private JScrollPane scrollPane;
	public FenetreUtil(Concierge c) {
		this.c = c;
		this.pane=new JPanel();
		init();
	}
	/**
	 * Initializes JPanel to list all utilisateurs
	 */
	public final void init() {
		this.pane.removeAll();
		this.pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		this.setJMenuBar(null);
		label = new JLabel();
		label.setText("Liste utilisateur :");
		this.pane.add(this.label);
		this.messages=new JPanel();
		this.messages.setLayout(new BoxLayout(messages,BoxLayout.Y_AXIS));
		this.scrollPane=new JScrollPane(messages,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		for(PapotageListener ba:c.bavards) {
			if(ba instanceof Bavard) {
				JLabel label2 = new JLabel();
				label2.setText(((Bavard) ba).getLogin());
				messages.add(label2);
			}
		}
		this.pane.add(this.scrollPane);
		
		setResizable(true);
		setContentPane(pane);
		setTitle("Connexion");
		pane.setBackground(null);
		setSize(300,300);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowAdapter exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
					setVisible(false);
		    }
		};
		this.addWindowListener(exitListener);
	}
}
