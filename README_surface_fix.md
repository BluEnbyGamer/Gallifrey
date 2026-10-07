# Vortex Manipulator Surface Teleport Fix

Surface mode now:
- loads the destination chunk before reading its heightmap;
- uses the motion-blocking surface height at the requested X/Z;
- only allows a destination strictly above Y=64;
- refuses the teleport with a message if no valid surface exists above Y=64.

Normal coordinate teleports are unchanged.

A full Gradle build was not run because this environment cannot download the project's Gradle distribution/dependencies.
