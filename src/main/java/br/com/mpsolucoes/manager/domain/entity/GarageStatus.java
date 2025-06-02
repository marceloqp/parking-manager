package br.com.mpsolucoes.manager.domain.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("GarageStatus")
@Data
public class GarageStatus {

    @Id
    private String id;
    private Boolean status;

    public GarageStatus(Boolean status) {
        this.status = status;
    }
}
