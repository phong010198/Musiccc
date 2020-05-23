package vn.ngphong.musiccc.models

import java.io.Serializable

data class Artist(
    var id: Long,
    var name: String,
    var tracks: MutableList<Track>,
    var expand: Boolean = false
) : Serializable