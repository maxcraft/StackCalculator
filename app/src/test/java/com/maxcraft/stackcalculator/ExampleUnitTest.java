package com.maxcraft.stackcalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest
{
	private Calculator mCalculator;
	private double[] mData = {5,2,3,1};
	private double mDelta = 0.0001;

	@Before
	public void setUp() {
		mCalculator = new Calculator();
	}

	@Test
	public void firstAdd_isCorrect()
	{
        mCalculator.setValue(mData[0]);
		mCalculator.add();
		assertEquals( 5d, mCalculator.getValue(), mDelta );
	}

	@Test
	public void add_isCorrect()
	{
		mCalculator.setValue(mData[0]);
		mCalculator.push();
		mCalculator.setValue(mData[1]);
		mCalculator.push();
		mCalculator.setValue(mData[2]);
		mCalculator.push();
		mCalculator.add();
		mCalculator.add();
		mCalculator.add();
		assertEquals( 13d, mCalculator.getValue(), mDelta );
	}

	@Test
	public void firstSub_isCorrect()
	{
		mCalculator.setValue(mData[1]);
		mCalculator.sub();
		assertEquals( -2d, mCalculator.getValue(), mDelta );
	}

	@Test
	public void sub_isCorrect()
	{
		mCalculator.setValue(mData[0]);
		mCalculator.add();
		mCalculator.push();
		mCalculator.sub();
		assertEquals( 0d, mCalculator.getValue(), mDelta );
	}

	@Test
	public void firstMul_isCorrect()
	{
		mCalculator.setValue(mData[2]);
		mCalculator.mul();
		assertEquals( 0, mCalculator.getValue(), mDelta );
	}

	@Test
	public void firstDiv_isCorrect()
	{
		mCalculator.setValue(mData[3]);
		mCalculator.div();
		assertEquals( 0, mCalculator.getValue(), mDelta );
	}

	@Test
	public void divZero_isCorrect()
	{
		mCalculator.setValue(0d);
		mCalculator.div();
		assertEquals( true, Double.isNaN(mCalculator.getValue()) );
	}

	@Test
	public void div_isCorrect()
	{
		mCalculator.setValue(mData[0]);
		mCalculator.add();
		mCalculator.push();
		mCalculator.swap();
		mCalculator.div();
		assertEquals( 1, mCalculator.getValue(), mDelta );
	}

	@Test
	public void divList_isCorrect()
	{
		mCalculator.setValue(mData[0]);
		mCalculator.push();
		mCalculator.setValue(mData[1]);
		mCalculator.push();
		mCalculator.setValue(mData[2]);
		mCalculator.push();
		mCalculator.div();
		mCalculator.div();
		mCalculator.div();
		assertEquals( 2.5, mCalculator.getValue(), mDelta );
	}

	@Test
	public void getEmptyStackValue_isCorrect()
	{
		assertEquals( 0, mCalculator.getValue(), mDelta );
	}

	@Test
	public void getStackClear_isCorrect()
	{
		mCalculator.setValue(mData[2]);
		mCalculator.add();
		mCalculator.push();
		mCalculator.add();
		mCalculator.clear();
		assertEquals( 0, mCalculator.getValue(), mDelta );
	}

	@Test
	public void push_isCorrect()
	{
		mCalculator.setValue(mData[0]);
		mCalculator.push();
		mCalculator.push();
		mCalculator.push();
		mCalculator.push();
		assertEquals( 5, mCalculator.getStack().size() );
	}
}