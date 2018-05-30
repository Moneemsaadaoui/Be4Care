package rrdl.be4care.Views.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import rrdl.be4care.Controllers.GetMyDoctors;
import rrdl.be4care.R;
import rrdl.be4care.Views.Fragments.MainUIFragments.ProfileFragment;
import rrdl.be4care.Views.Fragments.profile.AllStruck;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Repertoire.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Repertoire#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Repertoire extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Repertoire() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Repertoire.
     */
    // TODO: Rename and change types and number of parameters
    public static Repertoire newInstance(String param1, String param2) {
        Repertoire fragment = new Repertoire();
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
        View view = inflater.inflate(R.layout.fragment_repertoire, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Button back = view.findViewById(R.id.back);
        RecyclerView recyclerView = view.findViewById(R.id.replist);
        ImageButton addbtn = view.findViewById(R.id.addbtnrep);
        GetMyDoctors getMyDoctors=new GetMyDoctors(getContext(),recyclerView);
        getMyDoctors.getDoctors();
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext(), R.style.NewDialog);
                dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.repertoire_popup);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button listmed = dialog.findViewById(R.id.listmed);
                Button addmed = dialog.findViewById(R.id.addmed);
                Button liststruck = dialog.findViewById(R.id.liststruct);
                Button addstruck = dialog.findViewById(R.id.addstruct);
                addmed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFragmentManager().beginTransaction().replace(R.id.MainContainer,new allDoctors()).commit();
                        dialog.dismiss();
                    }
                });
                addstruck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFragmentManager().beginTransaction().replace(R.id.MainContainer,new AllStruck()).commit();
                        dialog.dismiss();
                    }
                });

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.MainContainer, new ProfileFragment()).commit();
            }
        });
     //   GetMyDoctors getMyDoctors = new GetMyDoctors(getContext(), recyclerView);
       // getMyDoctors.getDoctors();
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
