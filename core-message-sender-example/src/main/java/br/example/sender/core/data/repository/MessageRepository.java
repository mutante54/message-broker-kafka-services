package br.example.sender.core.data.repository;

import org.springframework.data.repository.CrudRepository;

import br.example.sender.core.data.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
