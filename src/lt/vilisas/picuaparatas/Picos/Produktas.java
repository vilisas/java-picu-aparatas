package lt.vilisas.picuaparatas.Picos;

public class Produktas {
	private String pavadinimas;
	private int kiekis;

	public Produktas(String pavadinimas) {
		this(pavadinimas, 0);
	}

	public Produktas(String pavadinimas, int kiekis) {
		setPavadinimas(pavadinimas);
		setKiekis(kiekis);
	}

	public Produktas(Produktas produktas) {
		this(produktas.getPavadinimas(), produktas.getKiekis());
	}

	public Produktas() {
		this("", 0);
	}

	public Produktas(int kiekis) {
		this("", kiekis);
	}

	
	public String getPavadinimas() {
		return pavadinimas;
	}

	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	public int getKiekis() {
		return kiekis;
	}

	public void setKiekis(int kiekis) {
		this.kiekis = kiekis;
	}

	@Override
	public Produktas clone() {
		return (new Produktas(getPavadinimas(), getKiekis()));
	}

	@Override
	public String toString() {
		return "Produktas [pavadinimas='" + pavadinimas + "', kiekis=" + kiekis + "]";
	}

	

	

}
