package br.com.dio.barbershop.service;

import br.com.dio.barbershop.entity.ScheduleEntity;

public interface ScheduleService {
    ScheduleEntity save(final ScheduleEntity entity);

    void delete(final long id);
}
