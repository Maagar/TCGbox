package util

import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

fun openURL(url:String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: IOException) {
            println("Failed to open URL: $url with IO error: ${e.message}")
            e.printStackTrace()
        } catch (e: URISyntaxException) {
            println("Failed to open URL: $url with malformed URL: ${e.message}")
            e.printStackTrace()
        } catch (e: SecurityException) {
            println("Failed to open URL: $url with security error: ${e.message}")
            e.printStackTrace()
        }
    } else {
        println("Desktop Browse is not supported on this platform or action is not supported.")
    }
}