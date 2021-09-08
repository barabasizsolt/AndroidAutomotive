# Android Automotive application based on *[android.car](https://developer.android.com/reference/android/car/Car)* library

The purpose of this application is to monitorize and visually represent the in-vehicle sensor changes.

## Prerequisites

* **[Latest version of Android Studio](https://developer.android.com/studio)**
* **[Polestar 2 Emulator](https://www.polestar.com/us/developer/get-started/)**
* **[Java JDK](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)**

## Note

    Currently the application is designed and built only for Polestar 2 Emulator.
    In case of any other AVD's the application won't work properly.
 
## Installation
* Clone the repository into your local machine
* Open the project using Android Studio
* Build the project, after the build is completed the application will be installed on your avd

      Before you build, make sure that the previously installed Polestar 2 AVD is selected
 
## Application details

The application is built around four main component.

* **Speedometer component**: 
It gives a secondary speedometer and also responsible for observing three in-vehicle sensor changes, which are the following:
  * Parking brake status (active/inactive)
  * Low fuel indicator
  * Current selected gear
  
* **Energy component**:
This component is responsible for every battery/energy based sensor and gives a graphical representation about their changes.
The above mentioned sensors are the following:
  * EV battery level (Wh) - the current charged status of the vehicle in percentage
  * Battery capacity (Wh) - the maximum battery capacity
  * Range remaining (m) - the maximum range that could be traveled with the current charged status
  * Odometer (km) - total traveled distance with the vehicle
  * EV instantaneous charge rate sensor (mW) - the current charging rate
  * Outside temperature (°C)
  
  If the vehicle is charging the Odometer sensor is being replaced with the EV instantaneous charge rate sensor
  
* **Vehicle info component**:
It shows every build information about the vehicle (ex: *build id*, *model*, *built year*) and also list every in-vehicle car sensor (ex: *Goldfish 3-axis Gyroscope*)

* **Benchmark component**:
This component basically is a mini CPU Benchmark application. 
It was developed to test the CPU performance in different scenarios (ex: during low battery usage, after software update) \
The benchmark has four main part:
    * Primality test on the first 20 million number
    * Calculate factorial of the first 20.000 number
    * Sorting a list with 400.000 element
    * Multiplying two 800x800 matrices
  

## Used libraries and technologies

* **[android.car library](https://developer.android.com/reference/android/car/Car)**
* **[ibrahimsn98 speedometer library](https://github.com/ibrahimsn98/speedometer)**
* **[androidx.room library](https://developer.android.com/jetpack/androidx/releases/room)**
* **[kotlin coroutines](https://kotlinlang.org/docs/coroutines-overview.html)**
* **[android material design](https://material.io/develop/android)**

The application is built around the **[MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)** design principle.
 
## Screenshots
<img src="https://serving.photos.photobox.com/8079941429052e91dcbf11d8cd3f4a25ff7b2ad569dd2b98dbae953af350bdc299c42cb8.jpg" width="510" height="540">


<img src="https://serving.photos.photobox.com/102845173a36e4673209a6d3ce89711ac2eab38cc1fc161c641f1741afceddd46b7638de.jpg" width="510" height="540">


<img src="https://serving.photos.photobox.com/517087026c0168443d27f73dbf7213f5c8d796ca220db4a5e2d4603f34caeb331b3b5777.jpg" width="510" height="540">


<img src="https://serving.photos.photobox.com/231777043613d3cae6714989220137c87f07f3f4ddcac5ffb2cc263a8e69fba3c2ecd2f6.jpg" width="510" height="540">
