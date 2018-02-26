package com.maxcraft.stackcalculator;

import java.util.List;
import java.util.Stack;

/**
 * Created by Maksym.Kravtsov on 1/23/2016.
 */
class Calculator
{
	private final Stack< Double > mStack = new Stack<>();

	private static class BiOperands
	{
		public Double first;
		public Double second;
	}

	public void clear()
	{
		mStack.clear();
	}

	public void add()
	{
		BiOperands operands = readBiOperands();
		double result = operands.second + operands.first;

		mStack.push( result );
	}

	public void sub()
	{
		BiOperands operands = readBiOperands();
		double result = operands.second - operands.first;

		mStack.push( result );
	}

	public void mul()
	{
		BiOperands operands = readBiOperands();
		double result = operands.second * operands.first;

		mStack.push( result );
	}

	public void div()
	{
		BiOperands operands = readBiOperands();
		double result = operands.second / operands.first;

		mStack.push( result );
	}

	public void push()
	{
		Double value = mStack.peek();
		mStack.push( value );
	}

	public void swap()
	{
		if( mStack.size() > 0 )
		{
			BiOperands operans = readBiOperands();
			mStack.push( operans.first );
			mStack.push( operans.second );
		}
	}

	private BiOperands readBiOperands()
	{
		BiOperands retval = new BiOperands();
		retval.first = getStackValue();
		retval.second = getStackValue();

		return retval;
	}

	private Double getStackValue()
	{
		return ( mStack.size() > 0 ) ? mStack.pop() : 0.0;
	}

	public List< Double > getStack()
	{
		return mStack;
	}

	public Double getValue()
	{
		if( mStack.size() < 1 )
		{
			mStack.push( 0.0 );
		}

		return mStack.peek();
	}

	public void setValue( Double value )
	{
		if( mStack.size() > 0 )
		{
			mStack.pop();
		}

		mStack.push( value );
	}
}
