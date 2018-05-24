import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Fenetre extends JFrame{
	private JPanel pane;
	private JTextField textLogin;
	private JTextArea text;
	private JLabel label;
	private JButton button;
	private Concierge c;
	public Bavard b;
	private Object[] list;
	private JComboBox<Object> box;
	private JPanel messages;
	private JScrollPane scrollPane;
	private JScrollBar vertical;
	public Fenetre(Concierge c, Bavard b) {
		this.c=c;
		this.b=b;
		this.pane=new JPanel();
		init();
		
	}
	/**
	 * Once the user has logged in, this function creates the JPanel to main messaging interface
	 */
	public final void init() {
		this.pane.removeAll();
		this.pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		c.addFenetre(this);
		JMenuBar menuBar = new JMenuBar(); 
		JMenu propos = new JMenu("A propos");
		JMenu action = new JMenu("Action");
		JMenuItem deco = new JMenuItem("Deconnexion");
		JMenuItem util = new JMenuItem("Liste Utilisateur");
		action.add(deco);
		propos.add(util);
		menuBar.add(action);
		menuBar.add(propos);
		deco.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				b.createPapotageEvent("OffLineBavardEvent", "User "+ b.getLogin()+" has quit the channel!");
				c.removeListener(b);
				setVisible(false);
				
			}});
		
		util.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FenetreUtil window=new FenetreUtil(c);
				
			}});
		this.setJMenuBar(menuBar);
		
		label = new JLabel();
		label.setText("MESSAGE");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.pane.add(this.label);
		
		text = new JTextArea(); 
		text.setRows(3);
		text.setColumns(40);
		text.setLineWrap(true);
		
		text.setMaximumSize(new Dimension(800,24));
		text.setMinimumSize(new Dimension(300,24));
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		list=new Object[] {"Sport","Cinema","Polytech","Animaux"};
		box=new JComboBox<Object>(list);
		box.setMaximumSize(new Dimension(800,24));
		box.setMinimumSize(new Dimension(300,24));
		box.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.button=new JButton("Envoyer");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b.createPapotageEvent(box.getSelectedItem().toString(), text.getText());	
			}
		});
		BufferedImage img = generateIdenticons(b.getLogin(),50,50);
		JLabel jLabelImg = new JLabel(new ImageIcon(img));
		jLabelImg.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.messages=new JPanel();
		this.messages.setLayout(new BoxLayout(messages,BoxLayout.Y_AXIS));
		this.scrollPane=new JScrollPane(messages,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.vertical=scrollPane.getVerticalScrollBar();
		
		this.pane.add(jLabelImg);
		this.pane.add(this.text);
		this.pane.add(this.box);
		this.pane.add(this.button);
		
		for(int i = 0; i<list.length;i++) {	
			JCheckBox checkBox = new JCheckBox();
			JLabel label1 = new JLabel(list[i].toString());
			checkBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
			        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
			            if(!b.categ.contains(label1.getText())) {
			            	b.categ.add(label1.getText().toString());
			            }
			        } else {//checkbox has been deselected
			            b.categ.remove(label1.getText().toString());
			        };
				}
			});
			this.pane.add(checkBox);
			this.pane.add(label1);
		}
		this.pane.add(this.scrollPane);
		pane.setBackground(Color.lightGray);
		setContentPane(pane);
		setTitle("Papoter" +" "+ b.getLogin());
		setResizable(true);
		setSize(500,500);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowAdapter exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "Are you sure you want to close the application?", 
		             "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	b.createPapotageEvent("OffLineBavardEvent", "User "+ b.getLogin()+" has quit the channel!");
					c.removeListener(b);
					setVisible(false);
		        }
		    }
		};
		this.addWindowListener(exitListener);
	}
	/**
	 * This function adds a message to the JPanel
	 */
	public void updateMessage() {
		JTextArea newJLabel = new JTextArea();
		newJLabel.setRows(1);
		newJLabel.setColumns(40);
		newJLabel.setWrapStyleWord(true);
		newJLabel.setLineWrap(true);
		ImageIcon img = new ImageIcon();
		JLabel jLabelImg = new JLabel();
		for(PapotageEvent object:b.papotageEvent) {
			BufferedImage Buffimg = generateIdenticons(object.getBavard().getLogin(),20,20);
			img.setImage(Buffimg);
			jLabelImg.setIcon(img);
			if(object.getSujet()!= "OnLineBavardEvent" && object.getSujet()!="OffLineBavardEvent") {
				if(object.getCorps().length()>10) {
					newJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
					newJLabel.setText(object.getBavard().getLogin()+ " says: " + object.getCorps().substring(0, 10) + "...");
					newJLabel.addMouseListener(new MouseListener()  
					{  
						@Override
					    public void mouseClicked(MouseEvent e)  
					    {  
							newJLabel.setText(object.getBavard().getLogin()+ " says: " + object.getCorps());
					    }

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub	
						}  
					}); 
				}
				else {
					newJLabel.setText(object.getBavard().getLogin()+ " says: " + object.getCorps());
				}
				newJLabel.setToolTipText("Time : "+ object.getDate()+ " Category : " + object.getSujet());
				messages.add(jLabelImg);
				messages.add(newJLabel);
				setContentPane(pane);
			}else {
				newJLabel.setText(object.getCorps());
				newJLabel.setToolTipText("Time "+ object.getDate());
				messages.add(jLabelImg);
				messages.add(newJLabel);
				setContentPane(pane);
			}
		}
		this.vertical.setValue(vertical.getMaximum());
	}
	
	/**
	 * Deals with the identicons
	 * @param text
	 * @param image_width
	 * @param image_height
	 * @return
	 */
	public static BufferedImage generateIdenticons(String text, int image_width, int image_height){
        int width = 5, height = 5;

        byte[] hash = text.getBytes();

        BufferedImage identicon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = identicon.getRaster();

        int [] background = new int [] {255,255,255, 0};
        int [] foreground = new int [] {hash[0] & 255, hash[1] & 255, hash[2] & 255, 255};

        for(int x=0 ; x < width ; x++) {
            //Enforce horizontal symmetry
            int i = x < 3 ? x : 4 - x;
            for(int y=0 ; y < height; y++) {
                int [] pixelColor;
                //toggle pixels based on bit being on/off
                if((hash[i] >> y & 1) == 1)
                    pixelColor = foreground;
                else
                    pixelColor = background;
                raster.setPixel(x, y, pixelColor);
            }
        }

        BufferedImage finalImage = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);

        //Scale image to the size you want
        AffineTransform at = new AffineTransform();
        at.scale(image_width / width, image_height / height);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        finalImage = op.filter(identicon, finalImage);
        return finalImage;
	}
}