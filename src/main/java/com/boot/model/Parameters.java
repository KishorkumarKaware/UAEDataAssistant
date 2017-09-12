package com.boot.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameters {
	private String email;

    private String year;

    private String activity;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getYear ()
    {
        return year;
    }

    public void setYear (String year)
    {
        this.year = year;
    }

    public String getActivity ()
    {
        return activity;
    }

    public void setActivity (String activity)
    {
        this.activity = activity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+", year = "+year+", activity = "+activity+"]";
    }
}
