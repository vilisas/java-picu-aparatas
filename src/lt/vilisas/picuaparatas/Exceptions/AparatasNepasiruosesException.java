package lt.vilisas.picuaparatas.Exceptions;

public class AparatasNepasiruosesException extends PicaException {
	private int skaitliukas;
	
	public AparatasNepasiruosesException(int skaitliukas) {
		this.skaitliukas = skaitliukas;
	}
	
	public int gautiSkaitliuka() {
		return this.skaitliukas;
	}

}
