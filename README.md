# Android Notes App

This repository contains an Android notes app built using Clean Architecture principles and Jetpack Compose UI toolkit. The app allows users to create, read, update, and delete notes, as well as sort them by name, date, or color in both ascending and descending order. Additionally, the app includes home screen widgets powered by the Glance API to provide users with quick access to their notes.

## Features

- **Clean Architecture:** The app is structured using the Clean Architecture principles to achieve separation of concerns and maintainability.
- **Jetpack Compose:** The user interface is developed using Jetpack Compose, providing a modern and declarative way of building UIs.
- **CRUD Functionality:** Users can Create, Read, Update, and Delete notes within the app.
- **Sorting:** Notes can be sorted by name, date, or color in ascending or descending order.
- **Home Screen Widgets:** Home screen widgets are implemented using the Glance API to display users' notes on their home screen.

## Screenshots

![App Screenshots](screenshots.png)

## Getting Started

1. Clone the repository: `git clone https://github.com/mohammed-samy-1/my-notes`
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Home Screen Widgets

To add a home screen widget:

1. Long-press on an empty area of your device's home screen.
2. Select "Widgets" from the menu.
3. Find the "Notes Widget" and drag it to your home screen.

Explain how to add and customize home screen widgets using the Glance API.

## Testing

### Unit Testing

Unit tests ensure that individual components of your app work correctly in isolation. They cover the smallest testable parts of your code, usually functions and classes.

To run unit tests, execute the following command:
`./gradlew test`

### Integration Testing

Integration tests focus on testing the interactions between different parts of your app. They ensure that the various components work together as expected.

To run integration tests, execute the following command:
`./gradlew connectedAndroidTest` or `./gradlew cAT`


### End-to-End Testing

End-to-end (E2E) tests simulate real user scenarios and interactions with your app. They test the app as a whole and help identify issues that might arise from the integration of different modules.

To run E2E tests, make sure you have a connected device or emulator, then execute:
`./gradlew connectedAndroidTest` or `./gradlew cAT`

## Contributing

Contributions are welcome! To contribute to this project:

1. Fork the repository.
2. Create a new branch for your feature: `git checkout -b feature-name`
3. Make your changes and commit them.
4. Push your changes to your fork: `git push origin feature-name`
5. Create a pull request on the main repository.

## License

This project is licensed under the [MIT License](https://github.com/mohammed-samy-1/my-notes/blob/master/LICENSE.txt).


