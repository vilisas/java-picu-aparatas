package lt.vilisas.picuaparatas;

import java.util.Map;

import lt.vilisas.picuaparatas.Exceptions.AparatasNepasiruosesException;
import lt.vilisas.picuaparatas.Exceptions.NepakankaProduktuException;
import lt.vilisas.picuaparatas.Exceptions.PicaException;
import lt.vilisas.picuaparatas.Picos.Pica;
import lt.vilisas.picuaparatas.Picos.Produktas;
import lt.vilisas.picuaparatas.Picos.Receptas;
import lt.vilisas.picuaparatas.Picos.Sarasas;

/*
 * Picų aparato reikalavimai:
 * Picos gaminamos iš produktų: sūris, padas, padažas, dešra, pomidoras ir t.t. Produktai skaičiuojami vienetais.
 * Picos kepamos pagal receptus. Vieną receptą sudaro koks nors produktų rinkinys.
 * Gaminant picą sumažinami produktų kiekiai ir grąžinama pica.
 * Picos gali būti trijų dydžių.
 * Galima pasirinkti kokio recepto ir dydžio pica bus pagaminta.
 * Reikia galimybės valyti aparatą, nes kas 20 picos kepimą aparatas turi paprašyti valymo.
 * Jeigu trūksta produktų pasirinktai picai, pasiūlyti kokią nors kitą picą. 
*/

/*
 * 
 * Picu aparatas priima recepta, grazina pica
 * Picu aparatas saugo produktu kieki, skaiciuoja pagamintas picas, gamina 3 dydziu picas
 * 
 * Tam, kad picu aparatas galetu pasiulyti recepta, reikia zinoti visus receptus.
 * Kadangi servisas zino visus receptus, tai PicuAparatas tik kepa picas arba patikrina, ar pakanka produktu picai. 
 * Galimu picu sarasu rupinasi front-end'as - jis gali paklausti, ar picu aparatas gali iskepti nurodyto dydzio pica
 * pagal nurodyta recepta.
 * 
 */

public class PicuAparatas extends Sarasas {
	
	public final static int PANAUDOJIMU_SKAICIAUS_RIBA = 20;
//	private ArrayList<Produktas> produktai;
	private int pagamintaPicu = 0;
	private int valymoSkaitliukas = PANAUDOJIMU_SKAICIAUS_RIBA;
	private String pavadinimas;	// picos pavadinimas
	
	public String getPavadinimas() {
		return pavadinimas;
	}

	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	public PicuAparatas() {
		super();
	}
	
	public PicuAparatas(String pavadinimas) {
		this();
		setPavadinimas(pavadinimas);
	}
	
	/**
	 * patikrina, ar pakanka produktu aparate picai, pagal nurodyta recepta pagaminti
	 * @param receptas
	 * @param dydis
	 * @return - true - pakanka, false - ne
	 */
	public boolean arPakankaProduktu(Sarasas receptas, int dydis) {
		Map<String, Produktas> rps = receptas.getProduktai();	//recepto produktu sarasas, kodo iskaitomumui
		// pradzioje patikrinam, ar pakanka produktu, jei ne - return null ( & throw exception ? )
		for (String pavadinimas : rps.keySet()) {
				int kiekis = rps.get(pavadinimas).getKiekis() * dydis;
				if (this.gautiProduktoKieki(pavadinimas) < kiekis) {
					return false;
				}
			}
		return true;
	}
	public Pica gamintiPica(Receptas receptas, int dydis) throws PicaException {
		/*
		 * pasitikrinam, ar galim gaminti pica
		 * i Pica sudeti sukraunam kiekius, kiek ir kokiu produktu sudeta
		 */
		if (receptas == null) {
			System.out.println("Nera tokio recepto!");
			return null;
		}
		
		if (!arPasiruoses()) {
			throw new AparatasNepasiruosesException(gautiValymoSkaitliuka());
		}
		
		
		// front end'as arba servisas turetu sutikrinti dydi 
//		if ((dydis <1 || dydis >3)) {
//			throw new PicosDydisException(dydis);
//		}

		// sutikrina, ar pakanka produktu pasirinktai picai ir dydziui pagaminti
		// tikrinam kieki aparate, pagal recepte nurodyta produkto pavadinima ir reikalinga kieki dauginant is dydzio 
		// jei nepakanka, metodas grazina null
		
		Pica pica = new Pica(receptas.getPavadinimas(), dydis);
		pica.setPicaPagaminta(false);
		
		// pradzioje patikrinam, ar pakanka produktu, jei ne - return null ( & throw exception ? )
		boolean produktuPakanka = arPakankaProduktu(receptas, dydis);

		// jei picos pagaminti nepavyko - griztam ir produktu nenuminusuojam
		if (!produktuPakanka) {
			throw new NepakankaProduktuException(pavadinimas);
		}
		
		Map<String, Produktas> rps = receptas.getProduktai();	//recepto produktu sarasas, kodo iskaitomumui
		// numinusuojam produktus is aparato
		for (String key : rps.keySet()) {
			int kiekis = rps.get(key).getKiekis() * dydis;
				this.sumazintiProduktoKieki(key, kiekis);
				pica.sukurtiProdukta(key, kiekis);
			}		
		pica.setPicaPagaminta(produktuPakanka);
		
		this.pagamintaPicu++;
		this.valymoSkaitliukas--;
		return pica;		
	}
			
	public boolean arPasiruoses() {
		return(valymoSkaitliukas >0);
	}
	
	protected void valytiAparata() {
		this.valymoSkaitliukas = PANAUDOJIMU_SKAICIAUS_RIBA;
	}

	protected int gautiPagamintuPicuSkaiciu() {
		return pagamintaPicu;
	}

	protected int gautiValymoSkaitliuka() {
		return valymoSkaitliukas;
	}
	
	@Override
	public String toString() {
		return "PicuAparatas [arPasiruoses()=" + arPasiruoses() + ", pavadinimas=" + pavadinimas + ", pagamintaPicu="
				+ pagamintaPicu + ", valymoSkaitliukas=" + valymoSkaitliukas + ", getProduktai()=" + getProduktai()
				+ "]";
	}
	
	

}
