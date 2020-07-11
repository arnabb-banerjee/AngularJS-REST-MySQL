package Helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class common {
	static StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  

	public static void ErrorLog(String Module, Exception e)
	{
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("c:/test/ErrorLog.txt", true));
			writer.newLine();
			writer.write(Module);
			writer.newLine();
			writer.write(e.toString());
		    writer.close();

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
	}
	
	public static void ErrorLog(String Module, String e)
	{
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("c:/test/ErrorLog.txt", true));
	    	writer.newLine();
			writer.write(Module);
			writer.newLine();
			writer.write(e.toString());
		    writer.close();

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
	}

}


