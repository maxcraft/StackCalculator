package com.maxcraft.stackcalculator;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Maksym.Kravtsov on 1/23/2016.
 */
class ListInverter< E > implements List< E >
{
	private final List< E > mList;

	public ListInverter( List< E > list )
	{
		mList =list;
	}

	@Override
	public void add( int location, E object )
	{
		mList.add( invertLocation( location ), object );
	}

	@Override
	public boolean add( E object )
	{
		return mList.add( object );
	}

	@Override
	public boolean addAll(int location, @NonNull Collection< ? extends E > collection )
	{
		return mList.addAll( invertLocation( location ), collection );
	}

	@Override
	public boolean addAll(@NonNull Collection< ? extends E > collection )
	{
		return mList.addAll( collection );
	}

	@Override
	public void clear()
	{
		mList.clear();
	}

	@Override
	public boolean contains( Object object )
	{
		return mList.contains( object );
	}

	@Override
	public boolean containsAll(@NonNull Collection< ? > collection )
	{
		return mList.containsAll( collection );
	}

	@Override
	public E get( int location )
	{
		return mList.get( invertLocation( location ) );
	}

	@Override
	public int indexOf( Object object )
	{
		return invertLocation( mList.indexOf( object ) );
	}

	@Override
	public boolean isEmpty()
	{
		return mList.isEmpty();
	}

	@Override
	public Iterator< E > iterator()
	{
		return null;
	}

	@Override
	public int lastIndexOf( Object object )
	{
		return invertLocation( mList.lastIndexOf( object ) );
	}

	@Override
	public ListIterator< E > listIterator()
	{
		return null;
	}

	@Override
	public ListIterator< E > listIterator( int location )
	{
		return null;
	}

	@Override
	public E remove( int location )
	{
		return mList.remove( invertLocation( location ) );
	}

	@Override
	public boolean remove( Object object )
	{
		return mList.remove( object );
	}

	@Override
	public boolean removeAll(@NonNull Collection< ? > collection )
	{
		return mList.removeAll( collection );
	}

	@Override
	public boolean retainAll(@NonNull Collection< ? > collection )
	{
		return mList.retainAll( collection );
	}

	@Override
	public E set( int location, E object )
	{
		return mList.set( invertLocation( location ), object );
	}

	@Override
	public int size()
	{
		return mList.size();
	}

	@NonNull
	@Override
	public List< E > subList( int start, int end )
	{
		return new ListInverter<>( mList.subList( invertLocation( start ), invertLocation( end ) ) );
	}

	@NonNull
	@Override
	public Object[] toArray()
	{
		return mList.toArray();
	}

	@NonNull
	@Override
	public < T > T[] toArray(@NonNull T[] array )
	{
		return array;
	}

	private int invertLocation( int location )
	{
		return mList.size() - 1 - location;
	}
}
