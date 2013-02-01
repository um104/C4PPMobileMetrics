package edu.channel4.mm.db.android.activity;

import android.test.AndroidTestCase;

/**
 * This test is used for setting up automated testing.
 * 
 * @author ray
 *
 */

public class FakeTest extends AndroidTestCase {

	int one, two;
	protected void setUp() {
		one = 1;
		two = 2;
	}
	
	public void testFake() {
		assertEquals(3, one+two);
	}
	
	public void testFakeFail() {
		assertEquals(4, one+two);
	}

}
