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
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
//programmed w/ pain by Mark Chalamish, circa Dec 2025
/*

	   ______________
	  /_____________/\
	  \ \		  / /
	___\ \________/_/___
   /____\ \____________/\
   \ \   \ \	/ /   / /
 ___\ \___\_\__/ /___/_/___
/\___\ \______/ /_________/\
\ \   \ \   \ \/   / /   / /
 \ \   \ \  /\ \  / /   / /
  \ \   \ \/ /\ \/ /   / /
   \ \   \/ /  \/ /   / /
	\ \  / /\  / /\  / /
	 \ \/ /\ \/ /\ \/ /
	  \_\/  \_\/  \_\/
"gl my fellow coders"-Titanc3/Mark 2025
*/

@TeleOp(name = "FTC Decode Teleop") //Decode bot config
public class TeleopDecode extends OpMode { // NOT linearopmode, processes are different, be wary
	
	private VoltageSensor hubVoltSens;
	private DcMotor frontLeft;
	private DcMotor frontRight;
	private DcMotor backLeft;
	private DcMotor backRight;
	private DcMotor shooterL;
	private DcMotor shooterR;
	private DcMotor intake;
	private ServoController ControlHub_ServoController;
	double mvSpeed = 1;
	double shootSpeed = 0.5;
	double voltage;
	double vMult;


	private DcMotor temp;
	boolean isInverted = false;

	@Override
	public void init() {
		telemetry.addData("Status", "Initialized");
		frontLeft = hardwareMap.get(DcMotor.class, "front_left");
		frontRight = hardwareMap.get(DcMotor.class, "front_right");
		backLeft = hardwareMap.get(DcMotor.class, "back_left");
		backRight = hardwareMap.get(DcMotor.class, "back_right");
		shooterL = hardwareMap.get(DcMotor.class, "shooterL");
		shooterR = hardwareMap.get(DcMotor.class, "shooterR");
		intake = hardwareMap.get(DcMotor.class, "intake");
		hubVoltSens = hardwareMap.get(VoltageSensor.class, "Control Hub");
		
		frontLeft.setDirection(DcMotor.Direction.REVERSE);
		frontRight.setDirection(DcMotor.Direction.FORWARD);
		backLeft.setDirection(DcMotor.Direction.REVERSE);
		backRight.setDirection(DcMotor.Direction.FORWARD);
		

	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */
	@Override
	public void init_loop() {
		gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
		telemetry.addData("Mission", "\nCook up that Competition🦾🔥,\n You got this ");
	}

	/*
	 * Code to run ONCE when the driver hits PLAY
	 */
	@Override
	public void start() {
	}

	/*
	 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
	 */
	@Override
	public void loop() {
		voltage = hubVoltSens.getVoltage();
		vMult = 12/voltage;
		telemetry.addData("Battery", voltage);
		telemetry.addData("Translation Speed", mvSpeed*100); // [MOVEMENT]
		if (gamepad1.right_stick_x > 0.1) { //detect input, swap mode to rotation
			frontLeft.setPower(mvSpeed * gamepad1.right_stick_x - 0.1); // ignores mv speed, only translation needs precision
			frontRight.setPower(-1 * mvSpeed * gamepad1.right_stick_x + 0.1);
			backLeft.setPower(mvSpeed * gamepad1.right_stick_x - 0.1);
			backRight.setPower(-1 * mvSpeed * gamepad1.right_stick_x + 0.1);
		}
		else if (gamepad1.right_stick_x < -0.1) { //detect input, swap mode to rotation
			frontLeft.setPower(mvSpeed * gamepad1.right_stick_x + 0.1); // ignores mv speed, only translation needs precision
			frontRight.setPower(-1 * mvSpeed * gamepad1.right_stick_x - 0.1);
			backLeft.setPower(mvSpeed * gamepad1.right_stick_x + 0.1);
			backRight.setPower(-1 * mvSpeed * gamepad1.right_stick_x - 0.1);
		}
		else { //otherwise translate
			frontLeft.setPower(clipExtra(gamepad1.left_stick_x+(-1*gamepad1.left_stick_y)) * mvSpeed);
			frontRight.setPower(clipExtra((-1 * gamepad1.left_stick_x) + (-1*gamepad1.left_stick_y)) * mvSpeed);
			backLeft.setPower(clipExtra((-1 * gamepad1.left_stick_x) + (-1*gamepad1.left_stick_y)) * mvSpeed);
			backRight.setPower(clipExtra(gamepad1.left_stick_x+(-1*gamepad1.left_stick_y)) * mvSpeed);
		}
		
		
		if (gamepad1.dpadDownWasPressed()) {mvSpeed = 0.5; gamepad1.rumble(0.1, 0, 500);} // [MOVEMENT SPEED]
		if (gamepad1.dpadUpWasPressed()) {mvSpeed = 1; gamepad1.rumble(1, 0, 500);}
		
		
		if (gamepad1.crossWasPressed()) {shootSpeed = 0.50; gamepad1.rumble(0.1, 0, 500);} // [OUTTAKE SPEED]
		if (gamepad1.circleWasPressed()) {shootSpeed = 0.55; gamepad1.rumble(0.6, 0, 500);}
		if (gamepad1.triangleWasPressed()) {shootSpeed = 0.6; gamepad1.rumble(1, 0, 500);}


		if (gamepad1.left_bumper) { // [INTAKE]
			intake.setPower(0.7*vMult);
		}
		else if (gamepad1.left_trigger > 0.2) {intake.setPower(-0.5*vMult);}
		else {intake.setPower(0);}
		
		
		if (gamepad1.right_trigger > 0.2) { // [OUTTAKE]
			shooterL.setPower(1*shootSpeed*vMult);
			shooterR.setPower(-1*shootSpeed*vMult);
		}
		else {
			shooterL.setPower(0);
			shooterR.setPower(0);
		}
		
		if (isInverted) {telemetry.addData("Movement", "Inverted");}
		else {telemetry.addData("Movement", "Normal");}
		
		if (gamepad1.psWasPressed()) { // [Invert controls]
			if (!isInverted) {
				isInverted = true;
				gamepad1.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
				frontLeft.setDirection(DcMotor.Direction.FORWARD);
				frontRight.setDirection(DcMotor.Direction.REVERSE);
				backLeft.setDirection(DcMotor.Direction.FORWARD);
				backRight.setDirection(DcMotor.Direction.REVERSE);
			}
			else {
				isInverted = false;
				gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
				frontLeft.setDirection(DcMotor.Direction.REVERSE);
				frontRight.setDirection(DcMotor.Direction.FORWARD);
				backLeft.setDirection(DcMotor.Direction.REVERSE);
				backRight.setDirection(DcMotor.Direction.FORWARD);
			}
			temp = frontLeft;
			frontLeft = backRight;
			backRight = temp;
			temp = frontRight;
			frontRight = backLeft;
			backLeft = temp;
		}
	}

	/*
	 * Code to run ONCE after the driver hits STOP
	 */
	@Override
	public void stop() {

	}
	
	
	private double clipExtra(double intx) {
		if (intx < -1) {
		  intx = -1;
		} else if (intx > 1) {
		  intx = 1;
		}
		return intx;
  }
}
