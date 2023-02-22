package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDto;
import com.sistema.blog.dto.PublicacionRespuesta;

public interface PublicacionServicio {

	public PublicacionDto crearPublicacion(PublicacionDto publicacionDto);

	public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
			String sortDir);

	public PublicacionDto obtenerPublicacionByiD(long id);

	public PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto, long id);

	public void eliminarPublicacion(long id);
}
