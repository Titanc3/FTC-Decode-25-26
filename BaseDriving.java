package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "BaseDriving (Starting Point)")  //mecanum bot config
public class BaseDriving extends LinearOpMode {

	// this code specifically sets up driving for the base, nothing else
	// build off of this code every season for quick programming
	// motorNum-motorPos 0-frontLeft 1-frontRight 2-backLeft 3-backRight
	//Unofficial Copyright Mark Chalamish circa Dec 2025

  private DcMotor front_left;
  private DcMotor front_right;
  private DcMotor back_left;
  private DcMotor back_right;
  private ServoController ControlHub_ServoController;

  double mvSpeed;
  ElapsedTime myElapsedTime;
  double fl_br_pwr;
  double fr_bl_pwr;

  /**
   * Do the entire thingamajig
   */
  @Override
  public void runOpMode() {
	double LeftX;
	double LeftY;
	double RightX;
	double RightY;

	front_left = hardwareMap.get(DcMotor.class, "front_left");
	front_right = hardwareMap.get(DcMotor.class, "front_right");
	back_left = hardwareMap.get(DcMotor.class, "back_left");
	back_right = hardwareMap.get(DcMotor.class, "back_right");
	ControlHub_ServoController = hardwareMap.get(ServoController.class, "Control Hub");

	setup();
	waitForStart();
	if (opModeIsActive()) {
	  myElapsedTime.reset();
	  telemetry.update();
	  SetCtrl1Speed(0.7);
	  gamepad1.rumble(500);
	  while (opModeIsActive()) {
		telemetry.update();
		LeftX = clipExtra(gamepad1.left_stick_x * 1.25);
		LeftY = clipExtra(-gamepad1.left_stick_y * 1.25);
		RightX = clipExtra(gamepad1.right_stick_x * 1.25);
		RightY = clipExtra(-gamepad1.right_stick_y * 1.25);
		// Fix y inversion weirdness & ensure max speed at joystick edges
		fl_br_pwr = clipExtra(LeftY + LeftX);
		fr_bl_pwr = clipExtra(LeftY - LeftX);
		if (gamepad1.left_bumper) {
		  if (gamepad1.left_bumper) {
		  }
		}
		if (gamepad1.right_bumper) {
		}
		if (gamepad1.dpad_left) {
		  SetCtrl1Speed(0.4);
		}
		if (gamepad1.dpad_up) {
		  SetCtrl1Speed(0.7);
		}
		if (gamepad1.dpad_right) {
		  SetCtrl1Speed(1);
		}
		if (RightX != 0) {
		  turn(RightX);
		  telemetry.addData("Movement", "Rotation");
		} else {
		  front_left.setPower(fl_br_pwr * mvSpeed);
		  front_right.setPower(fr_bl_pwr * mvSpeed);
		  back_left.setPower(fr_bl_pwr * mvSpeed);
		  back_right.setPower(fl_br_pwr * mvSpeed);
		  telemetry.addData("Movement", "Translation");
		}
		telemetry.addData("Speed", mvSpeed * 100 + "%");
		telemetrySpace(2);
		telemetry.addLine("Insert ctrl2 data here");
	  }
	}
  }

  /**
   * Describe this function...
   */
  private void setup() {
	ControlHub_ServoController.pwmEnable();
	gamepad1.rumble(1000);
	SetCtrl1Speed(0.7);
	// Possible Available Vals:  0.4, 0.7, 1
	front_left.setDirection(DcMotor.Direction.REVERSE);
	front_right.setDirection(DcMotor.Direction.FORWARD);
	back_left.setDirection(DcMotor.Direction.REVERSE);
	back_right.setDirection(DcMotor.Direction.FORWARD);
	myElapsedTime = new ElapsedTime();
  }

  /**
   * Clip ints out of range -1, 1 inclusive
   */
  private double clipExtra(double int2) {
	if (int2 < -1) {
	  int2 = -1;
	} else if (int2 > 1) {
	  int2 = 1;
	}
	return int2;
  }

  /**
   * create X amounts of spaces (\ns)
   */
  private void telemetrySpace(int x) {
	for (int count = 0; count < x; count++) {
	  telemetry.addLine("");
	}
  }

  /**
   * Set speed & color on controller1
   */
  private void SetCtrl1Speed(double newMvSpeed) {
	if (newMvSpeed == 0.4) {
	  gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
	} else if (newMvSpeed == 0.7) {
	  gamepad1.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
	} else if (newMvSpeed == 1) {
	  gamepad1.setLedColor(255, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
	} else {
	  gamepad1.setLedColor(0, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
	}
	mvSpeed = newMvSpeed;
  }

  /**
   * turn the robot,
   * -1 = full speed left,
   *  1 = full speed right
   */
  private void turn(double turnSpeed) {
	front_left.setPower(clipExtra(fl_br_pwr + turnSpeed) * mvSpeed);
	back_left.setPower(clipExtra(fl_br_pwr + turnSpeed) * mvSpeed);
	front_right.setPower(clipExtra(fr_bl_pwr - turnSpeed) * mvSpeed);
	back_right.setPower(clipExtra(fr_bl_pwr - turnSpeed) * mvSpeed);
  }

  /**
   * set zero power behavior for all motors, false = float, true = brake
   */
  private void setMotorZPBBrake(boolean bool) {
	if (bool) {
	  front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	  front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	  back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	  back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	} else {
	  front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
	  front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
	  back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
	  back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
	}
  }
}
