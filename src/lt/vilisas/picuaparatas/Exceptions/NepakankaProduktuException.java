package lt.vilisas.picuaparatas.Exceptions;

public class NepakankaProduktuException extends PicaException {

	private String pavadinimas;
	
	public NepakankaProduktuException(String pavadinimas) {
		// TODO Auto-generated constructor stub
		this.pavadinimas = pavadinimas;
	}
	
	public String gautiProduktoPavadinima() {
		return this.pavadinimas;
	}

}
