package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GerenciaMetas {

	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	

	/**
	 * Valida a data passada na Meta, só será valida se sua data for 
	 * maior ou igual a data atual e for menor que a data atual mais seis semanas.
	 * @param meta
	 * @return
	 */
	public static boolean validaData(String dataLimite) throws DataInvalidaException{
		
		try {					
			Date dateAux = df.parse(dataLimite);
			Calendar cDataMeta = Calendar.getInstance();
			cDataMeta.setTime(dateAux);
			
			Calendar cDataMin = Calendar.getInstance();		
									
			Calendar clMaximo = Calendar.getInstance();
			clMaximo.add(Calendar.DAY_OF_YEAR, 42);
			
			String info = "semana do ano " + cDataMeta.get(Calendar.WEEK_OF_YEAR)+ " dia = " + cDataMeta.get(Calendar.DAY_OF_MONTH)+ ", mês = " + cDataMeta.get(Calendar.MONTH) + ", ano = " + cDataMeta.get(Calendar.YEAR);
			System.out.println(info + " é valida " +(cDataMeta.after(cDataMin) && cDataMeta.before(clMaximo)));
			
			if(cDataMeta.after(cDataMin)){
				if(cDataMeta.before(clMaximo)){
					return true;
				}else{
					throw new DataInvalidaException("A data informada está além do limite maximo de seis semanas");
				}
			}else{
				throw new DataInvalidaException("A data informada já passou");
			}
		} catch (ParseException e) {
			throw new DataInvalidaException("Data invalida");
		}
	}
	
	public int semanaDaMeta(String data){		
		try {
			Date dateAux = df.parse(data);
			Calendar cDataMeta = Calendar.getInstance();
			cDataMeta.setTime(dateAux);
			
			return cDataMeta.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return 0;
	}
}
