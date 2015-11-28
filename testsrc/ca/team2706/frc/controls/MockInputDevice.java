package ca.team2706.frc.controls;

import java.util.ArrayList;
import java.util.List;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.components.ui.InputDevice;
import org.strongback.mock.Mock;

public abstract class MockInputDevice implements InputDevice {

	protected List<ContinuousRange> axes;
	protected List<Switch> buttons;
	protected List<DirectionalAxis> pads;
	
	protected List<Integer> padVals;
	
	public MockInputDevice() {
		axes = new ArrayList<ContinuousRange>();
		buttons = new ArrayList<Switch>();
		pads = new ArrayList<DirectionalAxis>();
		
		padVals = new ArrayList<Integer>();
	}
	
	@Override
	public ContinuousRange getAxis(int axis) {
		if(axis < 0 || axis >= axes.size())
			return null;
		else
			return axes.get(axis);
	}
	
	public void setOrAddAxis(int axisNum, ContinuousRange axis, boolean expandAxisCount) {
		if(axisNum >= axes.size())
			if((expandAxisCount && axisNum >= 0) || axisNum == axes.size()) {
				for(int i = axes.size(); i <= axisNum-1; i++)
					axes.add(null);
				axes.add(axis);
			}
			else
				return;
		else
			axes.set(axisNum, axis);
	}
	

	@Override
	public Switch getButton(int button) {
		if(button < 0 || button >= axes.size())
			return null;
		else
			return buttons.get(button);
	}
	
	public void setOrAddButton(int buttonNum, Switch button, boolean expandButtonCount) {
		if(buttonNum >= buttons.size())
			if((expandButtonCount && buttonNum >= 0) || buttonNum == buttons.size()) {
				for(int i = buttons.size(); i <= buttonNum-1; i++)
					buttons.add(Mock.notTriggeredSwitch());
				buttons.add(button);
			}
			else
				return;
		else
			buttons.set(buttonNum, button);
	}
	

	@Override
	public DirectionalAxis getDPad(int pad) {
		if(pad < 0 || pad >= axes.size())
			return null;
		else
			return pads.get(pad);
	}
	
	public void setOrAddDPad(int padNum, DirectionalAxis pad, boolean expandPadCount) {
		if(padNum >= padVals.size())
			if((expandPadCount && padNum >= 0) || padNum == padVals.size()) {
				for(int i = padVals.size(); i <= padNum-1; i++) {
					padVals.add(null);
					pads.add(null);
				}
				padVals.add(pad.getDirection());
				pads.add(() -> padVals.get(pads.size()));
			}
			else
				return;
		else
			padVals.set(padNum, pad.getDirection());
	}
	
	public static ContinuousRange createContinuousRange(double value) {
		return () -> value;
	}
	
	public static DirectionalAxis createDirectionalAxis(int value) {
		return () -> value;
	}
}
