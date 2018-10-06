package lt.vilisas.keptuve;

import java.util.InputMismatchException;
import java.util.Scanner;
import lt.vilisas.picuaparatas.*;
import lt.vilisas.picuaparatas.Exceptions.NepakankaProduktuException;
import lt.vilisas.picuaparatas.Exceptions.PicaException;
import lt.vilisas.picuaparatas.Picos.Pica;
import lt.vilisas.picuaparatas.Picos.Receptas;

/*
 * TODO: sutvarkyti skaiciu input'a, kai ivedama neteisinga reiksme
 * 
 */
public class Main {
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {

		sukurkPicosAparatus();
		kurkReceptus();
		uzpildykVisusAparatusProduktais();
		
//		parodytiAparatuBusenas();
//		parodytiPicuAparatus();
//		parodytiReceptus();
		
		boolean pabaiga = false;
		while(!pabaiga) {
			System.out.println("Ar norite picos? 0 - ne, 1 - taip, 2 - apziureti aparatus, 3 - valyti, 4- uzpildyti"); 
			int	response = gautiSkaiciu(0);
			switch(response) {
			
			case 0:
				baigtiDarba();
				break;
			case 1:
				String pasirinkimas;
				PicuAparatas pa = pasirinktiPicuAparata();
				if (pa == null ) {
					break;
				}
				Receptas r = pasirinktiRecepta(pa);
				if (r == null) {
					break;
				}
//				System.out.print("Nurodykite dydi (1-3) arba didesne");
//				int dydis = gautiSkaiciu(0);
				int dydis = pasirinktiPicosDydi();
				if (dydis <=0) {
					break;
				}
				Pica p;
				try {
					p = PicuAparatuServisas.patiektiPica(pa.getPavadinimas(), r.getPavadinimas(), dydis);
					if (p.arPicaPagaminta()) {
						System.out.println("Pica pagaminta!");
					}
					System.out.println(p.toString());
				} catch (NepakankaProduktuException e) {
					System.out.println("pameginkite kita pica ar dydi");
				} catch (PicaException e) {
					System.out.println("nepavyko pagaminti picos: "+e.getCause());
				}
				
				break;
			case 2:
				parodytiAparatuBusenas();
				break;
			case 3:
				PicuAparatuServisas.valytiVisusAparatus();
				break;
			case 4:
				uzpildykVisusAparatusProduktais();
				break;
			default:
				System.out.println("blogas pasirinkimas");
			}
		}
	}

	private static int pasirinktiPicosDydi() {
		int dydis=0;
		do {
			System.out.println("Nurodykite dydi (1 maza, 2 - didele, 3 - XXL,  0 - atsaukti)");
			dydis = sc.nextInt();
		} while (dydis <0 || dydis > 3);
		return dydis;
	}
	private static Receptas pasirinktiRecepta(PicuAparatas pa) {
		Receptas receptas;
		do {
		System.out.println("Pasirinkite pica (arba x)");
		parodytiReceptus(pa);
		String pasirinkimas = gautiTeksta(">");
		
		if (pasirinkimas.equals("x")) {
			return null;
		}
		receptas = PicuAparatuServisas.gautiRecepta(pasirinkimas);
		} while (receptas == null);
		return receptas;
	}


	private static PicuAparatas pasirinktiPicuAparata() {
		String pasirinkimas;
		PicuAparatas pa;
		do {
		System.out.println("Pasirinkite aparata (arba x)");
		parodytiPicuAparatus();
		pasirinkimas = gautiTeksta(">");
		if (pasirinkimas.equals("x")) {
			return null;
		}
		pa = PicuAparatuServisas.surastiPicuAparata(pasirinkimas);
		} while (pa == null);
		return pa;

	}
	
	private static String gautiTeksta(String tekstas) {
		System.out.print(tekstas);
		return sc.next();
	}
	private static int gautiSkaiciu(int minimum) {
		int response = -1;
		try {
			do {
				System.out.print(">");
				response = sc.nextInt();
				if (response <minimum) {
					System.out.println("\nskaicius turi ne mazesnis uz " + minimum);
				}
			} while (response < 0);
		} catch (InputMismatchException e) {
			System.out.println("\natsakymas turi buti teigiamas sveikasis skaicius");
			sc.next(); // reikia nuskaityti
		}
		
		return response;
	}
	private static void baigtiDarba() {
		PicuAparatuServisas.gautiAparatuSarasa().clear();
		sc.close();
		System.out.println("C ya!");
		System.exit(0);
	}
	private static void parodytiPicuAparatus() {
		System.out.println(PicuAparatuServisas.gautiAparatuSarasa().toString());		
	}
	private static void parodytiReceptus() {
		System.out.println(PicuAparatuServisas.gautiReceptuSarasa().toString());
	}

	private static void parodytiReceptus(PicuAparatas pa) {
		for (String pavadinimas : PicuAparatuServisas.gautiReceptuSarasa()) {
			if (pa.arPakankaProduktu(PicuAparatuServisas.gautiRecepta(pavadinimas), 1)) {
				System.out.print(pavadinimas + " ");
			}
		}
	}
	
	private static void parodytiAparatuBusenas() {
		for (String pavadinimas :PicuAparatuServisas.gautiAparatuSarasa()) {
			System.out.println(pavadinimas);
			System.out.println(PicuAparatuServisas.surastiPicuAparata(pavadinimas).toString());
		}
	}
	private static void uzpildykVisusAparatusProduktais() {		
		
		PicuAparatuServisas.uzpildytiAparataProduktais("visiems", "robotu");
		PicuAparatuServisas.uzpildytiAparataProduktais("visiems", "surio");
		PicuAparatuServisas.uzpildytiAparataProduktais("visiems", "astri");
		
		PicuAparatuServisas.uzpildytiAparataProduktais("robotams", "robotu");
		PicuAparatuServisas.uzpildytiAparataProduktais("zmonems", "surio");
		PicuAparatuServisas.uzpildytiAparataProduktais("zmonems", "astri");		
		
	}
	private static void sukurkPicosAparatus() {
		PicuAparatuServisas.sukurtiPicuAparata("zmonems");
		PicuAparatuServisas.sukurtiPicuAparata("robotams");
		PicuAparatuServisas.sukurtiPicuAparata("visiems");

	}
	private static void kurkReceptus() {
		// kuriam recepta taip:
		PicuAparatuServisas.pridetiRecepta("surio");
		PicuAparatuServisas.pridetiProduktaIRecepta("surio", "suris", 5);
		PicuAparatuServisas.pridetiProduktaIRecepta("surio", "padas", 1);
		PicuAparatuServisas.pridetiProduktaIRecepta("surio", "padazas", 3);
		
		// arba taip:
		Receptas astriosPicosReceptas = new Receptas("astri");
		astriosPicosReceptas.sukurtiProdukta("padas", 1);
		astriosPicosReceptas.sukurtiProdukta("desra", 5);
		astriosPicosReceptas.sukurtiProdukta("suris", 5);
		astriosPicosReceptas.sukurtiProdukta("pipirai", 6);
		astriosPicosReceptas.sukurtiProdukta("padazas", 4);
		PicuAparatuServisas.pridetiRecepta(astriosPicosReceptas);	// pridedam i sarasa

		Receptas robotuPicosReceptas = new Receptas("robotu");
		robotuPicosReceptas.sukurtiProdukta("skarda",1);
		robotuPicosReceptas.sukurtiProdukta("varztai",5);
		robotuPicosReceptas.sukurtiProdukta("vinys",6);
		robotuPicosReceptas.sukurtiProdukta("metalo drozles",5);
		robotuPicosReceptas.sukurtiProdukta("solidolas",13);
		robotuPicosReceptas.sukurtiProdukta("solidolas",3);	// tik vienas produktas tuo paciu raktu
		PicuAparatuServisas.pridetiRecepta(robotuPicosReceptas);
	}

	
}
