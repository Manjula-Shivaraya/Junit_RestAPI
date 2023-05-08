package com.psl.Junit_RestAPI;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CalciTest {

	Calci calci = new Calci();

	@BeforeAll // Executes before all test methods gets executed
	static void initAll() {
		System.out.println(" @Beforeall ");
	}

	@BeforeEach // Executes before each test methods gets executed
	void init() {
		System.out.println("@BeforeEach ");
	}

	@DisplayName("AssertEqual & NotEqual") // Checks if the expected and the actual value/results are same.
	@Test
	void assertEqualTest() {
		assertNotEquals(7, calci.add(5, 5), "AssertNotEqual Test is failed");
		assertEquals(20, calci.multiply(10, 2), "AssertEqual test failed");
	}

	@DisplayName("AssertArrayEqual") // Compares if the 2 arrays are equal.
	@Test
	void assertArrayEqualTest() {
		assertArrayEquals(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 }, "AssertArrayEqual test failed"); // If Expected
																											// and
																											// Actual
																											// results
																											// are same,
																											// then it
																											// will pass
																											// the test
																											// case.
	}

	@Test
	@DisplayName("Assert iterable equal") // Compares if the 2 lists are equal.
	public void assetIterableEqual() {

		Integer a[] = new Integer[] { 10, 20, 30, 40 };
		Iterable<Object> list = Arrays.asList(a);
		Integer b[] = new Integer[] { 10, 20, 30, 40 };
		Iterable<Object> list2 = Arrays.asList(b);

		Assertions.assertIterableEquals(list, list2);

	}

	@Test
	@DisplayName("AssertNOTNULL") // Checks if the provided string is equal to null or not.
	public void AssertNOTNULL() {
		String nullString = null;
		String notNullString = "ABC";
		Assertions.assertNotNull(notNullString);
		assertNull(nullString);
	}

	@Test
	@DisplayName("AssertTrue")
	public void assertTruetest() {
		boolean value1 = true;
		boolean value2 = false;
		assertFalse(value2, "Assert False test failed");
		assertTrue(value1);
	}

	@Test
	@DisplayName("AssertThrows")
	public void assertexception() {
		Assertions.assertThrows(ArithmeticException.class, () -> calci.divide(10, 0),
				"Assert Exception test case failed");

	}

	@Test
	@DisplayName("AssertAll") // used to do multiple test under one Test method
	public void AsserAllTest() {
		Assertions.assertAll(() -> assertEquals(4, calci.add(1, 3)));
		Assertions.assertAll(() -> assertEquals(2, calci.divide(10, 5)));
		Assertions.assertAll(() -> assertEquals(2, calci.add(-1, 3)));
		Assertions.assertAll(() -> assertEquals(1, calci.add(1, 0)));
	}

//	@Test
//	@DisplayName("AssertFails")
//	public void AssertfailsTest() {
//		Assertions.fail("This test case will fail all the time");
//	}

	@DisplayName("DIVISION") // In the Junit section where we can see how many test cases passed, there the
								// testDivide method is been displayed as DIVISION
	@Test
	void testDivide() {
		assertEquals(5, calci.divide(10, 2));
	}

	@Test
	@Disabled // It skips this particular method to get executed. Therefore this method will
				// not get executed.
	void testDivide1() {
		assertEquals(50, calci.divide(100, 2));
	}

	@Nested // Nested inner class
	@DisplayName("Nested")
	class NestedTest {

		@Test
		void testDivide() {
			assertEquals(5, calci.divide(10, 2));
		}

		@Test
		void testDivide2() {
			assertEquals(5, calci.divide(10, 2));
		}

		@Test
		void testDivide3() {
			assertEquals(5, calci.divide(10, 2));
		}
	}

	@AfterEach // gets executed after each test method
	void tearThis() {
		System.out.println("@AfterEach executed");
	}

	@AfterAll // gets executed after all the test methods
	static void tear() {
		System.out.println("@AfterAll executed");
	}

}
