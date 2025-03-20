package br.com.dio.barbershop.service.impl;

import br.com.dio.barbershop.entity.ClientEntity;
import br.com.dio.barbershop.repository.ClientRepository;
import br.com.dio.barbershop.service.query.ClientQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ClientService implements br.com.dio.barbershop.service.ClientService {
    private final ClientRepository repository;
    private final ClientQueryService queryService;

    @Override
    public ClientEntity save(final ClientEntity entity) {
        queryService.verifyEmail(entity.getEmail());
        queryService.verifyPhone(entity.getPhone());

        return repository.save(entity);
    }

    @Override
    public ClientEntity update(final ClientEntity entity) {
        queryService.verifyEmail(entity.getId(), entity.getEmail());
        queryService.verifyPhone(entity.getId(), entity.getPhone());

        var stored = queryService.findById(entity.getId());
        stored.setName(entity.getName());
        stored.setPhone(entity.getPhone());
        stored.setEmail(entity.getEmail());
        return repository.save(stored);
    }

    @Override
    public void delete(final long id) {
        queryService.findById(id);
        repository.deleteById(id);
    }
}
