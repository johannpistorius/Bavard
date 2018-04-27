import java.util.ArrayList;

public class Bavard {
	private String login;
	public ArrayList<PapotageEvent> papotageEvent;
	private Concierge c;
	public Bavard(String l, Concierge c) {
		this.login=l;
		this.c=c;
		this.papotageEvent = new ArrayList<PapotageEvent>();
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
		return "Bavard [login=" + login + "]";
	}
	
	public void createPapotageEvent(String categorie, String text) {
		this.papotageEvent.add(new PapotageEvent(categorie, text,this));
		
	}
	public void addPapotageEvent(PapotageEvent p) {
		this.papotageEvent.add(p);
	}
	
}
