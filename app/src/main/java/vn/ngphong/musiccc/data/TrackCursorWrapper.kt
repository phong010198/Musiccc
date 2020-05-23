package vn.ngphong.musiccc.data

import android.database.Cursor
import android.database.CursorWrapper
import android.provider.MediaStore
import vn.ngphong.musiccc.models.Track


class TrackCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    fun getTrack(): Track {
        val id = getLong(getColumnIndex(MediaStore.Audio.Media._ID))
        val title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE))
        val artist = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST))
        val artistID = getLong(getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
        val album = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM))
        val albumID = getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
        val duration = getLong(getColumnIndex(MediaStore.Audio.Media.DURATION))
        val data = getString(getColumnIndex(MediaStore.Audio.Media.DATA))
        return Track(id, title, artist, artistID, album, albumID, "", duration, data)
    }
}