package com.bewitchment.common.core.capability.transformation;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.api.transformation.ITransformation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTransformationData implements ITransformationData {

	@CapabilityInject(ITransformationData.class)
	public static final Capability<ITransformationData> CAPABILITY = null;

	ITransformation type = DefaultTransformations.NONE;
	int level = 0, blood = 0;
	boolean isNightVisionActive = false;

	public CapabilityTransformationData() {
	}

	public static void init() {
		CapabilityManager.INSTANCE.register(ITransformationData.class, new TransformationDataStorage(), CapabilityTransformationData::new);
	}

	@Override
	public ITransformation getType() {
		if (type == null) {
			return DefaultTransformations.NONE;
		}
		return type;
	}

	/**
	 * Prefer the use of {@link com.bewitchment.common.core.helper.TransformationHelper#setTypeAndLevel()} over this. That one automatically takes
	 * care of syncronization and refreshing data
	 */
	@Override
	public void setType(ITransformation type) {
		this.type = type;
		if (type != DefaultTransformations.VAMPIRE) {
			blood = 0;
		} else {
			blood = getMaxBlood();
		}
	}

	@Override
	public int getLevel() {
		return level;
	}

	/**
	 * Prefer the use of {@link com.bewitchment.common.core.helper.TransformationHelper#setTypeAndLevel()} over this. That one automatically takes
	 * care of syncronization and refreshing data
	 */
	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean addVampireBlood(int amount) {
		if (getBlood() >= getMaxBlood() && amount > 0)
			return false;
		if (amount + getBlood() < 0)
			return false;
		blood += amount;
		return true;
	}

	@Override
	public int getBlood() {
		return blood;
	}

	@Override
	public int getMaxBlood() {
		int max = 50 + 155 * getLevel();
		if (getBlood() > max) {
			setBlood(max);
		}
		return max; // lvl 0: 50, lvl 5: 825, lvl 10: 1600
	}

	@Override
	public void setBlood(int blood) {
		this.blood = blood;
	}

	@Override
	public boolean isNightVisionActive() {
		return isNightVisionActive;
	}

	@Override
	public void setNightVision(boolean flag) {
		isNightVisionActive = flag;
	}

}
