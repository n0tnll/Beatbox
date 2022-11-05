package com.shv.android.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shv.android.beatbox.databinding.ActivityMainBinding
import com.shv.android.beatbox.databinding.LitsItemSoundsBinding

private const val TAG = "MainActivity"
private lateinit var beatBox: BeatBox

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var beatBoxViewModel: BeatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factoryModel = BeatBoxViewModel.BeatBoxViewModelFactory(assets)
        beatBoxViewModel = ViewModelProvider(this, factoryModel)[BeatBoxViewModel::class.java]

        beatBox = beatBoxViewModel.beatBox

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = SoundViewModel(beatBox)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }
        binding.seekBar.progress = beatBox.playbackSpeed

        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.viewModel?.speed = progress
                beatBox.playbackSpeed = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private inner class SoundHolder(private val binding: LitsItemSoundsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = SoundViewModel(beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<LitsItemSoundsBinding>(
                layoutInflater,
                R.layout.lits_item_sounds,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount(): Int = sounds.size

    }
}
