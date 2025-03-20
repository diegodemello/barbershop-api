package br.com.dio.barbershop.service.impl;

import br.com.dio.barbershop.entity.ScheduleEntity;
import br.com.dio.barbershop.repository.ScheduleRepository;
import br.com.dio.barbershop.service.query.ScheduleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService implements br.com.dio.barbershop.service.ScheduleService {
    private final ScheduleRepository repository;
    private final ScheduleQueryService queryService;

    @Override
    public ScheduleEntity save(final ScheduleEntity entity) {
        queryService.verifyIfScheduleExists(entity.getStartAt(), entity.getEndAt());

        return repository.save(entity);
    }

    @Override
    public void delete(final long id) {
        queryService.findById(id);
        repository.deleteById(id);
    }
}
