package com.leoshrey.testquiz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.leoshrey.testquiz.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Main Working Code

    FragmentHomeBinding binding;
    FirebaseFirestore data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        ArrayList<SubjectModel> categories = new ArrayList<>();
//        categories.add(new SubjectModel("", "Science", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));
//        categories.add(new SubjectModel("", "Mathematics", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));
//        categories.add(new SubjectModel("", "English", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));
//        categories.add(new SubjectModel("", "Sample Subject 4", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));
//        categories.add(new SubjectModel("", "Sample subject 5", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));
//        categories.add(new SubjectModel("", "Sample subject 6", "https://media.istockphoto.com/vectors/symbol-of-science-research-atom-logo-vector-icon-illustration-rotate-vector-id1180524517?k=6&m=1180524517&s=612x612&w=0&h=1VviltvmaHas3HZ6s1QQhvoV_llqdghInWPJqXcfJZI="));

        SubjectAdapter adapter = new SubjectAdapter(getContext(), categories);

        data = FirebaseFirestore.getInstance();

        data.collection("SubjectCategories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        for(DocumentSnapshot snapshot: value.getDocuments()){
                            SubjectModel model = snapshot.toObject(SubjectModel.class);
                            model.setSubjectId(snapshot.getId());
                            categories.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        binding.categoryList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.categoryList.setAdapter(adapter);

        // Inflate the layout for this fragment
        return binding.getRoot();

    }
}