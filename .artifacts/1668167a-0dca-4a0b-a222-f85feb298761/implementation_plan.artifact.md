# Upgrade Target SDK and Align with Android 15 Requirements

This plan upgrades the project to fully support Android 15 (API level 35) and addresses the "recent communication" from Google Play Console by ensuring all activities handle edge-to-edge correctly and dependencies are up to date.

## User Review Required

> [!IMPORTANT]
> - Upgrading to Android 15 (API 35) enforces edge-to-edge behavior. I will add `enableEdgeToEdge()` to `CategoryActivity.kt` and ensure insets are handled in `ComposeScreens.kt`.
> - Dependency updates might require minor code adjustments if there are breaking changes in newer versions of Compose or Lifecycle libraries.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/amris/AndroidStudioProjects/FruitList/gradle/libs.versions.toml)
- Update `agp`, `kotlin`, `composeBom`, `lifecycleRuntimeKtx`, and `activity` to more recent stable versions.

#### [MODIFY] [build.gradle](file:///C:/Users/amris/AndroidStudioProjects/FruitList/app/build.gradle)
- Verify `compileSdk` and `targetSdk` are at 35 (which they are, but ensuring consistency).
- Ensure `jvmTarget` is consistent.

### UI & Activities

#### [MODIFY] [CategoryActivity.kt](file:///C:/Users/amris/AndroidStudioProjects/FruitList/app/src/main/java/com/cb/fruitlist/CategoryActivity.kt)
- Add `enableEdgeToEdge()` to `onCreate`.

#### [MODIFY] [ComposeScreens.kt](file:///C:/Users/amris/AndroidStudioProjects/FruitList/app/src/main/java/com/cb/fruitlist/ui/ComposeScreens.kt)
- Ensure all screens use `WindowInsets.systemBars.asPaddingValues()` or similar to respect the status and navigation bars.
- Fix any potential issues with `TextToSpeech` initialization (using `rememberUpdatedState` or similar to avoid leaks/stale context).

### Manifest

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/amris/AndroidStudioProjects/FruitList/app/src/main/AndroidManifest.xml)
- Add `android:dataExtractionRules` and `android:fullBackupContent` to satisfy Lint warnings related to Android 12+ backup changes.

## Verification Plan

### Automated Tests
- Run `gradlew app:assembleDebug` to ensure the project still builds.
- Run `gradlew lintDebug` to check if the SDK-related warnings are resolved.

### Manual Verification
- Deploy to an Android 15 emulator/device to verify edge-to-edge behavior.
