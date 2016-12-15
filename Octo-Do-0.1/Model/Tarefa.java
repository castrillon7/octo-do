package Model;

import java.io.Serializable;


public class Tarefa implements Serializable {
	private static final long serialVersionUID = -3689371224260485225L;
	//Atributes
	private String nome;
	//Lista lista;
	private Boolean status;
	private Long data;

	public Tarefa()
	{

	}

	public Tarefa(String nome, Boolean status, Long data) {
		super();
		this.nome = nome;
		this.status = status;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}
}
