import java.time.LocalDateTime;

public class PapotageEvent{
	private String sujet;
	private String corps;
	private Bavard bavard;
	private LocalDateTime date;
	private String destinataire;
	public PapotageEvent(String s, String c, Bavard b, String d) {
		this.sujet=s;
		this.corps=c;
		this.bavard=b;
		this.destinataire = d;
		this.date=LocalDateTime.now();
	}
	/**
	 * @return the sujet
	 */
	public String getSujet() {
		return sujet;
	}
	/**
	 * @param sujet the sujet to set
	 */
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	/**
	 * @return the corps
	 */
	public String getCorps() {
		return corps;
	}
	/**
	 * @param corps the corps to set
	 */
	public void setCorps(String corps) {
		this.corps = corps;
	}
	/**
	 * @return the b
	 */
	public Bavard getBavard() {
		return bavard;
	}
	/**
	 * @param b the b to set
	 */
	public void setBavard(Bavard b) {
		this.bavard = b;
	}
	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 * @return the destinataire
	 */
	public String getDestinataire() {
		return destinataire;
	}
	/**
	 * @param destinataire the destinataire to set
	 */
	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PapotageEvent [sujet=" + sujet + ", corps=" + corps + ", bavard=" + bavard + ", date=" + date + "]";
	}
	
	
	
}