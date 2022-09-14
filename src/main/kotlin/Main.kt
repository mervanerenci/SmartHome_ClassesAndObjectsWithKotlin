import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// SMART DEVICE SUPER CLASS

open class SmartDevice(val name : String, val category: String) {
    var deviceStatus = "online"
        protected set
    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus ="on"
    }
    open fun turnOff() {
        deviceStatus ="off"
    }
    open fun printDeviceInfo() {
        println("Device name: $name, Category: $category, Type: $deviceType")
    }

    // SMART TV DEVICE

    class SmartTvDevice(deviceName: String, deviceCategory: String) :
        SmartDevice(name = deviceName, category= deviceCategory) {
        override val deviceType ="Smart TV"

        private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
        private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

        override fun printDeviceInfo() {
            super.printDeviceInfo()
        }

        override fun turnOn() {

            super.turnOn()
            println(
                "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                        "set to $channelNumber."
            )
        }

        override fun turnOff() {
            super.turnOff()
            println("$name turned off")

        }

        fun increaseSpeakerVolume() {
            speakerVolume++
            println("Speaker volume increased to $speakerVolume .")
        }

        fun decreaseSpeakerVolume() {
            speakerVolume--
            println("Speaker volume decreased to $speakerVolume .")
        }

        fun nextChannel() {
            channelNumber++
            println("Channel number increased to $channelNumber .")
        }

        fun previousChannel() {
            channelNumber--
            println("Channel number is decreased to $channelNumber")
        }



    }

    // SMART LIGHT DEVICE

    class SmartLightDevice(deviceName: String, deviceCategory: String) :
        SmartDevice(name = deviceName, category= deviceCategory) {
        override val deviceType = "Smart Light"

        private var brightnessLevel by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)


        override fun printDeviceInfo() {
            super.printDeviceInfo()
        }

        override fun turnOn() {
            super.turnOn()
            brightnessLevel = 2
            println("$name turned on. The brightness level is $brightnessLevel.")
        }

        override fun turnOff() {
            super.turnOff()
            brightnessLevel = 0
            println("Smart Light turned off")
        }

        fun increaseBrightness() {
            brightnessLevel++
            println("Brightness level increased to $brightnessLevel")
        }

        fun decreaseBrightness() {
            brightnessLevel--
            println("Brightness level decreased to $brightnessLevel")
        }
    }



}

//smarthome HAS-A smartdevice

class SmartHome(
    val smartTvDevice: SmartDevice.SmartTvDevice,
    val smartLightDevice: SmartDevice.SmartLightDevice
                ) {

    var deviceTurnOnCount = 0
        private set


    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }
    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }
    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }
    fun decreaseTvVolume() {
        smartTvDevice.decreaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    fun changeTvChannelToPrevious() {
        smartTvDevice.previousChannel()
    }
    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }
    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
    }
    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }
    fun decreaseLightBrightness() {
        smartLightDevice.decreaseBrightness()
    }
    fun printSmartTvInfo() {
        smartTvDevice.printDeviceInfo()
    }
    fun printSmartLightInfo() {
        smartLightDevice.printDeviceInfo()
    }
    fun turnOffAll() {
        turnOffTv()
        turnOffLight()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    private var fieldData = initialValue
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }


}

fun main() {
    val smartHome = SmartHome(
        SmartDevice.SmartTvDevice(deviceName = "Android Tv", deviceCategory = "Entertainment"),
        SmartDevice.SmartLightDevice(deviceName = "Google Light", deviceCategory = "Utility")
    )

    smartHome.turnOnTv()
    smartHome.printSmartTvInfo()
    println()
    smartHome.turnOnLight()
    smartHome.printSmartLightInfo()
    println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")
    println()


    smartHome.increaseTvVolume()
    smartHome.changeTvChannelToNext()
    smartHome.decreaseTvVolume()
    smartHome.changeTvChannelToPrevious()
    println()


    smartHome.increaseLightBrightness()
    smartHome.decreaseLightBrightness()
    println()

    smartHome.turnOffAll()
    println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")



}