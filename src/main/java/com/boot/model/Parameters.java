package com.boot.model;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameters {
	private String email;

	 private String[] sector;
    
    private String[] year;

    private String table;

    public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

    public String[] getYear ()
    {
        return year;
    }

    public void setYear (String[] year)
    {
        this.year = year;
    }
    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String[] getSector ()
    {
        return sector;
    }

    public void setSector (String[] sector)
    {
        this.sector = sector;
    }


	@Override
	public String toString() {
		return "Parameters [email=" + email + ", sector=" + sector + ", year=" + Arrays.toString(year) + ", table="
				+ table + "]";
	}

}
