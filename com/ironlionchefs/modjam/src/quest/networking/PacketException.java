package com.ironlionchefs.modjam.src.quest.networking;
public class PacketException extends Exception
{
	public PacketException()
	{
	}

	public PacketException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PacketException(String message)
	{
		super(message);
	}

	public PacketException(Throwable cause)
	{
		super(cause);
	}
}