package xyz.walk8243.androidmusicplayer.feature

import android.os.Environment
import java.io.File

class MusicFiles(baseDirPath: String?) {
    private val baseDir = if (baseDirPath != null) File(baseDirPath) else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)

    fun getFilesRecursively(dir: File?): ArrayList<File> {
        val returnFiles = arrayListOf<File>()
        val files = if (dir == null) baseDir.listFiles() else dir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isFile) {
                    returnFiles.add(file)
                } else if (file.isDirectory) {
                    returnFiles.addAll(getFilesRecursively(file))
                }
            }
        }
        return returnFiles
    }
}