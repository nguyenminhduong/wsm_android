NOTE_FILE_NAME=release_notes.txt
rm $NOTE_FILE_NAME
./gradlew :app:assembleDebug crashlyticsUploadDistributionDebug -PdisablePredex
./gradlew :app:assembleRelease crashlyticsUploadDistributionRelease
