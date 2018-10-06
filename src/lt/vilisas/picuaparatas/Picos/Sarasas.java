package lt.vilisas.picuaparatas.Picos;


import java.util.HashMap;
import java.util.Map;
/*
 * 
 * viskam, kas savyje turi produktu sarasa. Receptas, Pica
 * 
 * 
 */
public class Sarasas{

	private Map<String,Produktas> produktai;

	public Sarasas(){
		this.produktai = new HashMap<String,Produktas>();
	}

	
	public Sarasas clone() {
		// sukuria saraso kopija
		// nuskaito produktus is sios klases map'o, ir sukisa i naujo produkto sarasa
		
		Sarasas s = new Sarasas();
		Map <String,Produktas> prod = gautiProduktuKopija();
		s.nustatytiProduktuSarasa(prod);
		return s;
	}

	protected Produktas gautiProdukta(String pavadinimas) {
		return produktai.get(pavadinimas);
	}

	public int gautiProduktoKieki(String pavadinimas) {
		Produktas p = produktai.get(pavadinimas);
		if (p != null) { 
			return p.getKiekis();
		}
		return 0; // arba galima -1, kadangi nerasta
	}
	
	protected Map<String, Produktas> gautiProduktuKopija() {
		Map<String, Produktas> produktuKopija = new HashMap<>(produktai);
		for (String key : this.produktai.keySet()) {
			produktuKopija.put(key, new Produktas(this.produktai.get(key))); // kuriam atskira produkto kopija
		}
		return produktuKopija;
	}

	public Map<String, Produktas> getProduktai() {
		return produktai;
	}	
	
	protected void nustatytiProduktuSarasa(Map<String, Produktas> produktai) {
		this.produktai = produktai;
	}
	
	public void pridetiProduktu(Sarasas sarasas) {
		//for (Produktas p : sarasas.getProduktai()) {
		this.produktai.putAll(sarasas.gautiProduktuKopija());
	}

	/**
	 * Naudojam uzpildyti aparata produktais. Sukuria naujus produkto objektus sarase 
	 * @param sarasas - produktu sarasas
	 * @param kiekis  - produkto kiekis, neziurint to, koks nurodytas sarase
	 */

	public void pridetiProduktu(Sarasas sarasas, int kiekis) {
		for (String p : sarasas.getProduktai().keySet()) {
			sukurtiProdukta(p, kiekis);;
		}
	}
	
	// grazina produkta pagal pavadinima, jei toks yra
	public Produktas rastiProdukta(String pavadinimas) {
		return produktai.get(pavadinimas);
	}	

	public void sukurtiProdukta(Produktas p) {
		//produktai.put(pavadinimas, new Produktas(pavadinimas));
		if (p==null) return;
		produktai.put(p.getPavadinimas(), p);
	}
	// sukuriam produkta
	public void sukurtiProdukta(String pavadinimas) {
		produktai.put(pavadinimas, new Produktas(pavadinimas));
	}
	
	public void sukurtiProdukta(String pavadinimas, int kiekis) {
		produktai.put(pavadinimas, new Produktas(pavadinimas, kiekis));
	}

	// ne public, vogti produktus negrazu
	protected void sumazintiProduktoKieki(String pavadinimas, int kiekis) {
		Produktas p = rastiProdukta(pavadinimas);
		if ((p != null) && (gautiProduktoKieki(pavadinimas) >= kiekis)) {
			p.setKiekis(p.getKiekis() - kiekis);
		}
	}

	@Override
	public String toString() {
		return "Sarasas [produktai=" + produktai + "]";
	}
	
}
