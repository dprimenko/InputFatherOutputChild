import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import sun.awt.SunToolkit.InfiniteLoop;
import sun.misc.IOUtils;

public class InputFatherOutputChild {

	public static void main(String[] args) {
		
		
		ProcessBuilder paBuilder = new ProcessBuilder("/bin/ls", "-l", "/home/usuario");
		ProcessBuilder pbBuilder = new ProcessBuilder("grep", "D");
		Process pa = null;
		Process pb = null;
		
		try {
			pa = paBuilder.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			pb = pbBuilder.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Conectamos los flujos de salida y entrada entre procesos
		
		BufferedReader salidaA = null;
		
		try {
			salidaA = 
					new BufferedReader(new InputStreamReader(pa.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter entradaB = null;
		
		try {
			entradaB = 
					new BufferedWriter(new OutputStreamWriter(pb.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Realizamos el trasvase de datos
		
		String resultadoA = "";
		
		try {
			while ((resultadoA = salidaA.readLine()) != null) {
				entradaB.write(resultadoA);
				entradaB.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			salidaA.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			entradaB.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Conectamos flujo salida del proceso B a consola estandar
		
		BufferedReader salidaB = null;
		
		try {
			salidaB = new BufferedReader(new InputStreamReader(pb.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String resultadoB = "";
		
		try {
			while ((resultadoB = salidaB.readLine()) != null) {
				System.out.println(resultadoB);
			}
			salidaB.close();
			
			int finA = pa.waitFor();
			int finB = pb.waitFor();
			
			System.out.println("Procesos hijos finalizados y con salida " + finA + " y " + finB);
			
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
