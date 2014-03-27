import java.math.BigDecimal;

public class MainTest {

	public static void main(String[] args) {

		BigDecimal x = new BigDecimal("10");

		System.out.println(x.multiply(new BigDecimal("10")));

		System.out.println(x);
	}
}