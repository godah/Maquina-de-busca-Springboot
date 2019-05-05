package com.maquinadebusca.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Link implements Serializable {

	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = -1L;

	//@Lob
	@NotBlank
	@Column(unique = true)
	private String url;

	@Basic
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime ultimaColeta;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "host_id")
	private Host host;

	@JsonIgnore
	@ManyToMany(mappedBy = "links", // Nome do atributo na classe Documento.
			fetch = FetchType.LAZY)
	private Set<Documento> documentos;

	public Link() {
		documentos = new HashSet<>();
	}

	public Link(String url, Documento documento) {
		this.url = url;
		this.ultimaColeta = null;
		this.documentos.add(documento);
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

	public LocalDateTime getUltimaColeta() {
		return ultimaColeta;
	}

	public void setUltimaColeta(LocalDateTime ultimaColeta) {
		this.ultimaColeta = ultimaColeta;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	public void addDocumento(Documento documento) {
		this.documentos.add(documento);
	}

	public void removeDocumento(Documento documento) {
		this.documentos.remove(documento);
	}

	
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + Objects.hashCode(this.id);
		hash = 71 * hash + Objects.hashCode(this.url);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Link other = (Link) obj;
		if (!Objects.equals(this.url, other.url)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
