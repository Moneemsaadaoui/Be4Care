package rrdl.be4care.Views.Fragments.MainUIFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;

import rrdl.be4care.Controllers.LoadDocuments;
import rrdl.be4care.R;
import rrdl.be4care.Utils.SectionedRV;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocumentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SectionedRV listAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int SORTCODE;
    private OnFragmentInteractionListener mListener;

    public DocumentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentsFragment newInstance(String param1, String param2) {
        DocumentsFragment fragment = new DocumentsFragment();
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
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        // Inflate the layout for this fragment


        Toolbar toolbar;
        CollapsingToolbarLayout collapsingToolbarLayout;
        toolbar = (Toolbar) view.findViewById(R.id.toolbar1);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.CollapsingToolbarLayout1);

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        Button sort = view.findViewById(R.id.sort);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("Documents");
        collapsingToolbarLayout.setExpandedTitleMargin(40, 0, 0, 150);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        RecyclerView rv = view.findViewById(R.id.documentRecycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        SharedPreferences prefs = getActivity().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        final LoadDocuments loadDocuments = new LoadDocuments(getContext(), prefs.getString("AUTH", ""),
                rv);
        loadDocuments.Load_Docs();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDocuments.Load_Docs();
                mSwipeRefreshLayout.setRefreshing(false);


            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.NewDialog);
                listAdapter = loadDocuments.getDocadapter();
                dialog.requestWindowFeature(getActivity().getWindow().FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.popupsort);
                dialog.getWindow().setBackgroundDrawableResource(R.color.space_transparent);
                dialog.show();
                Button docsort = dialog.findViewById(R.id.docsort);
                Button strucksort = dialog.findViewById(R.id.strucksort);
                Button typesort = dialog.findViewById(R.id.typesort);
                Button sortDate = dialog.findViewById(R.id.datesort);
                colorize(sortDate, docsort, typesort, strucksort, SORTCODE);

                sortDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listAdapter = loadDocuments.getDocadapter();
                        listAdapter.SortBydate();
                        sort.setText("Tri Par Date");
                        SORTCODE=0;
                        dialog.dismiss();

                    }
                });

                docsort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listAdapter = loadDocuments.getDocadapter();
                        sort.setText("Tri Par Docteur");
                        listAdapter.Sortbyname();
                        SORTCODE=1;
                        dialog.dismiss();
                    }
                });
                strucksort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listAdapter = loadDocuments.getDocadapter();
                        sort.setText("Tri Par Structure de Santé");
                        listAdapter.SortByHstruck();
                        SORTCODE=3;
                        dialog.dismiss();

                    }
                });
                typesort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listAdapter = loadDocuments.getDocadapter();
                        sort.setText("Tri Par Type");
                        listAdapter.SortBytype();
                        SORTCODE=2;
                        dialog.dismiss();
                    }
                });

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

    public void colorize(Button date, Button doc, Button type, Button Struck, int pos) {
        date.setTextColor(Color.BLACK);
        doc.setTextColor(Color.BLACK);
        type.setTextColor(Color.BLACK);
        Struck.setTextColor(Color.BLACK);
        switch (pos) {
            case 0:
                date.setTextColor(Color.RED);
                break;
            case 1:
                doc.setTextColor(Color.RED);
                break;
            case 2:
                type.setTextColor(Color.RED);
                break;
            case 3:
                Struck.setTextColor(Color.RED);
                break;
        }

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
