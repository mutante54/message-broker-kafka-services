package br.example.kafka.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import br.example.enums.MessageType;

public class MessageMailVO {

	private String templateCode;

	private String content;

	private Date sentDate;

	private Map<String, Object> params;

	private MessageType type;

	public MessageMailVO() {
	}

	public MessageMailVO(String templateCode, String content) {
		this.templateCode = templateCode;
		this.content = content;
		this.sentDate = new Date();

		params = new HashMap<String, Object>();
	}

	public MessageMailVO(String templateCode) {
		this.templateCode = templateCode;
		this.sentDate = new Date();
		this.type = MessageType.HTML;

		params = new HashMap<String, Object>();
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addValue(String key, Object value) {
		if (this.params == null) {
			params = new HashMap<String, Object>();
		}

		this.params.put(key, value);
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Object getValue(String key) {
		if (this.params != null) {
			return this.params.get(key);
		}
		return null;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}