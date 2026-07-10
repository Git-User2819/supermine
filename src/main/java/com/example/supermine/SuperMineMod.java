package com.example.supermine;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SuperMineMod implements ModInitializer {

	// Har bir o'yinchi uchun rejim yoqilganmi yoqilmaganini saqlaydi (server tomonida)
	public static final Set<UUID> ENABLED_PLAYERS = new HashSet<>();

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(TogglePayload.ID, TogglePayload.CODEC);

		// Client tugma bosganda shu yerga signal keladi
		ServerPlayNetworking.registerGlobalReceiver(TogglePayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			UUID id = player.getUuid();
			if (ENABLED_PLAYERS.contains(id)) {
				ENABLED_PLAYERS.remove(id);
				player.sendMessage(Text.literal("§c[SuperMine] O'chirildi"), true);
			} else {
				ENABLED_PLAYERS.add(id);
				player.sendMessage(Text.literal("§a[SuperMine] Yoqildi (6x6)"), true);
			}
		});

		// Har safar blok buzilganda ishlaydigan hodisa
		PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
			if (world.isClient) return true;
			if (!(player instanceof ServerPlayerEntity serverPlayer)) return true;
			if (!ENABLED_PLAYERS.contains(player.getUuid())) return true;

			ItemStack tool = player.getMainHandStack();
			boolean isPickaxe = tool.getItem() == Items.NETHERITE_PICKAXE;
			boolean isShovel = tool.getItem() == Items.NETHERITE_SHOVEL;

			if (isPickaxe || isShovel) {
				breakSurroundingArea(world, serverPlayer, pos, tool);
			}

			return true;
		});
	}

	/**
	 * Markaziy blok atrofida 6x6 maydonni (jami 36 blok, markaziy blok o'z-o'zidan
	 * o'yin tomonidan buziladi, biz qolgan 35 tasini qo'shimcha buzamiz) o'yinchi
	 * qarab turgan tekislikka mos ravishda buzadi.
	 */
	private void breakSurroundingArea(World world, ServerPlayerEntity player, BlockPos center, ItemStack tool) {
		HitResult hit = player.raycast(20.0, 1.0f, false);
		Direction side = Direction.UP;
		if (hit instanceof BlockHitResult blockHit) {
			side = blockHit.getSide();
		}

		Direction axisA;
		Direction axisB;
		switch (side.getAxis()) {
			case X -> {
				axisA = Direction.UP;
				axisB = Direction.SOUTH;
			}
			case Z -> {
				axisA = Direction.UP;
				axisB = Direction.EAST;
			}
			default -> {
				axisA = Direction.EAST;
				axisB = Direction.SOUTH;
			}
		}

		// -2..+3 -> 6 ta qiymat, markazni ham hisobga olgan holda 6x6 hosil qiladi
		for (int a = -2; a <= 3; a++) {
			for (int b = -2; b <= 3; b++) {
				if (a == 0 && b == 0) continue; // markaziy blok allaqachon buzilmoqda

				BlockPos targetPos = center.offset(axisA, a).offset(axisB, b);
				BlockState targetState = world.getBlockState(targetPos);

				if (targetState.isAir()) continue;

				float hardness = targetState.getHardness(world, targetPos);
				if (hardness < 0) continue; // bedrock va shunga o'xshash buzilmas bloklar

				if (!tool.isSuitableFor(targetState)) continue; // asbob mos kelmasa o'tkazib yuboriladi

				world.breakBlock(targetPos, true, player);
			}
		}
	}
}
