/**
 * 
 */
package br.example.sender.core;

import java.io.Serializable;

import br.example.sender.core.exception.SendMessageException;

/**
 * Interface que define um contrato para envio de mensagens
 * 
 * @author G0055135
 *
 */
public interface ISender<P extends Serializable, R> {

	/**
	 * Faz o envio de uma mensagem
	 * 
	 * @param p Objeto serializável
	 * @return Opcional. Pode ser um objeto qualquer
	 *
	 * @throws SendMessageException
	 */
	R send(P p) throws SendMessageException;

}
