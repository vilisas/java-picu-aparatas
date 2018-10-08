package lt.vilisas.picuaparatas;
/*
 * Picų serviso reikalavimai:
 * Kuria picų aparatus su produktų likučiais.
 * Saugo picų receptų konfigūraciją.
 * Saugo visus sukurtus picos aparatus.
 * Galima pašalinti pasirinktą picos aparatą.
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lt.vilisas.picuaparatas.Exceptions.AparatasNepasiruosesException;
import lt.vilisas.picuaparatas.Exceptions.NepakankaProduktuException;
import lt.vilisas.picuaparatas.Exceptions.PicaException;
import lt.vilisas.picuaparatas.Picos.Pica;
import lt.vilisas.picuaparatas.Picos.Receptas;
import lt.vilisas.picuaparatas.Picos.Sarasas;

import java.util.List;

public class PicuAparatuServisas {
	public final static int DEFAULT_PRODUKTO_KIEKIS = 20; // tiek vienetu produkto telpa aparate
	private static Map<String,PicuAparatas> picuAparatai = new HashMap<>();
	private static Map<String,Receptas> receptai = new HashMap<>();

	
	public PicuAparatuServisas() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param pavadinimas
	 * @return PicuAparatas
	 */
	public static PicuAparatas sukurtiPicuAparata(String pavadinimas) {
		PicuAparatas pa = new PicuAparatas(pavadinimas);
		picuAparatai.put(pavadinimas, pa);
		return pa;
		
		
	}
	
	public static boolean sunaikintiPicuAparata(String pavadinimas) {
		if (picuAparatai.containsKey(pavadinimas)) { // tikrinimo reikia, nes removinant null objekta irgi gali grazint true
			picuAparatai.remove(pavadinimas);
			return true;
		}		
		return false;
	}
	
	public static PicuAparatas surastiPicuAparata(String pavadinimas) {
		return picuAparatai.get(pavadinimas);	
	}
	
	public static String gautiPicuAparatoBusena(String pavadinimas) {
		return surastiPicuAparata(pavadinimas).toString();
	}
	
	
	public static void pridetiRecepta(String pavadinimas) {
		receptai.put(pavadinimas, new Receptas(pavadinimas));
	}
	public static void pridetiRecepta(Receptas receptas) {
		if (receptas != null) {
			receptai.put(receptas.getPavadinimas(), receptas);
		}
	}
	public static Receptas gautiRecepta(String pavadinimas) {
		return (receptai.get(pavadinimas));
	}
	
	public static boolean pridetiProduktaIRecepta(String receptas, String pavadinimas, int kiekis) {
		Receptas r = gautiRecepta(receptas);
		if (r != null) {
			r.sukurtiProdukta(pavadinimas, kiekis);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param aparatoPavadinimas	- Picos aparato pavadinimas
	 * @param sarasas				- produktu sarasas / receptas
	 * @return						- true, jei uzpilde
	 */
	public static boolean uzpildytiAparataProduktais(String aparatoPavadinimas, Sarasas sarasas) {
		if (aparatoPavadinimas.equals("") || (sarasas == null)) {
			return false;
		}
		PicuAparatas pa = surastiPicuAparata(aparatoPavadinimas);
		if (pa == null) {
			// nera tokio aparato
			return false;
		}
		pa.pridetiProduktu(sarasas, DEFAULT_PRODUKTO_KIEKIS);
		return true;
	}

	public static boolean uzpildytiAparataProduktais(String aparatoPavadinimas, String receptoPavadinimas) {
		if (aparatoPavadinimas.equals("") || (receptoPavadinimas.equals(""))) {
			return false;
		}
		PicuAparatas pa = surastiPicuAparata(aparatoPavadinimas);
		if (pa == null) {
			// nera tokio aparato
			return false;
		}
		if (gautiRecepta(receptoPavadinimas) == null) {
			return false;
		}
		
		pa.pridetiProduktu(gautiRecepta(receptoPavadinimas), DEFAULT_PRODUKTO_KIEKIS);
		return true;
	}

	public static boolean uzpildytiAparataProduktu(String aparatoPavadinimas, String produktas) {
		if (aparatoPavadinimas.equals("") || produktas.equals("")) {
			return false;
		}
		PicuAparatas pa = surastiPicuAparata(aparatoPavadinimas);
		if (pa == null) {
			// nera tokio aparato
			return false;
		}
		pa.sukurtiProdukta(produktas,DEFAULT_PRODUKTO_KIEKIS);
		
		return true;
	}
	
	
	
	public static Set<String> gautiReceptuSarasa() {
		Set<String> keys = receptai.keySet();
		return keys;
		
	}

	public static Set<String> gautiAparatuSarasa() {
		Set<String> keys = picuAparatai.keySet();
		return keys;
	}
	
	public static List<String> galimuPicuSarasas(PicuAparatas pa, int dydis) {
		List<String> l = new ArrayList<String>();
		for (String pavadinimas : gautiReceptuSarasa()) {
			if(pa.arPakankaProduktu(gautiRecepta(pavadinimas), dydis)){
				l.add(pavadinimas);
			}
		}
		
		return l;
	}
	
	public static Pica patiektiPica(String picuAparatas, String picosPavadinimas, int dydis) throws PicaException {
		
		Pica p;
		try {
			p = surastiPicuAparata(picuAparatas).gamintiPica(gautiRecepta(picosPavadinimas),  dydis);
			return p;
		} catch (AparatasNepasiruosesException e) {
			System.out.print("*** Aparatas nepasiruoses");
			if (e.gautiSkaitliuka() <= 0) {
				System.out.println(" - ji reikia isvalyti");
				throw e;
			}
			System.out.println();
		} catch (NepakankaProduktuException e) {
			System.out.println("Picai pagaminti truksta produkto " + e.gautiProduktoPavadinima());
			throw e;
		} catch (PicaException e) {
			System.out.println("Picos pagaminti nepavyko " + e.getCause());
		}
		return new Pica(picosPavadinimas,dydis);
	}
	
	public static void valytiVisusAparatus() {
		for(String pavadinimas:gautiAparatuSarasa()) {
			valytiAparata(pavadinimas);
		}
	}

	public static void valytiAparata(String pavadinimas) {
		System.out.println("Valau aparata "+ pavadinimas);
		surastiPicuAparata(pavadinimas).valytiAparata();
	}
	
	
	

}
