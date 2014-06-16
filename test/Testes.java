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
	 * -deve permitir editar uma meta qualquer
	 * -deve excluir as metas (uma por vez).
	 */
	
	private GenericDAOImpl dao = new GenericDAOImpl();
	private List<Meta> metas = new ArrayList<>();
	
	@Test
	public void deveCriarMetas() {		
		try {
			Meta meta1 = new Meta("meta teste 1", "2014-06-17");
			Meta meta2 = new Meta("meta teste 2", "2014-06-18");
			Meta meta3 = new Meta("meta teste 3", "2014-07-04");			
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
			Meta meta1 = new Meta("meta teste 1", "2014-06-13");
		} catch (DataInvalidaException e) {
			assertEquals("A data informada já passou", e.getMessage());
			// ok
		}
		
		// tenta criar um meta com um data que excede o limite de 6 semanas
		try {
			Meta meta2 = new Meta("meta teste 1", "2014-06-30");
		} catch (DataInvalidaException e) {
			assertEquals("A data informada está além do limite maximo de seis semanas", e.getMessage());
			// ok
		}
	}

	
	public GenericDAOImpl getDao() {
		return dao;
	}

	public void setDao(GenericDAOImpl dao) {
		this.dao = dao;
	}
}
