package br.com.mpsolucoes.manager.domain.repository;

import br.com.mpsolucoes.manager.domain.entity.GarageStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageStatusRepository extends CrudRepository<GarageStatus, String> {
}
