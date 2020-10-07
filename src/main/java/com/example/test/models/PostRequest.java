package com.example.test.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostRequest {
	@NotNull
	@NotBlank
	private String url;

	@NotNull
	@NotBlank
	private String type;

	@NotNull
	@NotBlank
	private String groupNumber;

	public String getUrl() {
        return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
        return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupNumber() {
        return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
}
