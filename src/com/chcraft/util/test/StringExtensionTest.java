package com.chcraft.util.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.chcraft.util.StringExtension;

public class StringExtensionTest {
	private String nullString;
	private String emptyString;
	private String upperCaseString;
	private String lowerCaseString;
	private String startWithDigit;
	private String startWithSpecialLetter;
	
	@Before
	public void init() {
		emptyString = "";
		upperCaseString = "AKTNF";
		lowerCaseString = "aktnf";
		startWithDigit = "1201AKTNF";
		startWithSpecialLetter = "@#Aktnf";
	}
	
	@Test
	public void toFirstCharUpperCaseTest() {
		assertThat(StringExtension.toFirstCharUpperCase(nullString), is(""));
		assertThat(StringExtension.toFirstCharUpperCase(emptyString), is(""));
		assertThat(StringExtension.toFirstCharUpperCase(startWithDigit), is(startWithDigit));
		assertThat(StringExtension.toFirstCharUpperCase(startWithSpecialLetter), is(startWithSpecialLetter));
		assertThat(StringExtension.toFirstCharUpperCase(upperCaseString), is("AKTNF"));
		assertThat(StringExtension.toFirstCharUpperCase(lowerCaseString), is("Aktnf"));
	}
	
	@Test
	public void toFirstCharLowerCaseTest() {
		assertThat(StringExtension.toFirstCharLowerCase(nullString), is(""));
		assertThat(StringExtension.toFirstCharLowerCase(emptyString), is(""));
		assertThat(StringExtension.toFirstCharLowerCase(startWithDigit), is(startWithDigit));
		assertThat(StringExtension.toFirstCharLowerCase(startWithSpecialLetter), is(startWithSpecialLetter));
		assertThat(StringExtension.toFirstCharLowerCase(upperCaseString), is("aKTNF"));
		assertThat(StringExtension.toFirstCharLowerCase(lowerCaseString), is("aktnf"));
	}
	
}
