package maharsh.server.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import maharsh.server.app.R;
import maharsh.server.app.adapter.SavedServerListAdapter;
import maharsh.server.app.datamodel.ServerTable;
import maharsh.server.app.viewmodel.SavedServerViewModel;

import static maharsh.server.app.activity.BaseFragmentActivity.addFab;

public class HomeFragment extends Fragment {

    View rootView;
    NavController navController;
    RecyclerView savedServerRecyclerView;
    LottieAnimationView notFound;
    SavedServerViewModel savedServerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        savedServerRecyclerView = rootView.findViewById(R.id.servers_recyclerView);
        navController = Navigation.findNavController(container);
        addFab.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_addServerFragment));
        notFound = rootView.findViewById(R.id.servers_not_found);
        notFound.setVisibility(View.GONE);
        savedServerViewModel = new ViewModelProvider(this).get(SavedServerViewModel.class);
        SavedServerListAdapter savedServerListAdapter = new SavedServerListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        savedServerRecyclerView.setLayoutManager(layoutManager);
        savedServerRecyclerView.setAdapter(savedServerListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(savedServerRecyclerView.getContext(),
                layoutManager.getOrientation());
        savedServerRecyclerView.addItemDecoration(dividerItemDecoration);

        savedServerRecyclerView.post(() -> savedServerViewModel.getSavedServers().observe(getViewLifecycleOwner(), serverTables -> {
            if (serverTables.size() > 0) {
                savedServerListAdapter.submitList(serverTables);
                notFound.setVisibility(View.GONE);
                savedServerRecyclerView.setVisibility(View.VISIBLE);
            }
            else {
                savedServerRecyclerView.setVisibility(View.GONE);
                notFound.setVisibility(View.VISIBLE);
            }
        }));


        return rootView;
    }
}