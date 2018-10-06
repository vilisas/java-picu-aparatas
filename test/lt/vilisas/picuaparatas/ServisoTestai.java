package lt.vilisas.picuaparatas;

import lt.vilisas.picuaparatas.*;
import lt.vilisas.picuaparatas.Exceptions.PicaException;
import lt.vilisas.picuaparatas.Picos.Pica;
import lt.vilisas.picuaparatas.Picos.Receptas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ServisoTestai {

	@Test
	void sukurtiAparata() {
		PicuAparatas pa = PicuAparatuServisas.sukurtiPicuAparata("aparatas");
		assertNotNull(pa);
		assertEquals(pa.getPavadinimas(), "aparatas");
		assertEquals(PicuAparatuServisas.surastiPicuAparata("aparatas"), pa);
		assertNull(PicuAparatuServisas.surastiPicuAparata("nera tokio"));
	}
	
	@Test
	void sunaikintiAparata() {
		sukurtiAparata();		// kvieciam, nes nezinome kokia tvarka testai bus atlikti.
		PicuAparatuServisas.sukurtiPicuAparata("aparatas2");
		assertEquals(PicuAparatuServisas.gautiAparatuSarasa().size(), 2);
		assertEquals(PicuAparatuServisas.sunaikintiPicuAparata("aparatas2"), true);    // sunaikina
		assertEquals(PicuAparatuServisas.sunaikintiPicuAparata("aparatas2"), false);   // jau nebera
		assertEquals(PicuAparatuServisas.sunaikintiPicuAparata("nera tokio2"), false); // net nebuvo
	}
	@Test
	void pridetiRecepta() {
		PicuAparatuServisas.pridetiRecepta("surio");
		Receptas r = PicuAparatuServisas.gautiRecepta("surio");
		assertNotNull(r);
		assertEquals(r.getPavadinimas(), "surio");
		assertNull(PicuAparatuServisas.gautiRecepta("nera tokio"));
		assertEquals(PicuAparatuServisas.gautiReceptuSarasa().size(), 1);
		Receptas r2 = r.clone();
		r2.setPavadinimas("surio2");
		PicuAparatuServisas.pridetiRecepta(r2);
		assertEquals(PicuAparatuServisas.gautiReceptuSarasa().size(), 2);		
		assertNotNull(PicuAparatuServisas.gautiRecepta("surio2"));
		assertEquals(PicuAparatuServisas.pridetiProduktaIRecepta("surio", "suris", 5), true);
		assertEquals(PicuAparatuServisas.pridetiProduktaIRecepta("surio", "padas", 1), true);
		
		assertEquals(PicuAparatuServisas.gautiRecepta("surio").gautiProduktoKieki("suris"), 5);
		// jei .clone veikia (kopija), tai kitos picos recepte produkto kiekis nepadides, jei link'as - padides
		assertEquals(PicuAparatuServisas.gautiRecepta("surio2").gautiProduktoKieki("suris"), 0);
	}
	
	@Test
	Receptas gautiSurioPicosRecepta() {
		Receptas r = new Receptas("surio");
		r.sukurtiProdukta("padas", 1);
		r.sukurtiProdukta("suris", 5);
		
		
		assertEquals(r.getPavadinimas(),"surio");
		assertEquals(r.gautiProduktoKieki("padas"), 1);
		assertEquals(r.gautiProduktoKieki("suris"), 5);
		
		return r;
	}
	@Test
	void uzpildytiAparataProduktais() {
		PicuAparatas pa = PicuAparatuServisas.sukurtiPicuAparata("aparatas");
		Receptas spr = gautiSurioPicosRecepta();
		assertEquals(PicuAparatuServisas.uzpildytiAparataProduktais("aparatas", spr), true);
		assertEquals(PicuAparatuServisas.uzpildytiAparataProduktais("aparatassss", spr), false);
		assertEquals(PicuAparatuServisas.surastiPicuAparata("aparatas").gautiProduktoKieki("suris"), PicuAparatuServisas.DEFAULT_PRODUKTO_KIEKIS);
		assertEquals(PicuAparatuServisas.surastiPicuAparata("aparatas").gautiProduktoKieki("padas"), PicuAparatuServisas.DEFAULT_PRODUKTO_KIEKIS);
		assertEquals(PicuAparatuServisas.surastiPicuAparata("aparatas").gautiProduktoKieki("varztai"), 0);
	}
	@Test
	void patiektiPica() {
		PicuAparatas pa = PicuAparatuServisas.sukurtiPicuAparata("aparatas");
		PicuAparatuServisas.uzpildytiAparataProduktais("aparatas", gautiSurioPicosRecepta());
		
		Pica p = null;
		try {
		p = PicuAparatuServisas.patiektiPica("aparatas", "surio", 1);
		} catch(PicaException e){
			fail("PicaException occured" + e.getCause());
		}
		
		assertEquals(p.arPicaPagaminta(), true);
		try {
			assertEquals(PicuAparatuServisas.patiektiPica("aparatas", "surio", 3).arPicaPagaminta(), true);
		} catch (PicaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// baigesi suris
		try {
//			assertEquals(
					PicuAparatuServisas.patiektiPica("aparatas", "surio", 3).arPicaPagaminta();
					fail("maza surio!");
		} catch (PicaException e) {
			System.out.println("This exception was expected");
		} 
	}
	
	@Test
	void aparatoSkaitliukoTestas() {
		PicuAparatas aparatas = PicuAparatuServisas.sukurtiPicuAparata("aparatas");
		Receptas surioPicosReceptas = gautiSurioPicosRecepta();
		for (int i = 1; i< aparatas.PANAUDOJIMU_SKAICIAUS_RIBA; i++) {
			PicuAparatuServisas.uzpildytiAparataProduktais("aparatas", surioPicosReceptas);
			try {
				PicuAparatuServisas.patiektiPica("aparatas", "surio", 1);
			} catch (PicaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(e.getCause());
			}
			assertEquals(aparatas.arPasiruoses(), true);
//			System.out.println(i);
		}
		assertEquals(aparatas.arPasiruoses(), true);
		try {
			assertEquals(PicuAparatuServisas.patiektiPica("aparatas", "surio", 1).arPicaPagaminta(), true);
		} catch (PicaException e) {
			e.printStackTrace();
			fail("Exception occured");
		}
		assertEquals(aparatas.arPasiruoses(), false);
		aparatas.valytiAparata();
		assertEquals(aparatas.arPasiruoses(), true);	
		
	}
	
	
}
