package vodja;

public enum VrstaIgralca {
	
	HUMAN,
	COMP; // computer

	@Override
	public String toString() {
		switch (this) {
		case HUMAN: return "èlovek";
		case COMP: return "raèunalnik";
		default: assert false; return "";
		}
	}
	
}
