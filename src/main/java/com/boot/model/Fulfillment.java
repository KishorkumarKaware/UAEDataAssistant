package com.boot.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fulfillment {
	private String speech;
	private String displayText;
    private String source;
    private Messages[] messages;

    public String getSpeech ()
    {
        return speech;
    }

    public void setSpeech (String speech)
    {
        this.speech = speech;
    }

    public Messages[] getMessages ()
    {
        return messages;
    }

    public void setMessages (Messages[] messages)
    {
        this.messages = messages;
    }

    public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
    @Override
    public String toString()
    {
        return "ClassPojo [speech = "+speech+", messages = "+messages+"]";
    }
}