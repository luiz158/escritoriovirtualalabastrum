import escritoriovirtualalabastrum.util.Util;

public class MainTest {

	public static void main(String[] args) {

		System.out.println(Util.converterStringParaBigDecimal("3.7"));
		System.out.println(Util.converterStringParaBigDecimal("3,7"));
		System.out.println(Util.converterStringParaBigDecimal("0"));
	}
}