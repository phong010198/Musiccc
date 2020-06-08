package vn.ngphong.musiccc.models

import java.io.Serializable

data class Playlist(
    var name: String,
    var tracks: MutableList<Track>
) : Serializable