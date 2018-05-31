package rrdl.be4care.Views.Fragments.MainUIFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toolbar;

import java.util.List;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.Controllers.SearchDocument;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.R;
import rrdl.be4care.Utils.Getdoctorlist;
import rrdl.be4care.Utils.Getdocumentlist;
import rrdl.be4care.Utils.ListAdapter;
import rrdl.be4care.Utils.searchadapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
      /*  ListAdapter la = new ListAdapter(;
        //insert collapsed logic here !*
        Toolbar toolbar;
        CollapsingToolbarLayout collapsingToolbarLayout;
        toolbar = (Toolbar) view.findViewById(R.id.toolbar1);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.CollapsingToolbarLayout1);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("Recherche");*/
        List<Document> doclist;
        List<Doctor> doctors;
        doclist= new Getdocumentlist(getContext()).getdocuments();
        doctors= new Getdoctorlist(getContext()).getdocuments();
        SharedPreferences prefs=getActivity().getSharedPreferences("GLOBAL",Context.MODE_PRIVATE);
       /* final ListView list = view.findViewById(R.id.listsearch);
        searchadapter searchadapter = new searchadapter(getContext(),doctors,doclist);
        list.setAdapter(searchadapter);*/
        SearchView search=view.findViewById(R.id.search);
        RecyclerView rv=view.findViewById(R.id.recyclersearch);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        search.setIconifiedByDefault(false);
        CollapsingToolbarLayout collapsingToolbarLayout;
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar1);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.CollapsingToolbarLayout1);
        collapsingToolbarLayout.setTitle("Recherche");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setExpandedTitleMargin(40,0,0,150);
        SearchDocument sd=new SearchDocument(getContext(),rv,search);
        sd.Load_Docs();
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
