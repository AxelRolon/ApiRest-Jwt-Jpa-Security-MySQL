package com.sistema.blog.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.excepciones.BlogAppException;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServImpl implements ComentarioServicio {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Override
	public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
		Comentario comentario = mapearEntidad(comentarioDTO);
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		comentario.setPublicacion(publicacion);
		Comentario nuevoComentario = comentarioRepositorio.save(comentario);
		return mapearDto(nuevoComentario);
	}
	 @Override
	public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId) {

		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		return comentarios.stream().map(comentario -> mapearDto(comentario)).collect(Collectors.toList());

	}

	@Override
	public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
		}

		return mapearDto(comentario);
	}

	@Override
	public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId,
			ComentarioDTO solicitudComentario) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
		}
		comentario.setNombre(solicitudComentario.getNombre());
		comentario.setEmail(solicitudComentario.getEmail());
		comentario.setCuerpo(solicitudComentario.getCuerpo());

		Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
		return mapearDto(comentarioActualizado);
	}

	@Override
	public void eliminarComentario(Long publicacionId, Long comentarioId) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
		}
		comentarioRepositorio.delete(comentario);
	}
	
	
	/*
	 * private ComentarioDTO mapearDto(Comentario comentario) { ComentarioDTO
	 * comentarioDTO = new ComentarioDTO(); comentarioDTO.setId(comentario.getId());
	 * comentarioDTO.setEmail(comentario.getEmail());
	 * comentarioDTO.setNombre(comentario.getNombre());
	 * comentarioDTO.setCuerpo(comentario.getCuerpo());
	 * 
	 * return comentarioDTO;
	 * 
	 * }
	 * 
	 * private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {// entidad a
	 * dtoc Comentario comentario = new Comentario();
	 * comentario.setId(comentarioDTO.getId());
	 * comentario.setNombre(comentarioDTO.getNombre());
	 * comentario.setEmail(comentarioDTO.getEmail());
	 * comentario.setCuerpo(comentarioDTO.getCuerpo());
	 * 
	 * return comentario; }
	 */
	
	//Model mapper simplifica el codigo de arriba sobre las conversiones o Mapeo de un objeto a una clase destino
	
	private ComentarioDTO mapearDto(Comentario comentario) {
		ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
		return comentarioDTO;
	}
	
	private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {// entidad a dtoc
		Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
		return comentario; 
	}

}
