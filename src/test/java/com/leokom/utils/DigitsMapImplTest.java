package com.leokom.utils;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

public class DigitsMapImplTest {
	@Test( expected = NoSuchElementException.class )
	public void noDataNoResults() {
		new DigitsMapImpl().get('a');
	}
	
	@Test
	public void noDataNoNextSequence() {
		assertFalse( new DigitsMapImpl().gotoNextSequence() );
	}
}
