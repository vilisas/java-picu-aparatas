package lt.vilisas.picuaparatas.Exceptions;

public class PicosDydisException extends PicaException {
	private int dydis;
	
	public PicosDydisException(int dydis) {
		this.dydis = dydis;
	}
	
	public int gautiDydi() {
		return this.dydis;
	}

}
