package alberto.hugo.locationalert.modelo;

import java.io.Serializable;

public class Notificacao implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer image;
	
	private String descricao;
	private String endereco;
	private Integer raio;
	
	public Integer getRaio() {
		return raio;
	}
	public void setRaio(int i) {
		this.raio = i;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Integer getImage() {
		return image;
	}
	public void setImage(Integer image) {
		this.image = image;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
	
}
