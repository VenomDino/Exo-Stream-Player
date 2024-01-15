package com.venomdino.exonetworkstreamer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.venomdino.exonetworkstreamer.adapters.LocalVideosRVAdapter;
import com.venomdino.exonetworkstreamer.databinding.FragmentLocalVideosBinding;
import com.venomdino.exonetworkstreamer.helpers.VideoUtil;
import com.venomdino.exonetworkstreamer.models.VideoInfoModel;

import java.util.List;

public class LocalVideosFragment extends Fragment {

    private FragmentLocalVideosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocalVideosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();

        if (activity != null) {

            List<VideoInfoModel> videos = VideoUtil.getAllVideos(activity);

            LocalVideosRVAdapter adapter = new LocalVideosRVAdapter(activity, videos);
            binding.recyclerView.setAdapter(adapter);

            GridLayoutManager layoutManager = new GridLayoutManager(activity,2);
            binding.recyclerView.setLayoutManager(layoutManager);
        }
    }
}