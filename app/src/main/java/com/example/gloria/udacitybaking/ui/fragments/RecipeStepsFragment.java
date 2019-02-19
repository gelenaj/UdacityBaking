package com.example.gloria.udacitybaking.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gloria.udacitybaking.data.Step;
import com.example.gloria.udacitybaking.R;

import com.example.gloria.udacitybaking.module.GlideApp;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeStepsFragment extends Fragment {
    public static final String STEP_KEY = "steps";
    private static final String STEP_POSITION_KEY = "position";
    private static final String PLAY = "play";

    @BindView(R.id.exoplayer)
    SimpleExoPlayerView exoPlayerView;

    @BindView(R.id.step_image)
    ImageView imageView;

    @BindView(R.id.instructions_container)
    NestedScrollView instructionsContainer;

    @BindView(R.id.instructions)
    TextView instructions;


    private SimpleExoPlayer exoPlayer;
    private Step step;
    private Unbinder unbinder;

    private long currentPosition = 0;
    private boolean play = true;

    public RecipeStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            step = getArguments().getParcelable(STEP_KEY);
        } else {
            Snackbar.make(instructionsContainer, R.string.error_recipes_list, Snackbar.LENGTH_LONG).show();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        if (savedInstanceState != null && savedInstanceState.containsKey(STEP_POSITION_KEY)) {
            currentPosition = savedInstanceState.getLong(STEP_POSITION_KEY);
            play = savedInstanceState.getBoolean(PLAY);
        }
        unbinder = ButterKnife.bind(this, view);
        instructions.setText(step.getDescription());



        if (!step.getThumbnailURL().isEmpty()) {
            GlideApp.with(this)
                    .load(step.getThumbnailURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializePlayer(Uri.parse(step.getVideoURL()));
        else {
            instructionsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(STEP_POSITION_KEY, currentPosition);
        outState.putBoolean(PLAY, play);
    }

    private void initializePlayer(Uri uri) {
        if (exoPlayer == null) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            exoPlayerView.setPlayer(exoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);

            MediaSource source = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);


            exoPlayer.prepare(source);

            // onRestore
            if (currentPosition != 0)
                exoPlayer.seekTo(currentPosition);

            exoPlayer.setPlayWhenReady(play);
            exoPlayerView.setVisibility(View.VISIBLE);
        }
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            play = exoPlayer.getPlayWhenReady();
            currentPosition = exoPlayer.getCurrentPosition();

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

}
