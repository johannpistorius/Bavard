import java.util.ArrayList;

public class Bavard implements PapotageListener{
	private String login;
	public ArrayList<PapotageEvent> papotageEvent;
	private Concierge c;
	public ArrayList<String> categ;
	public Bavard(String l, Concierge c) {
		this.login=l;
		this.c=c;
		this.papotageEvent = new ArrayList<PapotageEvent>();
		this.categ = new ArrayList<String>();
		this.categ.add("OnLineBavardEvent");
		this.categ.add("OffLineBavardEvent");
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bavard [login=" + login + " categ=" + categ + "]";
	}
	
	/**
	 * @return the papotageEvent
	 */
	public ArrayList<PapotageEvent> getPapotageEvent() {
		return papotageEvent;
	}
	/**
	 * @param papotageEvent the papotageEvent to set
	 */
	public void setPapotageEvent(ArrayList<PapotageEvent> papotageEvent) {
		this.papotageEvent = papotageEvent;
	}
	public void createPapotageEvent(String categorie, String text) {
		PapotageEvent m = new PapotageEvent(categorie, text,this);
		this.papotageEvent.add(m);	
		c.message(m);
	}
	
	public void addPapotageEvent(PapotageEvent p) {
		this.papotageEvent.add(p);
	}
	public void removePapotageEvent(PapotageEvent p) {
		this.papotageEvent.remove(p);
	}
	
	public void addCategorie(String s) {
		this.categ.add(s);
	}
	public void removeCategorie(String s) {
		this.categ.remove(s);
	}
	
}
