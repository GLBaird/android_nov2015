package com.example.listsandmodels;

public class PeopleModel {
	
	private String mSurname;
	private String mForename;
	
	public PeopleModel(String surname, String forename) {
		mSurname  = surname;
		mForename = forename;
	}
	
	@Override
	public String toString() {
		return mForename + " " + mSurname;
	}

	public String getSurname() {
		return mSurname;
	}

	public void setSurname(String surname) {
		mSurname = surname;
	}

	public String getForename() {
		return mForename;
	}

	public void setForename(String forename) {
		mForename = forename;
	}
	
}
