package vn.ngphong.musiccc.data.models

import java.io.Serializable

data class Artist(
    var id: Long,
    var name: String,
    var songs: MutableList<Song>,
    var expand: Boolean = false
) : Serializable