package br.com.estoque.backend.repository;

import br.com.estoque.backend.entities.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry,String> {
}
