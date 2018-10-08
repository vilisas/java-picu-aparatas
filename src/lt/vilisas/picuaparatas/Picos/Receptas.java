package lt.vilisas.picuaparatas.Picos;

import java.util.Map;

public class Receptas extends Sarasas {
	private String pavadinimas;	// Recepto pavadinimas

	public Receptas() {
		super();
		setPavadinimas("");
	}
	
	public Receptas(String pavadinimas) {
		this();
		setPavadinimas(pavadinimas);
	}
			
	public Receptas(String pavadinimas2, Map<String, Produktas> produktai) {
		// TODO Auto-generated constructor stub
		this(pavadinimas2);
		this.nustatytiProduktuSarasa(produktai);
	}
	
	@Override
	public Receptas clone() {
		return new Receptas(this.getPavadinimas(), this.gautiProduktuKopija());
	}
	
	public String getPavadinimas() {
		return pavadinimas;
	}
	
	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	@Override
	public String toString() {
		return "Receptas [pavadinimas=" + pavadinimas + "] -> " + super.toString();
	}
	
}
