# Spot the Nature App
**by Liisa Törmäkangas**

## About
This repository includes an Android mobile application that can be used to save nature observations (e.g. birds, plants, or other observation types) combined with the location of the observationd and a photo.

The project work is a part of OAMK/Information Technology course "Mobile Programming with Native Technologies".

**Please Note!** Commit 8542a47 on March 17th includes a functional basic application that collects, handles and stores observation data, collects location from the phone, and lists observations based on selected criteria. However, there are still several intended functionalities missing and these will be implemented as soon as possible. The ability to use phone camera and save images will be added, the drawer menu components will be finalized, and finally, a map that shows the observation locations is to be added.

---
## Description of the technologies used in the project
- The minimum SDK version to run the application is API 25 (Android 7.1.1).  
- The project is written with Kotlin language and uses Jetpack compose toolkit to create the UI. 
- androidx.lifecycle:lifecycle-viewmodel from Android Jetpack library is used to store the states of observation and location data.
- Application is using a Room persistence library v.2.6.1 and kapt compiler plugin to save the observation data.
- MVVM architecture is used to handle the observation details and location data between different components.
- Maxkeppeler/sheets-compose-dialogs/calendar library is used for the date picker.

---
## To get the application...
Clone the repository to your local and open it with Android Studio. Run the application either in emulator or use the "Pair Devices Using Wi-Fi" option to connect your computer and Android phone to run the app in the phone.
