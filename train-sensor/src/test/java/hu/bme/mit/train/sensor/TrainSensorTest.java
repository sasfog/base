package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    @Mock
    private TrainController controller;
    @Mock
    private TrainUser user;

    private TrainSensor sensor;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        sensor = new TrainSensorImpl(controller, user);
    }
    @Test
    public void AlarmState_OverAbsoluteMarginWithNegative() {
        verify(user,times(0)).setAlarmFlag(true);
        sensor.overrideSpeedLimit(-100);
        verify(user).setAlarmFlag(true);
        sensor.overrideSpeedLimit(-1);
        verify(user,times(2)).setAlarmFlag(true);
    }

    @Test
    public void AlarmState_OverAbsoluteMarginWithOverFiveHundred() {
        verify(user,times(0)).setAlarmFlag(true);
        sensor.overrideSpeedLimit(501);
        verify(user).setAlarmFlag(true);
        sensor.overrideSpeedLimit(1500);
        verify(user,times(2)).setAlarmFlag(true);
    }

    @Test
    public void AlarmState_OverRelativeMargin() {
        verify(user,times(0)).setAlarmFlag(true);
        when(controller.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        verify(user).setAlarmFlag(true);
    }
    @Test
    public void AlarmState_OverAbsoluteAndRelativeMargin() {
        verify(user,times(0)).setAlarmFlag(true);
        when(controller.getReferenceSpeed()).thenReturn(1500);
        sensor.overrideSpeedLimit(650);
        verify(user).setAlarmFlag(true);
    }

    @Test
    public void AlarmState_UnderAbsoluteAndRelativeMargin() {
        verify(user,times(0)).setAlarmFlag(true);
        when(controller.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(100);
        verify(user).setAlarmFlag(false);
    }
}
