package hu.bme.mit.train.interfaces;

public interface TrainUser {

	int getJoystickPosition();

	boolean getAlarmFlag();

	void setAlarmFlag(boolean alarmState);

	void overrideJoystickPosition(int joystickPosition);

}
