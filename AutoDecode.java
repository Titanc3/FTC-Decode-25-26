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
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name = "Decode AutoFire")

public class AutoDecode extends OpMode {
	/* Declare OpMode members. */
	private VoltageSensor hubVoltSens;
	private DcMotorEx frontLeft;
	private DcMotorEx frontRight;
	private DcMotorEx backLeft;
	private DcMotorEx backRight;
	private DcMotor shooterL;
	private DcMotor shooterR;
	private DcMotor intake;
	private ServoController ControlHub_ServoController;
	double voltage;
	double vMult;
	
	@Override
	public void init() {
		telemetry.addData("Status", "Initialized");
		frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
		frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
		backLeft = hardwareMap.get(DcMotorEx.class, "back_left");
		backRight = hardwareMap.get(DcMotorEx.class, "back_right");
		shooterL = hardwareMap.get(DcMotor.class, "shooterL");
		shooterR = hardwareMap.get(DcMotor.class, "shooterR");
		intake = hardwareMap.get(DcMotor.class, "intake");
		hubVoltSens = hardwareMap.get(VoltageSensor.class, "Control Hub");
		
		frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //reset all encoders
		frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		frontLeft.setDirection(DcMotor.Direction.REVERSE); // set motor directions
		frontRight.setDirection(DcMotor.Direction.FORWARD);
		backLeft.setDirection(DcMotor.Direction.REVERSE);
		backRight.setDirection(DcMotor.Direction.FORWARD);
		
		
		
		
		
	}


	@Override
	public void init_loop() {
	}


	@Override
	public void start() {
		frontLeft.setTargetPosition(300);
		frontRight.setTargetPosition(300);
		backLeft.setTargetPosition(300);
		backRight.setTargetPosition(300);
		frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	
		frontLeft.setPower(0.5);// actually start movement
		frontRight.setPower(0.5);
		backLeft.setPower(0.5);
		backRight.setPower(0.5);
	}


	@Override
	public void loop() {
		double position = backLeft.getCurrentPosition();
		double position2 = backRight.getCurrentPosition();
		voltage = hubVoltSens.getVoltage();
		vMult = 12/voltage;
		telemetry.addData("Bl", position);
		telemetry.addData("Br", position2);


	}


	@Override
	public void stop() {

	}
}
