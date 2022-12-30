package com.roy93group.launcher.storage

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.*

object ExternalStorage {

    inline fun write(context: Context, name: String, fn: (OutputStream, String) -> Unit) {
        val dir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val file = File(dir, name)
        FileOutputStream(file).use {
            fn(it, file.absolutePath)
        }
    }

    inline fun writeOutsideScope(
        context: Context,
        name: String,
        fn: (OutputStream, String) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, name)
                put(MediaStore.Downloads.RELATIVE_PATH, "Download")
                put(MediaStore.Downloads.IS_PENDING, 1)
            }

            val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val uri = context.contentResolver.insert(collection, values)

            uri?.let { u ->
                context.contentResolver.openOutputStream(u).use { os ->
                    os?.let {
                        fn(it, "Download/$name")
                    }
                }

                values.clear()
                values.put(MediaStore.Downloads.IS_PENDING, 0)
                context.contentResolver.update(
                    /* uri = */ u,
                    /* values = */values,
                    /* where = */null,
                    /* selectionArgs = */null
                )
            }
        } else write(context = context, name = name, fn = fn)
    }

    inline fun writeDataOutsideScope(
        context: Context,
        name: String,
        feedbackPopup: Boolean,
        block: (ObjectOutputStream) -> Unit
    ) {
        writeOutsideScope(context, name) { o, path ->
            val out = ObjectOutputStream(o)
            try {
                block(out)
                if (feedbackPopup) Toast.makeText(
                    /* context = */ context,
                    /* text = */"Saved: $path",
                    /* duration = */Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                if (feedbackPopup) Toast.makeText(
                    /* context = */ context,
                    /* text = */ "Error: ${e.localizedMessage}",
                    /* duration = */ Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    inline fun <T> read(context: Context, uri: Uri, block: (ObjectInputStream) -> T): T? {
        return try {
            ObjectInputStream(context.contentResolver.openInputStream(uri)).use(block)
        } catch (e: IOException) {
            null
        }
    }

//    private const val FILE_REQUEST_CODE = 0xf113

//    fun pickFile(activity: Activity, mime: String) {
//        val intent = Intent()
//        intent.action = Intent.ACTION_OPEN_DOCUMENT
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        intent.type = mime
//        activity.startActivityForResult(intent, FILE_REQUEST_CODE)
//    }

//    fun onActivityResultPickFile(
//        activity: Activity,
//        requestCode: Int,
//        data: Intent?,
//        fn: (InputStream?) -> Unit
//    ) {
//        if (requestCode == FILE_REQUEST_CODE) {
//            fn(
//                try {
//                    data?.data?.let { uri ->
//                        activity.contentResolver.openInputStream(uri)
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    null
//                }
//            )
//        }
//    }
}
