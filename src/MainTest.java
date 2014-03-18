import java.io.File;
import java.text.SimpleDateFormat;

public class MainTest {

	public static void main(String[] args) {

		MainTest mt = new MainTest();

		mt.x();
	}

	public void x() {

		File file = new File(getClass().getResource(getClass().getCanonicalName() + ".class").toString().replaceAll("file:/", ""));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		System.out.println(sdf.format(file.lastModified()));
	}
}