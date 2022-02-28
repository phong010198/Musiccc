package vn.ngphong.musiccc.data.models

import java.io.Serializable

data class Album(
    var id: Long,
    var name: String,
    var songs: MutableList<Song>
) : Serializable