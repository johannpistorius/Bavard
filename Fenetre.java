import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Fenetre extends JFrame{
	private JPanel pane;
	private JTextField text;
	private JLabel label;
	private JButton button;
	private Concierge c;
	private Bavard b;
	private Object[] list;
	private JComboBox<Object> box;
	public Fenetre(Concierge c) {
		this.c=c;
		c.addFenetre(this);
		init();
	}
	public final void init() {
		
		pane=new JPanel();
		label = new JLabel();
		label.setText("Login");
		text = new JTextField(20);  
		this.button=new JButton("Connexion");
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b = new Bavard(text.getText(),c);
				c.addListener(b);
				miseAJour();
			}
		});
		button.setEnabled(false);
		text.getDocument().addDocumentListener(new DocumentListener() {
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
			     if (text.getText().length()<3){
			         button.setEnabled(false);
			       }
			       else {
			         button.setEnabled(true);
			      }
			}
		});	
		this.pane.add(this.label);
		this.pane.add(this.text);
		this.pane.add(this.button);

		setResizable(true);
		setContentPane(pane);
		setTitle("Papoter");
		setSize(300,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void miseAJour() {
		this.pane.removeAll();
		label = new JLabel();
		label.setText("MESSAGE");
		text = new JTextField(40); 
		list=new Object[] {"Sport","Cinema","Polytech","Animaux"};
		box=new JComboBox<Object>(list);
		this.button=new JButton("Envoyer");
		this.pane.add(this.label);
		this.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				b.createPapotageEvent(box.getSelectedItem().toString(), text.getText());	
				c.message(b);
			}
		});
		BufferedImage img = generateIdenticons(b.getLogin(),50,50);
		JLabel jLabelImg = new JLabel(new ImageIcon(img));
		this.pane.add(jLabelImg);
		this.pane.add(this.text);
		this.pane.add(this.box);
		this.pane.add(this.button);
		setContentPane(pane);
		setSize(800,800);
	}
	
	public void updateMessage() {
		JLabel newJLabel = new JLabel();
		for(PapotageEvent object:b.papotageEvent) {
			newJLabel.setText(object.getBavard().getLogin()+ " say: " + object.getCorps()+ " at "+ object.getDate());
			pane.add(newJLabel);
			setContentPane(pane);
		}
	}
	

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