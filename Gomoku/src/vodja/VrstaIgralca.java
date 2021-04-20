package vodja;

public enum VrstaIgralca {
	R, C; 

	@Override
	public String toString() {
		switch (this) {
		case C: return "Èlovek";
		case R: return "Raèunalnik";
		default: assert false; return "";
		}
	}

}
