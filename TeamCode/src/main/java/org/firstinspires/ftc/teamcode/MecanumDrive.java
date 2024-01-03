package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class MecanumDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Servo claw = hardwareMap.get(Servo.class, "claw");
        Servo rotate = hardwareMap.get(Servo.class, "rotate");
        DcMotor rslide = hardwareMap.get(DcMotor.class, "RightSlide");
        DcMotor lslide = hardwareMap.get(DcMotor.class, "LeftSlide");

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            drive.getRawExternalHeading();
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * 0.5,
                            -gamepad1.left_stick_x * 0.5,
                            -gamepad1.right_stick_x * 0.5
                    )
            );

            drive.update();

            if (gamepad2.a) {
                claw.setPosition(0.2);
            }
            if (gamepad2.b) {
                claw.setPosition(-0.3);
            }
            if (gamepad2.x) {
                rotate.setPosition(0.6);
            }
            if (gamepad2.y) {
                rotate.setPosition(0.4);
            }
            if (gamepad2.dpad_down) {
                rslide.setPower(1);
                lslide.setPower(-1);
            } else if (gamepad2.dpad_up) {
                rslide.setPower(-1);
                lslide.setPower(1);
            } else {
                rslide.setPower(0);
                lslide.setPower(0);
            }


            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

        }
    }
}
