package vodja;

public enum VrstaIgralca {
	
	HUMAN,
	COMP; // computer

	@Override
	public String toString() {
		switch (this) {
		case HUMAN: return "človek";
		case COMP: return "računalnik";
		default: assert false; return "";
		}
	}
	
}
