package lt.vilisas.picuaparatas.Picos;
/*
 * Pica turi produktus, atitinkama ju kieki pagal dydi
 * Pavadinima
 * Dydi
 * 
 */


public class Pica extends Sarasas{
	private String pavadinimas;
	private int dydis;		// 1=Maza, 2=Didele, 3=Seimynine
	private boolean picaPagaminta = false;
//	private Map<String,Produktas> produktai;   (klaseje Sarasas)

	public Pica(String pavadinimas) {
		super();
		this.pavadinimas = pavadinimas;
	}
	
	public Pica(String pavadinimas, int dydis) {
		this(pavadinimas);
		this.dydis = dydis;
	}
	
	public Pica(String pavadinimas, int dydis, Sarasas s) {
		this(pavadinimas);
		this.dydis = dydis;
		nustatytiProduktuSarasa(s.getProduktai());
	}
		
	public String getPavadinimas() {
		return pavadinimas;
	}
	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}
	public int getDydis() {
		return dydis;
	}
	public void setDydis(int dydis) {
		this.dydis = dydis;
	}

	public boolean arPicaPagaminta() {
		return picaPagaminta;
	}

	public void setPicaPagaminta(boolean picaPagaminta) {
		this.picaPagaminta = picaPagaminta;
	}

	@Override
	public String toString() {
		return "Pica [pavadinimas=" + pavadinimas + ", dydis=" + dydis + ", picaPagaminta=" + picaPagaminta
				+ ", getProduktai()=" + getProduktai() + "]";
	}

	
}
