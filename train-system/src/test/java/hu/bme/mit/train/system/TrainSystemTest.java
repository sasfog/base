package hu.bme.mit.train.system;

import hu.bme.mit.train.interfaces.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.system.TrainSystem;

import java.util.Date;
import java.util.Timer;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	TrainTachograph tachograph;
	TrainRunner runner;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tachograph = system.getTachograph();
		runner = system.getRunner();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}


	@Test
	public void OverrideSpeedLimitToNegative_SetsSpeedLimitToZero(){
		sensor.overrideSpeedLimit(-5);
		Assert.assertEquals(0, controller.getSpeedLimit());
		Assert.assertNotEquals(-5, controller.getSpeedLimit());
	}

	@Test
	public void OverrideSpeedLimitToNegative_SetsSpeedLimitToZero_2(){
		sensor.overrideSpeedLimit(-5);
		Assert.assertEquals(0, controller.getSpeedLimit());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void LogDataToTachograph(){
		Assert.assertTrue(tachograph.isEmpty());
		tachograph.recordData();
		Assert.assertFalse(tachograph.isEmpty());
		Assert.assertEquals( 1, tachograph.getData());
		user.overrideJoystickPosition(5);
		sensor.overrideSpeedLimit(10);
		tachograph.recordData();
		Assert.assertFalse(tachograph.isEmpty());
		user.overrideJoystickPosition(3);
		sensor.overrideSpeedLimit(5);
		tachograph.recordData();

		Assert.assertEquals(3, tachograph.getData());
	}

}
