package br.example.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.example.data.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
