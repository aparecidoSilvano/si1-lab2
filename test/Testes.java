import static org.junit.Assert.assertEquals;
import models.GerenciaMetas;
import models.Meta;
import models.dao.GenericDAOImpl;

import org.junit.Test;

import play.db.jpa.Transactional;


public class Testes {
	
	/*
	 * os principais comportamentos a serem testados:
	 * -deve adcionar uma meta se ela for valida,
	 * -não deve adiconar a meta se ela não for valida,
	 * -deve permitir editar uma meta qualquer
	 * -deve excluir as metas (uma por vez).
	 */
	
	private GerenciaMetas gMetas = new GerenciaMetas();
	private GenericDAOImpl dao = new GenericDAOImpl();
	
	@Test
	@Transactional
	public void deveAdicionarMeta() {		
		// sendo uma meta, definida assim, "titulo", "data limite para ser cumprida, na forma yyyymmdd"
		try{
			
			gMetas.addMeta(new Meta("terminar lab de si", "20140616"));
			
			gMetas.addMeta(new Meta("entender como o h2 fuciona", "20140615"));
					
			for (Meta meta : gMetas.getMetas()) {
				System.out.println(meta.getNome());
			}
			assertEquals(2, gMetas.countMetas());

		}catch(Exception e){
			System.out.println("pegou uma exceção?");
			assertEquals("data invalida", e.getMessage());
		}
	}

	
	public GenericDAOImpl getDao() {
		return dao;
	}

	public void setDao(GenericDAOImpl dao) {
		this.dao = dao;
	}
}
