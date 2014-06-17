package controllers;

import java.util.ArrayList;
import java.util.List;

import models.DataInvalidaException;
import models.GerenciaMetas;
import models.Meta;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	
	private static final Form<Meta> metaForm =
			Form.form(Meta.class);
	private static GenericDAO dao = new GenericDAOImpl();

	private static String mensagemErro = "";
	
    public static Result index() {
    	resetaMensagemDeErro();
    	return redirect(routes.Application.listaMetas());
    }
    
   
    /*
     * Lista as metas cadastradas a partir daquelas presentes no bd.
     *
     */
    @Transactional
    public static Result listaMetas(){
    	List<Meta> metas = getDao().findAllByClassName("Meta");
    	
    	List<Integer> semanas = new ArrayList<>();
		
		int aux = 0;
		for (Meta meta : metas) {
			if(aux != meta.getSemanaDaMeta()){
				aux = meta.getSemanaDaMeta();
				semanas.add(aux);
			}
		}
    	return ok(views.html.index.render(metas, metaForm, semanas,mensagemErro));
    }
    
    
    /*
     * Cria uma nova meta
     */
    @Transactional
	public static Result novaMeta() {
    	resetaMensagemDeErro();
		List<Meta> result = getDao().findAllByClassName("Meta");
	
		Form<Meta> filledForm = metaForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.metas.render(result, filledForm));
		} else {
			
			try {
				GerenciaMetas.validaData(filledForm.get().getDataLimite());
			} catch (DataInvalidaException e) {
				mensagemErro = e.getMessage();
				return redirect(routes.Application.listaMetas());				
			}
			
//			Persiste a meta criada
			getDao().persist(filledForm.get());
			
			getDao().flush();
			return redirect(routes.Application.listaMetas());
		}
	}
    

 	@Transactional
 	public static Result deletaMeta(Long id) {
 		resetaMensagemDeErro();
 		// Remove a meta pelo Id 		
 		if(id!= null){
 			getDao().removeById(Meta.class, id);
 			
 			getDao().flush();
 		}
 		return redirect(routes.Application.listaMetas());
 	}
 	
 	@Transactional
 	public static Result setStatusMeta(Long id){
 		resetaMensagemDeErro();
 		
 		Meta meta = getDao().findByEntityId(Meta.class, id);
 		if(meta.getStatus()){
 			meta.setStatus(false);
 		}else{
 			meta.setStatus(true);
 		}
 		
 		getDao().merge(meta);
 		getDao().flush();
 		return redirect(routes.Application.listaMetas());
 	}
 	
 	
 	
	public static GenericDAO getDao() {
		return dao;
	}

	public static void setDao(GenericDAO dao) {
		Application.dao = dao;
	}
	
	private static void resetaMensagemDeErro(){
		mensagemErro = "";
	}
}
