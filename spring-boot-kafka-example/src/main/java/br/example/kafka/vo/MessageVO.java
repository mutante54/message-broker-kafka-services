/**
 * 
 */
package br.example.kafka.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import br.example.enums.MessageStatusType;
import br.example.enums.MessageType;

/**
 * @author G0055135
 */
public class MessageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String content;

	private String templateCode;

	private Date createdDate;

	private String params;

	private Date updatedDate;

	private MessageType type;

	private MessageStatusType status;

	private String phost;

	private String chost;

	private String consumerGroup;
	
	private String apiKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public MessageStatusType getStatus() {
		return status;
	}

	public void setStatus(MessageStatusType status) {
		this.status = status;
	}

	public String getPhost() {
		return phost;
	}

	public void setPhost(String phost) {
		this.phost = phost;
	}

	public String getChost() {
		return chost;
	}

	public void setChost(String chost) {
		this.chost = chost;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, "apiKey");
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
