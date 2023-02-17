package com.sistema.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sistema.blog.entidades.Comentario;

public class PublicacionDto {

	
	private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "El titulo de la publicacion debe tener mas de 2 caracteres")
	private String titulo;
	
	@NotEmpty
	@Size(min = 10, message = "El titulo de la publicacion debe tener mas de 10 caracteres")
	private String descripcion;
	

	@NotEmpty
	
	private String contenido;
	
	private Set<Comentario> comentarios;

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public PublicacionDto() {
		super();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public PublicacionDto(Long id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}

}
