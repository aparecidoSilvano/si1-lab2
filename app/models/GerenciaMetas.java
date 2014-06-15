package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GerenciaMetas {
	
	private long id;

	private final static DateFormat df = new SimpleDateFormat("yyyyMMdd");	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Valida a data passada na Meta, só será valida se sua data for 
	 * maior ou igual a data atual e for menor que a data atual mais seis semanas.
	 * @param meta
	 * @return
	 */
	public static boolean validaData(String dataLimite) throws Exception{
		try {			
			Date datapassada = df.parse(dataLimite);
						
			Calendar cDataMin = Calendar.getInstance();		
									
			Calendar clMaximo = Calendar.getInstance();
			clMaximo.add(Calendar.DAY_OF_YEAR, 42);
			
			if(datapassada.getTime() >= cDataMin.getTimeInMillis() && datapassada.getTime() <= clMaximo.getTimeInMillis()){
				return true;
			}
			
			throw new IllegalArgumentException("data invalida");
		} catch (ParseException e) {
			System.err.println("problemas na converção da data passada");
		}
		
		
		return false;
	}
}
