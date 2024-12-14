/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mankevich.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import pl.mankevich.designsystem.R

object RnmIcons {
    val Alien: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Alien)

    val ArrowCircleUp: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.ArrowCircleUp)

    val Blocks: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Blocks)

    val CalendarBlank: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.CalendarBlank)

    val CaretLeft: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.CaretLeft)

    val CaretRight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.CaretRight)

    val CubeFocus: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.CubeFocus)

    val GenderFemale: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.GenderFemale)

    val GenderIntersex: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.GenderIntersex)

    val GenderMale: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.GenderMale)

    val Genderless: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Genderless)

    val HeartFilled: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.HeartFilled)

    val HeartOutlined: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.HeartOutlined)

    val Home: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Home)

    val HouseSimple: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.HouseSimple)

    val Info: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Info)

    val MapPin: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.MapPin)

    val MonitorPlay: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.MonitorPlay)

    val Person: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Person)

    val Planet: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Planet)

    val Pulse: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Pulse)

    val Question: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Question)

    val Queue: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Queue)

    val Robot: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Robot)

    val Skull: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.Skull)

    val XCircle: ImageVector
        @Composable
        get() = ImageVector.vectorResource(RnmIconsRes.XCircle)

}

object RnmIconsRes {
    val Alien = R.drawable.ic_alien
    val ArrowCircleUp = R.drawable.ic_arrow_circle_up
    val Blocks = R.drawable.ic_blocks
    val CalendarBlank = R.drawable.ic_calendar_blank
    val CaretLeft = R.drawable.ic_caret_left
    val CaretRight = R.drawable.ic_caret_right
    val CubeFocus = R.drawable.ic_cube_focus
    val GenderFemale = R.drawable.ic_gender_female
    val GenderIntersex = R.drawable.ic_gender_intersex
    val GenderMale = R.drawable.ic_gender_male
    val Genderless = R.drawable.ic_genderless
    val HeartFilled = R.drawable.ic_heart_filled
    val HeartOutlined = R.drawable.ic_heart_outlined
    val Home = R.drawable.ic_home
    val HouseSimple = R.drawable.ic_house_simple
    val Info = R.drawable.ic_info
    val MapPin = R.drawable.ic_map_pin
    val MonitorPlay = R.drawable.ic_monitor_play
    val Person = R.drawable.ic_person
    val Planet = R.drawable.ic_planet
    val Pulse = R.drawable.ic_pulse
    val Question = R.drawable.ic_question
    val Queue = R.drawable.ic_queue
    val Robot = R.drawable.ic_robot
    val Skull = R.drawable.ic_skull
    val XCircle = R.drawable.ic_x_circle
}
