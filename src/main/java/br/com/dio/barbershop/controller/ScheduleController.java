package br.com.dio.barbershop.controller;

import br.com.dio.barbershop.controller.request.SaveScheduleRequest;
import br.com.dio.barbershop.controller.response.SaveScheduleResponse;
import br.com.dio.barbershop.controller.response.ScheduleAppointmentMonthResponse;
import br.com.dio.barbershop.mapper.ScheduleMapper;
import br.com.dio.barbershop.service.ScheduleService;
import br.com.dio.barbershop.service.query.ScheduleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.YearMonth;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("schedules")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService service;
    private final ScheduleQueryService queryService;
    private final ScheduleMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    SaveScheduleResponse save(@RequestBody @Valid SaveScheduleRequest request){
        var entity = mapper.toEntity(request);
        service.save(entity);
        return mapper.toSaveResponse(entity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable final long id){
        service.delete(id);
    }

    @GetMapping("{year}/{month}")
    ScheduleAppointmentMonthResponse listMonth(@PathVariable final int year, @PathVariable final int month){
        var yearMonth = YearMonth.of(year, month);
        var startAt = yearMonth.atDay(1)
                .atTime(0, 0, 0, 0)
                .atOffset(UTC);
        var endAt = yearMonth.atEndOfMonth()
                .atTime(23, 59, 59, 999_999_999)
                .atOffset(UTC);
        var entities = queryService.findInMonth(startAt, endAt);
        return mapper.toMonthResponse(year, month, entities);
    }
}