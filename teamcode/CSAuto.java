/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "CSAuto", group = "Autonomous")

public class CSAuto extends LinearOpMode implements MecanumDrive.TickCallback {


    private final MecanumDrive mecanumDrive = new MecanumDrive();


    private final CSRobot robot = new CSRobot();
    private int sleeveCode;

    private enum ScoringDirection {SCORE_LEFT, SCORE_RIGHT}
    private ScoringDirection scoringDirection = ScoringDirection.SCORE_LEFT;
    private int path = 0;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry, this);
        mecanumDrive.init(hardwareMap, telemetry, this);
        mecanumDrive.setCountPerDegree(7);

        robot.setDrivetrainMotorDirections(mecanumDrive);
        mecanumDrive.setupTickCallback(this);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

        waitForStart();
        mecanumDrive.forward(26, 0.5);
        if (path == 1) {
            mecanumDrive.leftTurn(90, 0.5);
            mecanumDrive.forward(24, 0.5);
        } else if (path == 3) {
            mecanumDrive.rightTurn(90, 0.5);
            mecanumDrive.forward(24, 0.5);
        }

        while (opModeIsActive()) {
            standardMecanumControls();
        }
        mecanumDrive.tickSleep();
    }

    public void standardMecanumControls() {
        double speed = (gamepad1.right_trigger * 0.5) + 0.5;
        double fwd = addDeadZone(gamepad1.left_stick_y) * speed;
        double rot = addDeadZone(gamepad1.right_stick_x) * speed;
        double strafe = addDeadZone(gamepad1.left_stick_x);
        strafe = strafe * speed * 1.6;

        if (strafe > 1) {
            strafe = 1;
        } else if (strafe < -1) {
            strafe = -1;
        }
        mecanumDrive.setMotors(strafe, fwd, rot, 1);
    }

    public void tickCallback() {
    }

    double addDeadZone(double input) {
        if (Math.abs(input) < 0.1) {
            return (0.0);
        }
        return (input);
    }

}
