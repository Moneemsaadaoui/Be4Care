package rrdl.be4care.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import rrdl.be4care.Controllers.GetMyDoctors;
import rrdl.be4care.R;

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
        SharedPreferences prefs = getActivity().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Button editbutton = view.findViewById(R.id.edit);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.NewDialog);
                dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.popup_login);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
            }
        });
        RoundedBitmapDrawable pdp = (RoundedBitmapDrawable) RoundedBitmapDrawable.createFromPath("@drawable/stephane");
        ListView profile_list = view.findViewById(R.id.ProfileElements);
       /* GetUserInfo userservice = new GetUserInfo(getContext(), profile_list, prefs.getString("TOKEN", "ERROR"));
        userservice.getUser();*/
        GetMyDoctors getMyDoctors=new GetMyDoctors(getContext(),profile_list, prefs.getString("TOKEN", "ERROR"));
        getMyDoctors.getDoctors();
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
