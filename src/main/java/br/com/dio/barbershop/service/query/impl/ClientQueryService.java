package br.com.dio.barbershop.service.query.impl;

import br.com.dio.barbershop.entity.ClientEntity;
import br.com.dio.barbershop.exception.EmailInUseException;
import br.com.dio.barbershop.exception.NotFoundException;
import br.com.dio.barbershop.exception.PhoneInUseException;
import br.com.dio.barbershop.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClientQueryService implements br.com.dio.barbershop.service.query.ClientQueryService {

    private final ClientRepository repository;

    @Override
    public ClientEntity findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado o clinete de id: " + id));
    }

    @Override
    public List<ClientEntity> list() {
        return repository.findAll() ;
    }

    @Override
    public void verifyPhone(String phone) {
        if (repository.existsByPhone(phone)){
            var message = "O telefone digitado já está sendo usado por outro cliente.";
            throw new PhoneInUseException(message);
        }
    }

    @Override
    public void verifyPhone(long id, String phone) {
        var optional = repository.findByPhone(phone);
        if (optional.isPresent() && !Objects.equals(optional.get().getPhone(), phone)){
            var message = "O telefone digitado já está sendo usado por outro cliente.";
            throw new PhoneInUseException(message);
        }
    }

    @Override
    public void verifyEmail(String email) {
        if (repository.existsByEmail(email)){
            var message = "O email digitado já está sendo usado por outro cliente.";
            throw new EmailInUseException(message);
        }
    }

    @Override
    public void verifyEmail(long id, String email) {
        var optional = repository.findByPhone(email);
        if (optional.isPresent() && Objects.equals(optional.get().getEmail(), email)){
            var message = "O email digitado já está sendo usado por outro cliente.";
            throw new EmailInUseException(message);
        }
    }
}
