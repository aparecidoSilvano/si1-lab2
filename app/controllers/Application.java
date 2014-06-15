package controllers;

import java.util.List;

import models.Meta;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
	private static final Form<Meta> metaForm =
			Form.form(Meta.class);
	private static GenericDAO dao = new GenericDAOImpl();

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
   // testando
    public static Result sobre(){
    	//return ok("sobre");
    	return ok(views.html.sobre.render(
    			"Top 100 filmes Cult",
    			play.core.PlayVersion.current())
    			);
    }
    
    @Transactional
    public static Result listaMetas(){
    	List<Meta> metas = getDao().findAllByClassName("Meta");
    	
    	return ok(views.html.metas.render(metas, metaForm));
    }
    
    
    /*
     * Cria uma nova meta
     */
    @Transactional
	public static Result novaMeta() {
		// Todos os Livros do Banco de Dados
		List<Meta> result = getDao().findAllByClassName("Meta");
		// O formulário dos Livros Preenchidos
		Form<Meta> filledForm = metaForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.metas.render(result, filledForm));
		} else {
			// Persiste o Livro criado
			getDao().persist(filledForm.get());
			// Espelha no Banco de Dados
			getDao().flush();
			return redirect(routes.Application.listaMetas());
		}
	}
    
 // Notação transactional sempre que o método fizer transação com o Banco de
 	// Dados.
 	@Transactional
 	public static Result deletaMeta(Long id) {
 		// Remove o Livro pelo Id
 		
 		if(id!= null){
 			getDao().removeById(Meta.class, id);
 			// Espelha no banco de dados
 			getDao().flush();
 		}
 		return redirect(routes.Application.listaMetas());
 	}

	public static GenericDAO getDao() {
		return dao;
	}

	public static void setDao(GenericDAO dao) {
		Application.dao = dao;
	}

}
