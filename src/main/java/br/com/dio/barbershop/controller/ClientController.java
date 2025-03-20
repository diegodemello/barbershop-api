package br.com.dio.barbershop.controller;

import br.com.dio.barbershop.controller.request.SaveClientRequest;
import br.com.dio.barbershop.controller.request.UpdateClientRequest;
import br.com.dio.barbershop.controller.response.ClientDetailResponse;
import br.com.dio.barbershop.controller.response.ListClientResponse;
import br.com.dio.barbershop.controller.response.SaveClientResponse;
import br.com.dio.barbershop.controller.response.UpdateClientResponse;
import br.com.dio.barbershop.mapper.ClientMapper;
import br.com.dio.barbershop.service.ClientService;
import br.com.dio.barbershop.service.query.ClientQueryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientQueryService queryService;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    SaveClientResponse save(@RequestBody @Valid final SaveClientRequest request){
        var entity = mapper.toEntity(request);
        service.save(entity);
        return mapper.toSaveResponse(entity);
    }

    @PutMapping("{id}")
    UpdateClientResponse update(@PathVariable final long id, @RequestBody @Valid final UpdateClientRequest request){
        var entity = mapper.toEntity(id, request);
        service.update(entity);
        return mapper.toUpdateResponse(entity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable final long id){
        service.delete(id);
    }

    @GetMapping("{id}")
    ClientDetailResponse findById(@PathVariable final long id){
        var entity = queryService.findById(id);
        return mapper.toDetailResponse(entity);
    }

    @GetMapping
    List<ListClientResponse> list(){
        var entities = queryService.list();
        return mapper.toListResponse(entities);
    }

}