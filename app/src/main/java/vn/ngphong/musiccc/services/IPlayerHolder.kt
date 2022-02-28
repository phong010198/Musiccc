package vn.ngphong.musiccc.services

import vn.ngphong.musiccc.data.models.Song
import vn.ngphong.musiccc.util.PlaybackListener

interface IPlayerHolder {
    fun isPlayerExist(): Boolean
    fun isPlaying(): Boolean
    fun toForeground()

    fun initPlayer()
    fun updateTracks(songs: MutableList<Song>, currentTrackPos: Int)
    fun setCurrentTrackByPos(position: Int)
    fun setRandomTrackPos()
    fun getCurrentTrack(): Song?
    fun getResumePosition(): Int
    fun getCurrentPosition(): Int?

    fun play()
    fun pause()
    fun resume()
    fun resumePause()
    fun seek(position: Int)
    fun playPrev()
    fun playNext()

    fun changeShuffleState()
    fun getShuffleState(): Boolean
    fun changeRepeatState()
    fun getRepeatState(): Int

    fun setPlaybackListener(playbackListener: PlaybackListener)

    @PlaybackListener.State
    fun getState(): Int
    fun stop()
}