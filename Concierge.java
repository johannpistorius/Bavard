import java.util.ArrayList;

public class Concierge {
	ArrayList<Bavard> bavards;
	ArrayList<Fenetre> fenetre;
	PapotageEvent event;
	public Concierge() {
		bavards=new ArrayList<Bavard>();
		fenetre= new ArrayList<Fenetre>();
	}
	public void addListener(Bavard b) {
		bavards.add(b);
	}
	public void addFenetre(Fenetre f) {
		fenetre.add(f);
	}
    public void afficherBavard() {
        this.bavards.forEach(listener -> System.out.println(listener));
    }
    
    public void update() {
    	for(Fenetre f:fenetre) {
    		f.updateMessage();
    	}
    }
    
    public void message(Bavard b) {
    	PapotageEvent lastElement = b.papotageEvent.get(b.papotageEvent.size()-1);
    	for(Bavard ba:bavards) {
    		if(!ba.equals(b)) {
    			ba.addPapotageEvent(lastElement);
    		}
    	}
    	update();
    }
}
