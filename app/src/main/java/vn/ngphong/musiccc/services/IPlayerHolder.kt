package vn.ngphong.musiccc.services

import vn.ngphong.musiccc.models.Track
import vn.ngphong.musiccc.utils.PlaybackListener

interface IPlayerHolder {
    fun isPlayerExist(): Boolean
    fun isPlaying(): Boolean
    fun toForeground()

    fun initPlayer()
    fun updateTracks(tracks: MutableList<Track>, currentTrackPos: Int)
    fun setCurrentTrackByPos(position: Int)
    fun getCurrentTrack(): Track?
    fun getRandomTrackPosition(): Int
    fun getResumePosition(): Int
    fun getCurrentPosition(): Int

    fun play()
    fun pause()
    fun resume()
    fun resumePause()
    fun seek(position: Int)
    fun playPrev()
    fun playNext()

    fun setPlaybackListener(playbackListener: PlaybackListener)

    @PlaybackListener.State
    fun getState(): Int
    fun stop()
}