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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

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
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	public Fenetre(Concierge c) {
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
		c.removeFenetre(this);
		label = new JLabel();
		label.setText("Login");
		textLogin = new JTextField(20);
		this.button=new JButton("Connexion");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b = new Bavard(textLogin.getText(),c);
				c.addListener(b);
				b.createPapotageEvent("OnLineBavardEvent", "User "+ b.getLogin()+" has joined the channel!");
				miseAJour();
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
		setTitle("Papoter");
		pane.setBackground(null);
		setSize(300,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	/**
	 * Once the user has logged in, this functin updates the JPanel to main messaging interface
	 */
	public void miseAJour() {
		this.pane.removeAll();
		this.pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		c.addFenetre(this);
		JMenuBar menuBar = new JMenuBar(); 
		JMenu propos = new JMenu("A propos");
		JMenu action = new JMenu("Action");
		JMenuItem deco = new JMenuItem("Deconnexion");
		action.add(deco);
		menuBar.add(action);
		menuBar.add(propos);
		deco.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				b.createPapotageEvent("OffLineBavardEvent", "User "+ b.getLogin()+" has quit the channel!");
				init();
				
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
				/*if(!checked.contains(box.getSelectedItem().toString())) {
					checked.add(box.getSelectedItem().toString());
				}*/
				b.createPapotageEvent(box.getSelectedItem().toString(), text.getText());	
			}
		});
		BufferedImage img = generateIdenticons(b.getLogin(),50,50);
		JLabel jLabelImg = new JLabel(new ImageIcon(img));
		jLabelImg.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.messages=new JPanel();
		this.messages.setLayout(new BoxLayout(messages,BoxLayout.Y_AXIS));
		this.scrollPane=new JScrollPane(messages,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		setSize(500,500);
	}
	/**
	 * This function adds a message to the JPanel
	 */
	public void updateMessage() {
		JTextArea newJLabel = new JTextArea(1,40);
		newJLabel.setWrapStyleWord(true);
		ImageIcon img = new ImageIcon();
		JLabel jLabelImg = new JLabel();
		//System.out.println(checked);
		for(PapotageEvent object:b.papotageEvent) {
			System.out.println(object);
			BufferedImage Buffimg = generateIdenticons(object.getBavard().getLogin(),20,20);
			img.setImage(Buffimg);
			jLabelImg.setIcon(img);
			if(object.getSujet()!= "OnLineBavardEvent" && object.getSujet()!="OffLineBavardEvent") {
				System.out.println("titi");
				if(object.getCorps().length()>10) {
					newJLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
					newJLabel.setText(object.getBavard().getLogin()+ " says: " + object.getCorps().substring(0, 10) + "...");
					newJLabel.addMouseListener(new MouseListener()  
					{  
						@Override
					    public void mouseClicked(MouseEvent e)  
					    {  
							// TODO Auto-generated method stub
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
				System.out.println("toto");
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