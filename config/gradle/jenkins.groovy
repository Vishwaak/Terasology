// This is a little convenience script meant to be run during the build startup phase for module builds in Jenkins
// First prepares Gradle for later execution by providing accurate naming and release info

// Find the true name of the project - probably available within Jenkins but this way can test easier outside
def settingsGradleFile = new File('settings.gradle')
def gradlePropertiesFile = new File('gradle.properties')
def moduleName = settingsGradleFile.getAbsoluteFile().parentFile.name - "Nano"
println "Preparing settings.gradle for module '$moduleName'"

// Make sure no existing files exists, no sneaking in stealthy Gradle stuff (would have to be in Git, Jenkins cleans thoroughly)
println "Deleting any existing settings.gradle or gradle.properties - build.gradle has already been overwritten"
settingsGradleFile.delete()
gradlePropertiesFile.delete()

def settingsGradleContent = """
// This allows us to have jobs in Jenkins with names that differ from their module name (such as jobs for "develop" and "master")
// It also avoids an issue where "workspace" became part of the Artifactory metadata after a path change in Jenkins
rootProject.name = '$moduleName'

// Experimental flag to feed Gradle in a release build job situation. Could be elsewhere in Jenkins but this is nice and easy
def isReleaseBuild = true
"""

settingsGradleFile << settingsGradleContent
