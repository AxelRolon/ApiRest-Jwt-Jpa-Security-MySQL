package com.sistema.blog.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entidades.Rol;
import com.sistema.blog.entidades.Usuario;

public interface RolRepositorio extends JpaRepository<Usuario, Long> {
//Problemas con NullPOINTEREXCEPTION? USA EL CONTENEDOR O CLASE (OPCIONAL) CHAVAL!!

	public Optional<Rol> findByNombre(String nombre);
}
