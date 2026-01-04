/*
Copyright 2025 FIRST Tech Challenge Team 3750

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This file contains a minimal example of an iterative (Non-Linear) "OpMode". An OpMode is a
 * 'program' that runs in either the autonomous or the TeleOp period of an FTC match. The names
 * of OpModes appear on the menu of the FTC Driver Station. When an selection is made from the
 * menu, the corresponding OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@Autonomous(name = "Red Shooter")

public class shooterRed extends OpMode {
	/* Declare OpMode members. */
	private VoltageSensor hubVoltSens;
	private ElapsedTime time;
	private DcMotorEx frontLeft;
	private DcMotorEx frontRight;
	private DcMotorEx backLeft;
	private DcMotorEx backRight;
	private DcMotor shooterL;
	private DcMotor shooterR;
	private DcMotor intake;
	double voltage;
	double vMult;

	@Override
	public void init() {
		telemetry.addData("Status", "Initialized");
		telemetry.addData("Warning", "\n\nThe bot MUST start at the goal for this auto\nensure testing before using this auto");
		frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
		frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
		backLeft = hardwareMap.get(DcMotorEx.class, "back_left");
		backRight = hardwareMap.get(DcMotorEx.class, "back_right");
		shooterL = hardwareMap.get(DcMotor.class, "shooterL");
		shooterR = hardwareMap.get(DcMotor.class, "shooterR");
		intake = hardwareMap.get(DcMotor.class, "intake");
		hubVoltSens = hardwareMap.get(VoltageSensor.class, "Control Hub");
		
		time = new ElapsedTime();
		
		frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		frontLeft.setDirection(DcMotor.Direction.REVERSE);
		frontRight.setDirection(DcMotor.Direction.FORWARD);
		backLeft.setDirection(DcMotor.Direction.REVERSE);
		backRight.setDirection(DcMotor.Direction.FORWARD);
		
		frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */
	@Override
	public void init_loop() {
	}

	/*
	 * Code to run ONCE when the driver hits PLAY
	 */
	@Override
	public void start() {
		time.reset();
	}

	/*
	 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
	 */
	@Override
	public void loop() {
		double maxDist = 2500;
		if (time.seconds() < 2) {
			telemetry.addData("fl", frontLeft.getCurrentPosition());
			telemetry.addData("fr", frontRight.getCurrentPosition());
			telemetry.addData("bl", backLeft.getCurrentPosition());
			telemetry.addData("br", backRight.getCurrentPosition());
			frontLeft.setPower(clipExtra((maxDist-frontLeft.getCurrentPosition())/maxDist*0.9));
			frontRight.setPower(clipExtra((maxDist-frontRight.getCurrentPosition())/maxDist*0.9));
			backLeft.setPower(clipExtra((maxDist-frontLeft.getCurrentPosition())/maxDist*0.9));
			backRight.setPower(clipExtra((maxDist-frontRight.getCurrentPosition())/maxDist*0.9));	
		}
		

		else if (time.seconds() < 3.5) {
			frontLeft.setPower(0);
			frontRight.setPower(0);
			backLeft.setPower(0);
			backRight.setPower(0);
			
			voltage = hubVoltSens.getVoltage();
			vMult = 12/voltage;
			shooterL.setPower(1*.4*vMult);
			shooterR.setPower(-1*.4*vMult);
		}
		
			else if (time.seconds() < 5) {
			frontLeft.setPower(0);
			frontRight.setPower(0);
			backLeft.setPower(0);
			backRight.setPower(0);
			
			voltage = hubVoltSens.getVoltage();
			vMult = 12/voltage;
			shooterL.setPower(1*.5*vMult);
			shooterR.setPower(-1*.5*vMult);
		}
		
		else if (time.seconds() < 12) { // I'd set 9 here, but 12 for certainty that the holder is cleared
			intake.setPower(0.5);
		}
		
		else if (time.seconds() < 13.5) {//I'd set 9 here, but 12 for certainty that the holder is cleared
			intake.setPower(0);
			frontLeft.setPower(-0.5);
			frontRight.setPower(0.5);
			backLeft.setPower(0.5);
			backRight.setPower(-0.5);
		}
		
		else {
			intake.setPower(0);
			shooterL.setPower(0);
			shooterR.setPower(0);
			frontLeft.setPower(0);
			frontRight.setPower(0);
			backLeft.setPower(0);
			backRight.setPower(0);
			
		}

	}

	/*
	 * Code to run ONCE after the driver hits STOP
	 */
	@Override
	public void stop() {}
	
	private double clipExtra(double intx) {
		if (intx < -1) {
		  intx = -1;
		} else if (intx > 1) {
		  intx = 1;
		}
		return intx;
	}
}
