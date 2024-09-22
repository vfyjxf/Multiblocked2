package com.lowdragmc.mbd2.common.trait.forgeenergy;

import com.lowdragmc.lowdraglib.syncdata.ITagSerializable;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class CopiableEnergyStorage extends EnergyStorage implements ITagSerializable<Tag> {

    public CopiableEnergyStorage(int capacity) {
        super(capacity);
    }

    public CopiableEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CopiableEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CopiableEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public CopiableEnergyStorage copy() {
        return new CopiableEnergyStorage(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public void deserializeNBT(Tag tag) {
        super.deserializeNBT(tag);
    }

    @Override
    public Tag serializeNBT() {
        return super.serializeNBT();
    }
}
