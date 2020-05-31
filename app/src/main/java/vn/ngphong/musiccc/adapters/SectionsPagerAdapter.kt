package vn.ngphong.musiccc.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import vn.ngphong.musiccc.views.fragments.AlbumsFragment
import vn.ngphong.musiccc.views.fragments.ArtistsFragment
import vn.ngphong.musiccc.views.fragments.TracksFragment

@Suppress("DEPRECATION")
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    private val tracksFrag = TracksFragment()
    private val artistsFrag = ArtistsFragment()
    private val albumsFrag = AlbumsFragment()
    private val mFragmentList: ArrayList<Fragment> =
        arrayListOf(tracksFrag, artistsFrag, albumsFrag)

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return 3
    }
}