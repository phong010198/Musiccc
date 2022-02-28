package vn.ngphong.musiccc.data.models

import java.io.Serializable

data class Playlist(
    var name: String,
    var songs: MutableList<Song>
) : Serializable