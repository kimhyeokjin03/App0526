package com.example.app0526;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

public class RestaurantDetailFragment extends Fragment {

    private RestaurantSharedViewModel viewModel;
    private static final String TAG = "RestaurantDetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtFeature = view.findViewById(R.id.txtFeature);
        ImageView imgRestaurant = view.findViewById(R.id.imgRestaurant);
        Button btnBack = view.findViewById(R.id.btnBack);

        viewModel = new ViewModelProvider(requireActivity())
                .get(RestaurantSharedViewModel.class);

        // 데이터 관찰 및 UI 업데이트
        viewModel.getSelectedRestaurant().observe(getViewLifecycleOwner(), restaurant -> {
            if (restaurant != null) {
                txtName.setText(restaurant.getName());
                txtFeature.setText(restaurant.getFeature());

                if (getContext() != null) {
                    Glide.with(this)
                            .load(restaurant.getImageUrl())
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .error(android.R.drawable.ic_menu_report_image)
                            .into(imgRestaurant);
                }
            }
        });

        // 뒤로 가기 버튼 로직: popBackStack()을 사용하여 확실하게 처리
        btnBack.setOnClickListener(v -> {
            Log.d(TAG, "뒤로 가기 버튼 클릭됨");
            
            // 버튼 클릭 확인용 토스트
            Toast.makeText(getContext(), "이전 화면으로 돌아갑니다", Toast.LENGTH_SHORT).show();

            if (isAdded()) {
                try {
                    // 내비게이션 스택에서 현재 프래그먼트 제거
                    boolean success = NavHostFragment.findNavController(this).popBackStack();
                    if (!success) {
                        Log.w(TAG, "popBackStack 실패 - 시스템 뒤로가기 호출");
                        if (getActivity() != null) {
                            getActivity().onBackPressed();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "뒤로가기 중 오류 발생", e);
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            }
        });

        return view;
    }
}
