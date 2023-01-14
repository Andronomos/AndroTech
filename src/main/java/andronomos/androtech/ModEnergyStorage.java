package andronomos.androtech;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
	public ModEnergyStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int extractedEnergy = super.extractEnergy(maxExtract, simulate);
		if(extractedEnergy != 0) {
			onEnergyChanged();
		}
		return extractedEnergy;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return super.receiveEnergy(maxReceive, simulate);
	}

	public int setEnergy(int energy) {
		this.energy = energy;
		return energy;
	}

	public abstract void onEnergyChanged();
}
