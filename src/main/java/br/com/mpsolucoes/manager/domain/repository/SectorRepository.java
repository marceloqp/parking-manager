package br.com.mpsolucoes.manager.domain.repository;


import br.com.mpsolucoes.manager.domain.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<Sector, String> {
}