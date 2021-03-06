import java.util.ArrayList;

public class Concierge {
	ArrayList<PapotageListener> bavards;
	ArrayList<Fenetre> fenetre;
	PapotageEvent event;
	public Concierge() {
		bavards=new ArrayList<PapotageListener>();
		fenetre= new ArrayList<Fenetre>();
	}
	/**
	 * Add listener to Bavard
	 * @param p
	 */
	public void addListener(PapotageListener p) {
		bavards.add(p);
	}
	/**
	 * Add a GUI
	 * @param f
	 */
	public void addFenetre(Fenetre f) {
		fenetre.add(f);
	}
	/**
	 * Remove a GUI
	 * @param f
	 */
	public void removeFenetre(Fenetre f) {
		fenetre.remove(f);
	}
	/**
	 * Remove listener to a Bavard
	 * @param p
	 */
	public void removeListener(PapotageListener p) {
		bavards.remove(p);
	}
	/**
	 * Show Bavard
	 */
    public void afficherBavard() {
        this.bavards.forEach(listener -> System.out.println(listener));
    }
    /**
     * Send message to all the Bavard   
     * @param m
     */
    public void message(PapotageEvent m) {
    	Fenetre fa = null;	
    	for(PapotageListener ba:bavards) {
        	for(Fenetre f:fenetre) {
        		if(f.b.equals(ba)) {
        			fa = f;
            		if(ba instanceof Bavard) {
            			if(((Bavard) ba).categ.contains(m.getSujet())) {
            				if( m.getDestinataire().equals("All")){
            					if(!((Bavard) ba).getPapotageEvent().contains(m)) {
            						ba.addPapotageEvent(m);
            					}	
            					fa.updateMessage();
                				fa.comboUtil();
            				}
            			}
            			if(((Bavard) ba).getLogin().equals(m.getDestinataire())) {
        					ba.addPapotageEvent(m);
        					fa.updateMessage();
            				fa.comboUtil();
        				} 
            			if( ((Bavard) ba).getLogin().equals(m.getBavard().getLogin()) && !m.getDestinataire().equals("All")){
        					fa.updateMessage();
            				fa.comboUtil();
        				}
            			
            		}
        		}
        	}	
    	}
    }
}
