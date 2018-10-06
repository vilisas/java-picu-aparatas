package lt.vilisas.picuaparatas;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.*;
import org.junit.jupiter.api.Test;

import lt.vilisas.picuaparatas.PicuAparatas;
import lt.vilisas.picuaparatas.Exceptions.PicaException;
import lt.vilisas.picuaparatas.Picos.Pica;
import lt.vilisas.picuaparatas.Picos.Produktas;
import lt.vilisas.picuaparatas.Picos.Receptas;
import lt.vilisas.picuaparatas.Picos.Sarasas;
/*
 * pirmas zaidimas su testais siaip..
 */
class Testai {

	Receptas r, r2, surioPicosReceptas;
	PicuAparatas pa, pa1;
	Pica p;
	
	@Before
	public void setUp() {
		surioPicosReceptas = new Receptas("Surio");
		surioPicosReceptas.sukurtiProdukta("padas", 1);
		surioPicosReceptas.sukurtiProdukta("suris", 5);
		pa = new PicuAparatas("aparatas");
	
	}
	@After
	public void tearDown() {
		
	}
	
	@Test
	void test() {
//		fail("Not yet implemented");
		Sarasas s = new Sarasas();
		assertNotNull(s);
		assertNotNull(new Produktas());
		assertNotNull(new Produktas(158));
		assertNotNull(new Produktas("desra"));
		
		Produktas p = new Produktas("desra", 249);
		assertNotNull(p);
		assertEquals(p.getPavadinimas(), "desra");
		assertEquals(p.getKiekis(), 249);
		p.setKiekis(158);
		assertEquals(p.getKiekis(), 158);
		
//		Receptas
		r = new Receptas("ROBOTU");
		
		r.sukurtiProdukta("skarda",1);
		r.sukurtiProdukta("varztai",5);
		r.sukurtiProdukta("vinys",6);
		r.sukurtiProdukta("metalo drozles",5);
		r.sukurtiProdukta("solidolas",13);
		r.sukurtiProdukta("solidolas",3);	// check

//		Receptas 
		r2 = new Receptas("Meksikietiska");
		r2.sukurtiProdukta("padas", 1);
		r2.sukurtiProdukta("desra", 5);
		r2.sukurtiProdukta("suris", 4);
		r2.sukurtiProdukta("pipirai", 6);
		r2.sukurtiProdukta("padazas", 4);		
		
//		Receptas 
		surioPicosReceptas = new Receptas("Surio");
		surioPicosReceptas.sukurtiProdukta("padas", 1);
		surioPicosReceptas.sukurtiProdukta("suris", 5);
		
		assertEquals(surioPicosReceptas.getPavadinimas(), "Surio");
		
		
		assertEquals(r2.gautiProduktoKieki("suris"), 4);
		assertEquals(surioPicosReceptas.gautiProduktoKieki("suris"), 5);
		
		Receptas r4 = surioPicosReceptas.clone();
		
		assertEquals(r4.gautiProduktoKieki("padas"), 1);
		assertEquals(r4.gautiProduktoKieki("suris"), surioPicosReceptas.gautiProduktoKieki("suris"));
		
	}
	
	@Test
	void picuAparatas() {
		setUp();
//		PicuAparatas 
		assertNotNull(pa);
		assertEquals(pa.getPavadinimas(), "aparatas");
	
		assertNotNull(surioPicosReceptas);
		pa.pridetiProduktu(surioPicosReceptas);
		assertEquals(pa.gautiProduktoKieki("padas"), 1);
		assertEquals(pa.gautiProduktoKieki("suris"), 5);
		pa.pridetiProduktu(surioPicosReceptas,20);
		assertEquals(pa.gautiProduktoKieki("padas"), 20);
		assertEquals(pa.gautiProduktoKieki("suris"), 20);
	}

	
	
	Pica keptiPicaPavyko(PicuAparatas pa, Receptas receptas, int dydis) {
		Pica p=null;
	try {
		p = pa.gamintiPica(receptas, dydis);
	} 
	catch(PicaException e) {
		fail("Picos iskepti nepavyko");
	}
	return p;
	}
	

	Pica keptiPicosNepavyko(PicuAparatas pa, Receptas receptas, int dydis) {
		Pica p=null;
	try {
		p = pa.gamintiPica(receptas, dydis);
		fail("Picos iskepti turejo nepavykti");
	} 
	catch(PicaException e) {
	
	}
	return p;
	}
	
	@Test
	void keptiPica() {
		setUp();
		//Pica p = pa.gamintiPica(r2, 1);
		Pica p = keptiPicaPavyko(pa, r2, 1);
		
		assertNull(p, "Jei receptas null - pica grazina null");
		p = keptiPicaPavyko(pa, r2, 0);
		assertNull(p, "Dydis turi buti 1-3, kitiap grazina null");
		assertEquals(pa.arPasiruoses(), true);
		assertEquals(pa.gautiValymoSkaitliuka(), 20);
		assertEquals(pa.gautiPagamintuPicuSkaiciu(), 0);
		// pridedam kieki vienai picai - tiesiog recepta
		pa.pridetiProduktu(surioPicosReceptas);
		assertEquals(pa.gautiProduktoKieki("padas"),1);
		try {
			p = pa.gamintiPica(surioPicosReceptas, 1);
		} catch(PicaException e){

		}
		assertEquals(p.arPicaPagaminta(),true);
		assertEquals(pa.gautiValymoSkaitliuka(), 19);
		assertEquals(pa.gautiPagamintuPicuSkaiciu(), 1);
//		p = pa.gamintiPica(surioPicosReceptas, 1);	// turi nebegaminti
		p=keptiPicosNepavyko(pa, surioPicosReceptas, 1);
//		assertEquals(p.arPicaPagaminta(),false);
		assertEquals(pa.gautiValymoSkaitliuka(), 19);
		assertEquals(pa.gautiPagamintuPicuSkaiciu(), 1);
		
		
	}

}
