package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Meta")
public class Meta implements Comparable<Meta>{
	@Id
	@SequenceGenerator(name = "META_SEQUENCE", sequenceName = "META_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "dataLimite")
	private String dataLimite;
		
	@Column(name = "status")
	private boolean status;
	
	@Column(name = "prioridade")
	private int prioridade;
	
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
	
	public Meta(){
		nome = "";
		dataLimite = "";
		status = false;
	}
	
	
	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Meta(String nome, String dataLimite) throws DataInvalidaException{
		try {
			if(GerenciaMetas.validaData(dataLimite)){
				this.dataLimite = dataLimite;
				this.nome = nome;
			}
		} catch (DataInvalidaException dataException) {
			throw dataException;
		}		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(String dataLimite){
		this.dataLimite = dataLimite;
	}

	public int getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataLimite == null) ? 0 : dataLimite.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meta other = (Meta) obj;
		if (dataLimite == null) {
			if (other.dataLimite != null)
				return false;
		} else if (!dataLimite.equals(other.dataLimite))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	public int getSemanaDaMeta(){		
		try {
			Date dateAux = df.parse(getDataLimite());
			Calendar cDataMeta = Calendar.getInstance();
			cDataMeta.setTime(dateAux);
			
			Calendar atual = Calendar.getInstance();
			
			return cDataMeta.get(Calendar.WEEK_OF_YEAR) - atual.get(Calendar.WEEK_OF_YEAR) +1;
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return -1;
	}
	
	public Date getData(){
		try {
			Date date = df.parse(dataLimite);
			return date;
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	@Override
	public int compareTo(Meta metaO) {
		return getData().compareTo(metaO.getData());
	}
}
