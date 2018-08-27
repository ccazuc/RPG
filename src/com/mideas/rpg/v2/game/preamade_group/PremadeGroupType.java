package com.mideas.rpg.v2.game.preamade_group;

public enum PremadeGroupType {

	QUESTING((byte)0, "Questing"),
	DUNGEONS((byte)1, "Dungeons"),
	SCENARIOS((byte)2, ""),
	RAIDS_CURR((byte)3, "Raids"),
	RAIDS_LEGACY((byte)4, "Raids - Legacy"),
	ARENAS((byte)5, "Arenas"),
	ARENAS_SKIRMISHES((byte)6, "Arenas Skirmishes"),
	BATTLEGROUNDS((byte)7, "Battlegrounds"),
	RATED_BATTLEGROUNDS((byte)8, "Rated Battlegrounds"),
	ASHRAN((byte)9, "Ashran"),
	CUSTOM((byte)10, "Custom"),
	;

	private final byte value;
	private final String text;
	
	private PremadeGroupType(byte value, String text)
	{
		this.value = value;
		this.text = text;
	}

	public byte getValue()
	{
		return (this.value);
	}
	
	public String getText()
	{
		return  (this.text);
	}

	public static PremadeGroupType getValue(byte value)
	{
		if (value >= 0 && value < PremadeGroupType.values().length)
			return (PremadeGroupType.values()[value]);
		return (null);
	}
}
