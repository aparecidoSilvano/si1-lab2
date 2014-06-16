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
	public static boolean validaData(String dataLimite) throws DataInvalidaException{
		try {			
			Date datapassada = df.parse(dataLimite);
						
			Calendar cDataMin = Calendar.getInstance();		
									
			Calendar clMaximo = Calendar.getInstance();
			clMaximo.add(Calendar.DAY_OF_YEAR, 42);
			
//			if(datapassada.getTime() >= cDataMin.getTimeInMillis() && datapassada.getTime() <= clMaximo.getTimeInMillis()){
//				return true;
//			}
			
			if(datapassada.getTime() >= cDataMin.getTimeInMillis()){
				if(datapassada.getTime() <= clMaximo.getTimeInMillis()){
					return true;
				}else{
					throw new DataInvalidaException("A data informada está além do limite maximo de seis semanas");
				}
			}else{
				throw new DataInvalidaException("A data informada já passou ");
			}
		} catch (ParseException e) {
			throw new DataInvalidaException("Data invalida");
		}
	}
}
