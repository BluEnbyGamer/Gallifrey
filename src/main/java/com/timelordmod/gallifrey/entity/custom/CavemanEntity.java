package com.timelordmod.gallifrey.entity.custom;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Cavemen of the Prehistoric dimension. They wander in small groups carrying a stone tool, leave
 * you alone, and fight back together if one of them is hit (like zombified piglins).
 */
public class CavemanEntity extends PathAwareEntity {

    private static final Item[] STONE_TOOLS = {
            Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_HOE
    };

    public CavemanEntity(EntityType<? extends CavemanEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        // Hit one and the rest of the group nearby joins in.
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData data = super.initialize(world, difficulty, reason, entityData, entityNbt);
        Random random = world.getRandom();
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(STONE_TOOLS[random.nextInt(STONE_TOOLS.length)]));
        setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.1F);
        return data;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }
}
