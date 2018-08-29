package com.mideas.rpg.v2.files;

public enum FileType
{

	SPELL_DBC((byte)0),
	AURA_DBC((byte)1),
	
	;
	private final byte value;
	
	private FileType(byte value)
	{
		this.value = value;
	}
	
	public byte getValue()
	{
		return this.value;
	}
}
