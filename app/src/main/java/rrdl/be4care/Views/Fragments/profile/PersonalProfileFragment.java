package rrdl.be4care.Views.Fragments.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.User;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.FillPersonalInfo;
import rrdl.be4care.Utils.RoomDB;
import rrdl.be4care.Utils.RoundedImageView;

import rrdl.be4care.R;
import rrdl.be4care.Utils.UIUtils;
import rrdl.be4care.Views.Activities.LoginActivity;
import rrdl.be4care.Views.Fragments.MainUIFragments.ProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonalProfileFragment() {
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
    public static PersonalProfileFragment newInstance(String param1, String param2) {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_personalprofile, container, false);
        final View popup = inflater.inflate(R.layout.popup_login, container, false);
        final EditText id, name, lastname, numtel, birth, sex;
        id = view.findViewById(R.id.identifiant);
        name = view.findViewById(R.id.name);
        lastname = view.findViewById(R.id.lastname);
        numtel = view.findViewById(R.id.numtel);
        birth = view.findViewById(R.id.birthdate);
        sex = view.findViewById(R.id.sex);
        final SharedPreferences prefs = getActivity().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Button editbutton = view.findViewById(R.id.edit);
        final Button Validate = view.findViewById(R.id.validate);
        Validate.setVisibility(View.GONE);
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.MainContainer, new ProfileFragment()).commit();
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.NewDialog);
                dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.popup_login);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button btn = dialog.findViewById(R.id.popup);
                Button Disconnect = dialog.findViewById(R.id.disconnect);
                Button Cancel = dialog.findViewById(R.id.cancel);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // id.setEnabled(true);
                        name.setEnabled(true);
                        lastname.setEnabled(true);
                        numtel.setEnabled(true);
                        birth.setEnabled(true);
                        sex.setEnabled(true);
                        Validate.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
                Disconnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        RoomDB db = RoomDB.getINSTANCE(getContext());
                        db.Dao().nukeDoctor();
                        db.Dao().nukeDocument();
                        db.Dao().nukeStruck();
                        prefs.edit().putString("AUTH", "").commit();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });

        final User mUser;
        final ImageView profilepic = view.findViewById(R.id.profilepic);
        //
        RoomDB db = RoomDB.getINSTANCE(getContext());
        User user = db.Dao().getuser();
        if (user != null && !user.getEmail().equals("")) {
            FillPersonalInfo info = new FillPersonalInfo(getContext(), user, id, name
                    , lastname, numtel, birth, sex);
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

                FillPersonalInfo fillPersonalInfo = new FillPersonalInfo(getContext(), response.body(), id, name
                        , lastname, numtel, birth, sex);
                fillPersonalInfo.fillinfo();
                Glide.with(getContext()).load(response.body().getPUrl())
                        .asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(UIUtils.getRoundedImageTarget(getContext(), profilepic, 70));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        // ListView profile_list = view.findViewById(R.id.ProfileElements);
       /* GetUserInfo userservice = new GetUserInfo(getContext(), profile_list, prefs.getString("TOKEN", "ERROR"));
        userservice.getUser();*/

        Validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //patch call
                JsonObject _user = new JsonObject();
                _user.addProperty("name", name.getText().toString());
                _user.addProperty("lastName", lastname.getText().toString());
                _user.addProperty("phNumber", numtel.getText().toString());
                _user.addProperty("bDate", birth.getText().toString());
                if(sex.getText().toString().toLowerCase().equals("homme")){_user.addProperty("sex",true);}
                else if(sex.getText().toString().toLowerCase().equals("femme")){_user.addProperty("sex",false);}
                final Call<User> update = apiservice.updateuser(prefs.getString("AUTH", ""), _user);
                update.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
                //do a call here
                id.setEnabled(false);
                name.setEnabled(false);
                lastname.setEnabled(false);
                numtel.setEnabled(false);
                birth.setEnabled(false);
                sex.setEnabled(false);
                Validate.setVisibility(View.GONE);
            }
        });
        // Inflate the layout for this fragment


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
