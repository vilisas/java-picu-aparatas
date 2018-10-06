package lt.vilisas.picuaparatas.Picos;

import java.util.Map;

public class Receptas extends Sarasas {
	private String pavadinimas;	// Recepto pavadinimas

	
	public String getPavadinimas() {
		return pavadinimas;
	}
	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}
			
	public Receptas() {
		super();
		setPavadinimas("");
	}
	
	public Receptas(String pavadinimas) {
		this();
		setPavadinimas(pavadinimas);
	}

//	public Receptas(String pavadinimas, ArrayList<Produktas> produktai) {
//		this(pavadinimas);
//		for (Produktas p : produktai) {
//			sukurtiProdukta(p);
//		}
//	}
//	

	
	public Receptas(String pavadinimas2, Map<String, Produktas> produktai) {
		// TODO Auto-generated constructor stub
		this(pavadinimas2);
		this.nustatytiProduktuSarasa(produktai);
		
	}
	@Override
	public String toString() {
		return "Receptas [pavadinimas=" + pavadinimas + "] -> " + super.toString();
	}

	@Override
	public Receptas clone() {
		return new Receptas(this.getPavadinimas(), this.gautiProduktuKopija());
		
	}
	
}
