package logika;

public class Tuple<T1, T2> {
	
	public final T1 fst;
	public final T2 snd;
	
	public Tuple(T1 fst, T2 snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	// V praksi se bo razred uporabljal npr. takole:
	// Tuple<Igralec, Koordinate> tup = new Tuple<Igralec, Koordinate>(Igralec.BLACK, koor);
	// nato pa:
	// tup.fst  ->  Igralec.BLACK
	// tup.snd  ->  koor
	
}
