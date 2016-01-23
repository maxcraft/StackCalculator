package com.maxcraft.stackcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
	private DecimalFormatSymbols mDecimalFormatSymbols;
	public String mMinusString;
	public String mDotString;
	private HashMap< Button, Runnable > mBtns = new HashMap<>();
	private View.OnClickListener mBtnClickListener;

	private Calculator mCalculator;
	private TextView mIndicatorTextView;
	private ListView mStackListView;
	private ArrayAdapter< Double > mAdapter;
	private StringBuffer mInputString = new StringBuffer();
	private NumberFormat mNumFormat = new DecimalFormat( "0.##############" );

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		mDecimalFormatSymbols = new DecimalFormatSymbols();
		mMinusString = String.valueOf( mDecimalFormatSymbols.getMinusSign() );
		mDotString = String.valueOf( mDecimalFormatSymbols.getDecimalSeparator() );

		mCalculator = ( Calculator )getLastCustomNonConfigurationInstance();

		if( mCalculator == null )
		{
			mCalculator = new Calculator();
		}

		initViews();

		mIndicatorTextView.setText( mNumFormat.format( mCalculator.getValue() ) );
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance()
	{
		return mCalculator;
	}

	private void initViews()
	{
		mIndicatorTextView = ( TextView )findViewById( R.id.indicator_txt );
		mStackListView = ( ListView )findViewById( android.R.id.list );
		mAdapter = new ArrayAdapter< Double >( this, android.R.layout.simple_list_item_1, new ListInverter<>( mCalculator.getStack() ) );
		mStackListView.setAdapter( mAdapter );

		mBtnClickListener = new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				Runnable command = mBtns.get( v );

				if( command != null )
				{
					command.run();
				}
			}
		};

		initButton( R.id.btn_clear, new ClearCalcCommand() );

		initButton( R.id.btn_0, new AppendDigitCalcCommand( 0 ) );
		initButton( R.id.btn_1, new AppendDigitCalcCommand( 1 ) );
		initButton( R.id.btn_2, new AppendDigitCalcCommand( 2 ) );
		initButton( R.id.btn_3, new AppendDigitCalcCommand( 3 ) );
		initButton( R.id.btn_4, new AppendDigitCalcCommand( 4 ) );
		initButton( R.id.btn_5, new AppendDigitCalcCommand( 5 ) );
		initButton( R.id.btn_6, new AppendDigitCalcCommand( 6 ) );
		initButton( R.id.btn_7, new AppendDigitCalcCommand( 7 ) );
		initButton( R.id.btn_8, new AppendDigitCalcCommand( 8 ) );
		initButton( R.id.btn_9, new AppendDigitCalcCommand( 9 ) );

		initButton( R.id.btn_dot, new AppendDotCalcCommand() );

		initButton( R.id.btn_neg, new NegCalcCommand() );
		initButton( R.id.btn_add, new AddCommand() );
		initButton( R.id.btn_sub, new SubCommand() );
		initButton( R.id.btn_mul, new MulCommand() );
		initButton( R.id.btn_div, new DivCommand() );
		initButton( R.id.btn_push, new PushCommand() );
		initButton( R.id.btn_swap, new SwapCommand() );
		initButton( R.id.btn_back, new BackCalcCommand() );
	}

	private void initButton( int btnId, Runnable command )
	{
		Button btn = ( Button )findViewById( btnId );

		if( btn != null )
		{
			btn.setOnClickListener( mBtnClickListener );
			mBtns.put( btn, command );
		}
	}

	private abstract class CalcCommand implements Runnable
	{
		@Override
		public void run()
		{
			if( mInputString.length() > 0 )
			{
				mIndicatorTextView.setText( mInputString.toString() );
			}
			else
			{
				mIndicatorTextView.setText( mNumFormat.format( mCalculator.getValue() ) );
			}

			mAdapter.notifyDataSetChanged();
		}
	}

	private class InputCalcCommand extends CalcCommand
	{
		@Override
		public void run()
		{
			if( mInputString.length() > 0 )
			{
				try
				{
					mCalculator.setValue( mNumFormat.parse( mInputString.toString() ).doubleValue() );
				}
				catch( ParseException e )
				{
					e.printStackTrace();
				}
			}
			else
			{
				mCalculator.setValue( 0d );
			}

			super.run();
		}
	}

	private class ClearCalcCommand extends InputCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.clear();
			mInputString.setLength( 0 );
			super.run();
		}
	}

	private class AppendDigitCalcCommand extends InputCalcCommand
	{
		public final int digit;

		private AppendDigitCalcCommand( int digit )
		{
			this.digit = digit;
		}

		@Override
		public void run()
		{
			mInputString.append( Integer.toString( digit ) );
			super.run();
		}
	}

	private class AppendDotCalcCommand extends InputCalcCommand
	{
		@Override
		public void run()
		{
			if( mInputString.length() < 1 )
			{
				mInputString.append( "0" );
			}

			if( mInputString.indexOf( mDotString, 0 ) < 0 )
			{
				mInputString.append( mDotString );
			}

			super.run();
		}
	}

	private class BackCalcCommand extends InputCalcCommand
	{
		@Override
		public void run()
		{
			if( mInputString.length() > 0 )
			{
				mInputString.deleteCharAt( mInputString.length() - 1 );
			}

			super.run();
		}
	}

	private class NegCalcCommand extends InputCalcCommand
	{
		@Override
		public void run()
		{
			if( mInputString.length() < 1 )
			{
				mInputString.append( mNumFormat.format( mCalculator.getValue() ) );
			}

			int index = mInputString.indexOf( mMinusString, 0 );

			if( index < 0 )
			{
				mInputString.insert( 0, mMinusString );
			}
			else
			{
				mInputString.deleteCharAt( 0 );
			}

			super.run();
		}
	}

	private class ResultCalcCommand extends CalcCommand
	{
		@Override
		public void run()
		{
			mInputString.setLength( 0 );
			super.run();
		}
	}

	private class AddCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.add();
			super.run();
		}
	}

	private class SubCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.sub();
			super.run();
		}
	}

	private class MulCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.mul();
			super.run();
		}
	}

	private class DivCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.div();
			super.run();
		}
	}

	private class PushCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.push();
			super.run();
		}
	}

	private class SwapCommand extends ResultCalcCommand
	{
		@Override
		public void run()
		{
			mCalculator.swap();
			super.run();
		}
	}
}
