package vn.ngphong.musiccc.ui

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.ngphong.musiccc.base.BaseViewModel
import vn.ngphong.musiccc.data.local.DataLoader
import vn.ngphong.musiccc.data.models.Album
import vn.ngphong.musiccc.data.models.Artist
import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.util.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper

    var currentSong = MutableLiveData<Song>()
    var currentSongList = MutableLiveData<MutableList<Song>>()
    var currentSongPos = MutableLiveData<Int>()
    var currentState = MutableLiveData<Int>()
    var allSongs = MutableLiveData<MutableList<Song>>()
    var allArtists = MutableLiveData<MutableList<Artist>>()
    var allAlbums = MutableLiveData<MutableList<Album>>()

    fun loadAllSongs(context: Context) {
        allSongs.value = DataLoader(context).querySongs()
    }

    fun getArtists() {
        val artists = mutableListOf<Artist>()
        if (!allSongs.value.isNullOrEmpty()) {
            for (song in allSongs.value!!) {
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
        allArtists.value = artists
    }

    fun getAlbums() {
        val albums = mutableListOf<Album>()
        if (!allSongs.value.isNullOrEmpty()) {
            for (song in allSongs.value!!) {
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
        allAlbums.value = albums
    }
}