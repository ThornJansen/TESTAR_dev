package org.fruit.alayer;

import static org.junit.Assert.*;

import org.fruit.Util;
import org.junit.Test;

public class VerdictTest {
	
	private final double DELTA = 0;
	
	private final Visualizer dummyVisualizer = new Visualizer(){
		private static final long serialVersionUID = -7830649624698071090L;
		public void run(State s, Canvas c, Pen pen) {}	
	};

	@Test
	public void testToString() {
		Verdict v = new Verdict(0.0, "This is a test verdict");
		assertEquals("The string representation of a Verdict shall include its severity and its info",
				"severity: 0.0 info: This is a test verdict", v.toString());
	}

	@Test
	public void testJoin() {
		Verdict v1 = new Verdict(Verdict.SEVERITY_OK, "Foo Bar"),
				v2 = new Verdict(Verdict.SEVERITY_MAX, "Bar"),
				v3 = new Verdict(Verdict.SEVERITY_MIN, "Baz", dummyVisualizer);
		assertTrue("Joining two Verdicts shall create a new Verdict",
				v1 != v1.join(v2));
		assertEquals("Joining two Verdicts shall set the severity to the maximum of both",
				Verdict.SEVERITY_MAX, v3.join(v2).severity(), DELTA);
		assertEquals("If a Verdict's info contains the info of the Verdict to be joined with, " +
				"then only the containing info shall be used",
				"Foo Bar", v1.join(v2).info());
		assertEquals("If a Verdict is OK and its info does not contain the info of the Verdict to be joined with, " +
				"then the containing info shall be discarded",
				"Baz", v1.join(v3).info());
		assertEquals("If a Verdict is not OK and its info does not contain the info of the Verdict to be joined with, " +
				"then both infos shall be included separated by a line break",
				"Bar\nBaz", v2.join(v3).info());
		assertTrue("Joining two Verdicts shall reset the Visualizer to the NullVisualizer",
				v3.join(v1).visualizer() == Util.NullVisualizer);
	}
	
	@Test
	public void testEquals() {
		Verdict v1 = Verdict.OK,
				v2 = Verdict.FAIL,
				v3 = new Verdict(Verdict.SEVERITY_FAIL, "Failure"),
				v4 = new Verdict(Verdict.SEVERITY_FAIL, "Different failure");
		assertTrue(Verdict.OK.equals(Verdict.OK));
		assertTrue(v1.equals(Verdict.OK));
		assertTrue(Verdict.OK.equals(v1));
		assertTrue(v2.equals(Verdict.FAIL));
		assertTrue(Verdict.FAIL.equals(v2));
		assertFalse(v1.equals(Verdict.FAIL));
		assertFalse(v3.equals(Verdict.FAIL));
		assertFalse(v3.equals(v4));
		assertFalse(v4.equals(v3));
	}

}
