/**
 * 
 */
package br.example.sender.core;

import java.io.Serializable;
import java.util.HashMap;

import br.example.sender.core.exception.SendMessageException;

/**
 * Interface que define um contrato para envio de mensagens
 * 
 * @author G0055135
 *
 */
public interface ISender<P extends Serializable, R> {

	/**
	 * Faz o envio da mensagem
	 * 
	 * @return Opcional. Pode ser um objeto qualquer
	 *
	 * @throws SendMessageException
	 */
	R send() throws SendMessageException;

	/**
	 * Faz a configuração de uma mensagem
	 * 
	 * @param params  {@link HashMap} com os parâmetros do envio (chave-valor)
	 * @param content Conteúdo da mensagem
	 * @return a própria instância de {@link ISender} já configurada e pronta para envio
	 * @throws SendMessageException
	 */
	ISender<?, ?> build(HashMap<Enum<?>, Object> params, String content) throws SendMessageException;
}
