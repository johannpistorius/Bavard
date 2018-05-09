import java.util.ArrayList;

public class Concierge {
	ArrayList<PapotageListener> bavards;
	ArrayList<Fenetre> fenetre;
	PapotageEvent event;
	public Concierge() {
		bavards=new ArrayList<PapotageListener>();
		fenetre= new ArrayList<Fenetre>();
	}
	public void addListener(PapotageListener p) {
		bavards.add(p);
	}
	public void addFenetre(Fenetre f) {
		fenetre.add(f);
	}
	public void removeFenetre(Fenetre f) {
		fenetre.remove(f);
	}
	public void removeListener(PapotageListener p) {
		bavards.remove(p);
	}
    public void afficherBavard() {
        this.bavards.forEach(listener -> System.out.println(listener));
    }
       
    public void message(PapotageEvent m) {
    	Fenetre fa = null;
    	for(PapotageListener ba:bavards) {
        	for(Fenetre f:fenetre) {
        		if(f.b.equals(ba)) {
        			fa = f;
        		}
        	}
    		if(ba instanceof Bavard) {
	    		if(!((Bavard) ba).getPapotageEvent().contains(m)) {
	    			if(((Bavard) ba).categ.contains(m.getSujet())) {
	    				ba.addPapotageEvent(m);
	    				if(fa!=null) {
	    					fa.updateMessage();
	    				}
	    			}
	    		}else {
    				if(fa!=null && ((Bavard) ba).categ.contains(m.getSujet())) {
    					fa.updateMessage();
    				}
	    		}
    		}
    	}
    }
}
