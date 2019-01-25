package rrdl.be4care.Views.Fragments.MainUIFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.User;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.FillPersonalInfo;
import rrdl.be4care.Utils.RoomDB;
import rrdl.be4care.Utils.RoundedImageView;
import rrdl.be4care.Utils.UIUtils;
import rrdl.be4care.Views.Fragments.DetailFragments.DoctorListingFragment;
import rrdl.be4care.Views.Fragments.Repertoire;
import rrdl.be4care.Views.Fragments.allDoctors;
import rrdl.be4care.Views.Fragments.profile.PersonalProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Gson gson = new Gson();
        Doctor doc = gson.fromJson(param1, Doctor.class);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        Button contacts=view.findViewById(R.id.contact);
        Button profile=view.findViewById(R.id.profilebtn);
        ImageView riv=view.findViewById(R.id.profilepic);
        final SharedPreferences prefs = getActivity().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);

        RoomDB db = RoomDB.getINSTANCE(getContext());
        User user = db.Dao().getuser();
        if (user != null && !user.getEmail().equals("")) {

        }
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final ApiService apiservice = retrofit.create(ApiService.class);
        final Call<User> get = apiservice.load_user(prefs.getString("AUTH", ""));
        get.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful() && !response.body().getName().equals("")
                                && !response.body().getEmail().equals("") &&
                                !response.body().getSex().equals("") &&
                                !response.body().getLastName().equals("") &&
                                !response.body().getPhNumber().equals("") &&
                                !response.body().getBDate().equals("")
                                && !response.body().getPUrl().equals("")) {
                            try {
                                db.Dao().insertUser(response.body());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


                Glide.with(getContext()).load(response.body().getPUrl())
                        .asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(UIUtils.getRoundedImageTarget(getContext(), riv, 70));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        Button apropos=view.findViewById(R.id.apropos);
       Button repertoire=view.findViewById(R.id.Répertoire);
       repertoire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getFragmentManager().beginTransaction().replace(R.id.MainContainer,
                       new Repertoire()).addToBackStack("rep").commit();
           }
       });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson=new Gson();

                  getFragmentManager().beginTransaction().replace(R.id.MainContainer, new PersonalProfileFragment()).addToBackStack("personal").commit();
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  SharedPreferences prefs=getActivity().getSharedPreferences("GLOBAL",Context.MODE_PRIVATE);
                prefs.edit().putString("AUTH","").commit();*/
               RoomDB db= RoomDB.getINSTANCE(getContext());
               List<Document> docs=db.Dao().getdocu();
                Toast.makeText(getContext(), "from dao"+docs.size(), Toast.LENGTH_SHORT).show();
            }
        });
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.MainContainer,new allDoctors()).addToBackStack("alldoc").commit();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
