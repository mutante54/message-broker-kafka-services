package br.example.sender.core.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.example.sender.core.enums.MessageStatusType;
import br.example.sender.core.enums.MessageType;

@Entity
public class Message {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @SequenceGenerator(sequenceName = "MESSAGE_SEQ", allocationSize = 1, name = "MESSAGE_SEQ")
    private Long id;
	
    private String content;
    
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
	private String params;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Enumerated(EnumType.STRING)
	private MessageType type;
	
	@Enumerated(EnumType.STRING)
	private MessageStatusType status;
	
	@Column(name = "PRODUCER_HOST")
    private String phost;
    
	@Column(name = "CONSUMER_HOST")
    private String chost;
	
	@Column(name = "CONSUMER_GROUP")
	private String consumerGroup;

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

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

}
