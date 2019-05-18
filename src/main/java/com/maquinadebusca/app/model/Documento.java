package com.maquinadebusca.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Documento implements Serializable {

	private static final long serialVersionUID = 4443382295340323876L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Lob
	@NotBlank
	private String url;

	@Lob
	@NotBlank
	private String texto;

	@Lob
	@NotBlank
	private String visao;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "documento_link", joinColumns = @JoinColumn(name = "documento_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id"))
	private Set<Link> links;

	private double frequenciaMaxima;

	private double somaQuadradosPesos;

	@OneToMany(mappedBy = "documento", // Nome do atributo na classe IndiceInvertido.
			cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<IndiceInvertido> indiceInvertido;

	public Documento() {
		this.indiceInvertido = new LinkedList<>();
		links = new HashSet<>();
	}

	public Documento(String url, String texto, String visao) {
		this.url = url;
		this.texto = texto;
		this.visao = visao;
		this.links = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getVisao() {
		return visao;
	}

	public void setVisao(String visao) {
		this.visao = visao;
	}

	public Set<Link> getLinks() {
		return links;
	}

	public void setLinks(Set<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		this.links.add(link);
	}

	public void removeLink(Link link) {
		links.remove(link);
	}

	public double getFrequenciaMaxima() {
		return frequenciaMaxima;
	}

	public void setFrequenciaMaxima(double frequenciaMaxima) {
		this.frequenciaMaxima = frequenciaMaxima;
	}

	public double getSomaQuadradosPesos() {
		return somaQuadradosPesos;
	}

	public void setSomaQuadradosPesos(double somaQuadradosPesos) {
		this.somaQuadradosPesos = somaQuadradosPesos;
	}

	public List<IndiceInvertido> getIndiceInvertido() {
		return indiceInvertido;
	}

	public void setIndiceInvertido(List<IndiceInvertido> indiceInvertido) {
		this.indiceInvertido = indiceInvertido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(frequenciaMaxima);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((indiceInvertido == null) ? 0 : indiceInvertido.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		temp = Double.doubleToLongBits(somaQuadradosPesos);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((visao == null) ? 0 : visao.hashCode());
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
		Documento other = (Documento) obj;
		if (Double.doubleToLongBits(frequenciaMaxima) != Double.doubleToLongBits(other.frequenciaMaxima))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (indiceInvertido == null) {
			if (other.indiceInvertido != null)
				return false;
		} else if (!indiceInvertido.equals(other.indiceInvertido))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (Double.doubleToLongBits(somaQuadradosPesos) != Double.doubleToLongBits(other.somaQuadradosPesos))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (visao == null) {
			if (other.visao != null)
				return false;
		} else if (!visao.equals(other.visao))
			return false;
		return true;
	}
	
}
