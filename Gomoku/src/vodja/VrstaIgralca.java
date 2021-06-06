package vodja;

public enum VrstaIgralca {
	
	HUMAN,
	COMP; // computer

	@Override
	public String toString() {
		switch (this) {
		case HUMAN: return "�lovek";
		case COMP: return "ra�unalnik";
		default: assert false; return "";
		}
	}
	
}
