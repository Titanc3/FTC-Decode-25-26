package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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
@Autonomous

public class backshot extends OpMode {
	/* Declare OpMode members. */
	private ElapsedTime time;
	private DcMotor frontLeft;
	private DcMotor frontRight;
	private DcMotor backLeft;
	private DcMotor backRight;
	private DcMotor shooterL;
	private DcMotor shooterR;
	private DcMotor intake;

	@Override
	public void init() {
		telemetry.addData("Urgent", "\n\nAre you sure you want to use this Auto?\nThis auto can only get 3pts\nUse ONLY if all other autos fail.");
		frontLeft = hardwareMap.get(DcMotor.class, "front_left");
		frontRight = hardwareMap.get(DcMotor.class, "front_right");
		backLeft = hardwareMap.get(DcMotor.class, "back_left");
		backRight = hardwareMap.get(DcMotor.class, "back_right");
		shooterL = hardwareMap.get(DcMotor.class, "shooterL");
		shooterR = hardwareMap.get(DcMotor.class, "shooterR");
		intake = hardwareMap.get(DcMotor.class, "intake");
		time = new ElapsedTime();
		
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
	}

	/*
	 * Code to run ONCE when the driver hits PLAY
	 */
	@Override
	public void start() {
		shooterL.setPower(0.57);
		shooterR.setPower(-0.57);
		while (time.seconds() < 3) {}
		intake.setPower(0.5);
		time.reset();
		while (time.seconds() < 7) {}
		intake.setPower(0);
		shooterL.setPower(0);
		shooterR.setPower(0);
		frontLeft.setPower(-0.5);
		frontRight.setPower(-0.5);
		backLeft.setPower(-0.5);
		backRight.setPower(-0.5);
	}

	/*
	 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
	 */
	@Override
	public void loop() {
		while (time.seconds() < 7.5) {}
		frontLeft.setPower(0);
		frontRight.setPower(0);
		backLeft.setPower(0);
		backRight.setPower(0);
	}

	/*
	 * Code to run ONCE after the driver hits STOP
	 */
	@Override
	public void stop() {

	}
	

}
