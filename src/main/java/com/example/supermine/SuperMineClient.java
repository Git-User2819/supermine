package com.example.supermine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SuperMineClient implements ClientModInitializer {

	// Default tugma: B (o'zgartirish uchun Options -> Controls -> Super Mine Mod bo'limidan foydalaning)
	private static KeyBinding toggleKey;

	@Override
	public void onInitializeClient() {
		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.supermine.toggle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_B,
				"category.supermine"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.wasPressed()) {
				ClientPlayNetworking.send(new TogglePayload());
			}
		});
	}
}
