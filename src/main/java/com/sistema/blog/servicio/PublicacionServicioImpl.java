package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicacionDto;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Override
	public PublicacionDto crearPublicacion(PublicacionDto publicacionDto) {

		Publicacion publicacion = mapearEntidad(publicacionDto);

		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

		PublicacionDto publicacionRespuesta = mapearDto(nuevaPublicacion);

		return publicacionRespuesta;
	}

	@Override
	public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		List<PublicacionDto> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDto(publicacion))
				.collect(Collectors.toList());

		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(medidaDePagina);
		;
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());

		return publicacionRespuesta;
	}

	// convierte entidad a dto
	/*
	 * private PublicacionDto mapearDto(Publicacion publicacion) {
	 * 
	 * PublicacionDto publicacionDto = new PublicacionDto();
	 * 
	 * publicacionDto.setId(publicacion.getId());
	 * publicacionDto.setTitulo(publicacion.getTitulo());
	 * publicacionDto.setDescripcion(publicacion.getDescripcion());
	 * publicacionDto.setContenido(publicacion.getContenido());
	 * 
	 * return publicacionDto; }
	 * 
	 * // convierte dto a entidad private Publicacion mapearEntidad(PublicacionDto
	 * publicacionDto) {
	 * 
	 * Publicacion publicacion = new Publicacion();
	 * 
	 * // publicacion.setId(publicacionDto.getId());
	 * publicacion.setTitulo(publicacionDto.getTitulo());
	 * publicacion.setDescripcion(publicacionDto.getDescripcion());
	 * publicacion.setContenido(publicacionDto.getContenido());
	 * 
	 * return publicacion; }
	 */

	// Codigo mas legible y facil de conversion o Mapeo

	private PublicacionDto mapearDto(Publicacion publicacion) {
		PublicacionDto publicacionDto = modelMapper.map(publicacion, PublicacionDto.class);
		return publicacionDto;

	}
	
	private Publicacion mapearEntidad(PublicacionDto publicacionDto) {
		Publicacion publicacion = modelMapper.map(publicacionDto, Publicacion.class);
		return publicacion;
	}

	@Override
	public PublicacionDto obtenerPublicacionByiD(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		return mapearDto(publicacion);
	}

	@Override
	public PublicacionDto actualizarPublicacion(PublicacionDto publicacionDto, long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacion.setTitulo(publicacionDto.getTitulo());
		publicacion.setDescripcion(publicacionDto.getDescripcion());
		publicacion.setContenido(publicacionDto.getContenido());

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDto(publicacionActualizada);
	}

	@Override
	public void eliminarPublicacion(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacionRepositorio.delete(publicacion);

	}

}
