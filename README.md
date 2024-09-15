### About

[![Maven](https://img.shields.io/maven-central/v/io.github.mflisar.kotpreferences/core?style=for-the-badge&color=blue)](https://central.sonatype.com/namespace/io.github.mflisar.kotpreferences)
[![API](https://img.shields.io/badge/api-21%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/github/languages/top/mflisar/kotpreferences.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/)
[![License](https://img.shields.io/github/license/MFlisar/KotPreferences?style=for-the-badge)](LICENSE)

With this library you can declare preferences via **kotlin delegates** and observe and update them via kotlin Flows. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

This is a KMP (kotlin multiplatform) library and the provided modules do support following platforms:

**Supported Platforms**

| Modules        | Android | iOS | jvm | Information |
|:---------------|---------|-----|-----|-------------|
| core           | √       | √   | √   |             |
| datastore      | √       | √   | √   |             |
| encryption-aes | √       |     |     | (1)         |
| compose        | √       | √   | √   |             |

* (1) Currently I only provide an encryption module for android. Pull requests with implementations for other platforms are welcome.

## :camera: Screenshots

| ![Demo](screenshots/demo.png "Demo") |
| :-: |

## :book: Documentation

The readme for this library with **code samples**, **screenshots** and more can be found on following *github page*:

[![Static Badge](https://img.shields.io/badge/Open%20Documentation-lightgreen?style=for-the-badge&logo=github&logoColor=black)](https://mflisar.github.io/github-docs/pages/libraries/kotpreferences/)

Additionally there is also a full working [demo app](demo) inside the *demo module*.