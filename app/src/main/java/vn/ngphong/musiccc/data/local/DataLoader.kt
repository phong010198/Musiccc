package vn.ngphong.musiccc.data.local

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import vn.ngphong.musiccc.data.models.Album
import vn.ngphong.musiccc.data.models.Artist
import vn.ngphong.musiccc.data.models.Playlist
import vn.ngphong.musiccc.data.models.Song

class DataLoader(val context: Context) {
    @SuppressLint("Recycle")
    private fun querySong(): SongCursorWrapper {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ARTIST,
            MediaStore.Audio.AudioColumns.ARTIST_ID,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.DATA
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.AudioColumns.TITLE} COLLATE LOCALIZED ASC"
        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)
        return SongCursorWrapper(cursor!!)
    }

    fun querySongs(): MutableList<Song> {
        val songs = mutableListOf<Song>()
        val cursor: SongCursorWrapper = querySong()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val song = cursor.getSong()
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            song.albumArt = ContentUris.withAppendedId(sArtworkUri, song.albumID).toString()
            songs.add(song)
            cursor.moveToNext()
        }
        cursor.close()
        return songs
    }

    fun getArtists(): MutableList<Artist> {
        val artists = mutableListOf<Artist>()
        val songs = querySongs()
        if (songs.isNotEmpty()) {
            for (song in songs) {
                var exist = false
                for (artist in artists) {
                    if (artist.songs.isNotEmpty() && artist.songs[0].artistID == song.artistID) {
                        artist.songs.add(song)
                        exist = true
                        break
                    }
                }
                if (exist) {
                    continue
                } else {
                    val artist = Artist(song.artistID, song.artist, mutableListOf(song))
                    artists.add(artist)
                }
            }
        }
        if (artists.size > 1) {
            artists.sortWith { obj1, obj2 ->
                obj1.name.compareTo(
                    obj2.name,
                    ignoreCase = true
                )
            }
        }
        return artists
    }

    fun getAlbums(): MutableList<Album> {
        val albums = mutableListOf<Album>()
        val songs = querySongs()
        if (songs.isNotEmpty()) {
            for (song in songs) {
                var exist = false
                for (album in albums) {
                    if (album.songs.isNotEmpty() && album.songs[0].albumID == song.albumID) {
                        album.songs.add(song)
                        exist = true
                        break
                    }
                }
                if (exist) {
                    continue
                } else {
                    val album = Album(song.albumID, song.album, mutableListOf(song))
                    albums.add(album)
                }
            }
        }
        if (albums.size > 1) {
            albums.sortWith { obj1, obj2 ->
                obj1.name.compareTo(
                    obj2.name,
                    ignoreCase = true
                )
            }
        }
        return albums
    }

    fun genPlaylist(): MutableList<Playlist> {
        val playlist = mutableListOf<Playlist>()
        val tracks = querySongs()
        val playlistTracks = mutableListOf<Song>()
        playlistTracks.add(tracks[1])
        playlistTracks.add(tracks[2])
        playlistTracks.add(tracks[5])
        playlistTracks.add(tracks[6])
        playlistTracks.add(tracks[7])
        val playlist0 = Playlist("Create a new playlist", mutableListOf())
        val playlist1 = Playlist("Playlist A", playlistTracks)
        playlist.add(playlist0)
        playlist.add(playlist1)
        return playlist
    }
}