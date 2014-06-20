package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.DataInvalidaException;
import models.GerenciaDatas;
import models.Meta;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.editar;

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
    	
    	povoaLista();
 
    	List<Meta> metas = getDao().findAllByClassName("Meta");    
    	
    	System.out.println(metas.size());
    	//metas.addAll(geraMetasParaTeste());
    	// ordena as metas pela semana.
    	Collections.sort(metas);
    	
    	List<Integer> semanas = new ArrayList<>();
    	Collections.sort(semanas);
		
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
			
		Form<Meta> filledForm = metaForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest();
		} else {
			
			try {
				GerenciaDatas.validaData(filledForm.get().getDataLimite());
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
 		//System.out.println(id);
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
 	
 	@Transactional
 	public static Result preEdita(Long id){
		Form<Meta> metaForm = Form.form(Meta.class).fill(getDao().findByEntityId(Meta.class, id));
		return ok(editar.render(id, metaForm));
 	}
 	
 	@Transactional
	public static Result editarMeta(Long id) {
		Form<Meta> alterarForm = Form.form(Meta.class).bindFromRequest();
		if (alterarForm.hasErrors()) {
			return badRequest(editar.render(id, alterarForm));
		}
		
		getDao().merge(alterarForm.get());
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
	
	private static void povoaLista() {
		if(getDao().findAllByClassName("Meta").isEmpty()){
			try{
				Meta meta1 = new Meta("fazer a correção dos lab que a mim forem alocados", "2014-07-02");
				getDao().persist(meta1);
			}catch (DataInvalidaException e) {
				
			}
			try{
				Meta meta2 = new Meta("resolver problema de documentação", "2014-07-05");
				getDao().persist(meta2);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta3 = new Meta("limpar meu quarto", "2014-07-05");
				getDao().persist(meta3);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta4 = new Meta("começar regime", "2014-07-07");
				getDao().persist(meta4);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta5 = new Meta("correr de manhã ou no final do dia", "2014-07-07");
				getDao().persist(meta5);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta6 = new Meta("fazer exames medicos", "2014-07-09");
				getDao().persist(meta6);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta7 = new Meta("ir no oftalmologista, fazer exames de rotina para ver estado da pressão do olho", "2014-07-15");
				getDao().persist(meta7);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta8 = new Meta("fazer manutenção no carro", "2014-07-22");
				getDao().persist(meta8);
			}catch (DataInvalidaException e) {
				
			}
			
			try{
				Meta meta9 = new Meta("ver a seleção brasileira ser campeã do mundo", "2014-07-13");
				getDao().persist(meta9);
			}catch (DataInvalidaException e) {
				
			}
			try {			
				Meta meta10 = new Meta("ver resultado do regime", "2014-07-25");
				getDao().persist(meta10);				
			} catch (DataInvalidaException e) {
				
			}
        	
    		
    	}
		
	}
}
