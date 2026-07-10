package com.example.supermine;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

/**
 * Bo'sh packet - client tugmani bosganda serverga "toggle qil" signalini yuboradi.
 */
public record TogglePayload() implements CustomPayload {
	public static final CustomPayload.Id<TogglePayload> ID =
			new CustomPayload.Id<>(Identifier.of("supermine", "toggle"));

	public static final PacketCodec<RegistryByteBuf, TogglePayload> CODEC =
			PacketCodec.unit(new TogglePayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
