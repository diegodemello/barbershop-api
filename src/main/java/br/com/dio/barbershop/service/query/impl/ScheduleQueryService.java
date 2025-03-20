package br.com.dio.barbershop.service.query.impl;

import br.com.dio.barbershop.entity.ScheduleEntity;
import br.com.dio.barbershop.exception.NotFoundException;
import br.com.dio.barbershop.exception.ScheduleInUseException;
import br.com.dio.barbershop.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleQueryService implements br.com.dio.barbershop.service.query.ScheduleQueryService {

    private final ScheduleRepository repository;

    @Override
    public ScheduleEntity findById(final long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Agendamento não encontrado")
        );
    }

    @Override
    public List<ScheduleEntity> findInMonth(final OffsetDateTime startAt, final OffsetDateTime endAt) {
        return repository.findByStartAtGreaterThanEqualAndEndAtLessThanEqualOrderByStartAtAscEndAtAsc(startAt, endAt);
    }

    @Override
    public void verifyIfScheduleExists(final OffsetDateTime startAt, final OffsetDateTime endAt) {
        if (repository.existsByStartAtAndEndAt(startAt, endAt)){
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }

}
