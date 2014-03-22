package escritoriovirtualalabastrum.controller;

import java.io.File;
import java.text.SimpleDateFormat;

public class fswrfsf {

	public static void main(String[] args) {

		File file = new File("/home/renan/workspaceeclipse/escritoriovirtualalabastrum/WebContent/WEB-INF/classes/escritoriovirtualalabastrum/controller/AnaliseController.class");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		System.out.println(sdf.format(file.lastModified()));
	}
}
