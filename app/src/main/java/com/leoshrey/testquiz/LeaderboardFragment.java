 package com.leoshrey.testquiz;

 import android.os.Bundle;

 import androidx.fragment.app.Fragment;
 import androidx.recyclerview.widget.LinearLayoutManager;

 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;

 import com.leoshrey.testquiz.databinding.FragmentLeaderboardBinding;
 import com.google.android.gms.tasks.OnSuccessListener;
 import com.google.firebase.auth.FirebaseAuthSettings;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.Query;
 import com.google.firebase.firestore.QuerySnapshot;

 import java.util.ArrayList;


 public class LeaderboardFragment extends Fragment {



     public LeaderboardFragment() {
         // Required empty public constructor
     }



     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     }

     FragmentLeaderboardBinding binding;

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         binding = FragmentLeaderboardBinding.inflate(inflater, container, false);

         FirebaseFirestore database = FirebaseFirestore.getInstance();

         final ArrayList<UserClass> users = new ArrayList<>();
         final LeaderboardAdapter adapter = new LeaderboardAdapter(getContext(), users);

         binding.recyclerView.setAdapter(adapter);
         binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         database.collection("user")
                 .orderBy("email", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                     UserClass user = snapshot.toObject(UserClass.class);
                     users.add(user);
                 }
                 adapter.notifyDataSetChanged();
             }
         });


         return binding.getRoot();
     }
 }
