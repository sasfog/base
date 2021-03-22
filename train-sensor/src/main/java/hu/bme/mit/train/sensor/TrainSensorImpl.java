package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import jdk.jshell.spi.ExecutionControl;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		if(isOverAbsoluteMargin(speedLimit) || isOverRelativeMargin(speedLimit))
		{
			user.setAlarmFlag(true);
		}
		else {
			user.setAlarmFlag(false);

		}
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);
	}

	private boolean isOverAbsoluteMargin(int newSpeedLimit){
		return newSpeedLimit < 0 || newSpeedLimit > 500;
	}

	private boolean isOverRelativeMargin(int newSpeedLimit){
		int refSpeed = controller.getReferenceSpeed();
		return newSpeedLimit < refSpeed / 2;
	}
}
