package br.com.estoque.backend.services;

import br.com.estoque.backend.dtos.EntryDto;
import br.com.estoque.backend.entities.Entry;
import br.com.estoque.backend.entities.User;
import br.com.estoque.backend.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final AuthService authService;

    public void createEntry(EntryDto entryDto) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = authService.getUser(authentication);

            Entry entry = Entry.builder()
                    .id(UUID.randomUUID().toString())
                    .user(user)
                    .quantity(entryDto.quantity())
                    .product(entryDto.product())
                    .totalValue(entryDto.product().getPrice().multiply(new BigDecimal(entryDto.quantity())))
                    .instant(Instant.now()  )
                    .build();

            entryRepository.save(entry);

        }catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }
}
