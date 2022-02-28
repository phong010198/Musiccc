package vn.ngphong.musiccc.data.local

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.CursorWrapper
import android.provider.MediaStore
import vn.ngphong.musiccc.data.models.Song

class SongCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    @SuppressLint("InlinedApi")
    fun getSong(): Song {
        val id = getLong(getColumnIndex(MediaStore.Audio.Media._ID))
        val title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE))
        val artist = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST))
        val artistID = getLong(getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
        val album = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM))
        val albumID = getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
        val duration = getLong(getColumnIndex(MediaStore.Audio.Media.DURATION))
        @Suppress("DEPRECATION") val data = getString(getColumnIndex(MediaStore.Audio.Media.DATA))
        return Song(id, title, artist, artistID, album, albumID, "", duration, data)
    }
}