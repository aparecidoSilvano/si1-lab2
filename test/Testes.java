import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import models.DataInvalidaException;
import models.Meta;
import models.dao.GenericDAOImpl;

import org.junit.Test;


public class Testes {
	
	/*
	 * os principais comportamentos a serem testados:
	 * -deve adcionar uma meta se ela for valida,
	 * -não deve adiconar a meta se ela não for valida,
	 */
	
	private List<Meta> metas = new ArrayList<>();
	
	@Test
	public void deveCriarMetas() {		
		try {
			new Meta("meta teste 1", "testando", "2014-06-25");
			new Meta("meta teste 2", "testando", "2014-06-30");
			new Meta("meta teste 3", "testando","2014-07-04");			
		} catch (DataInvalidaException e) {
			// err.
			fail();
		}	
	}
	
	@Test
	public void devePermitirMetasApenasComDatasVallidas(){
		// formato de uma data yyyy-mm-dd
		
		// tenta criar uma meta com uma data que já passou.
		try {
			new Meta("meta teste 1", "testando", "2014-06-15");
		} catch (DataInvalidaException e) {
			assertEquals("A data informada já passou", e.getMessage());
			// ok
		}
		
		// tenta criar um meta com um data que excede o limite de 6 semanas
		try {
			new Meta("meta teste 1", "testando","2014-12-30");
		} catch (DataInvalidaException e) {
			assertEquals("A data informada está além do limite maximo de seis semanas", e.getMessage());
			// ok
		}
	}
}
