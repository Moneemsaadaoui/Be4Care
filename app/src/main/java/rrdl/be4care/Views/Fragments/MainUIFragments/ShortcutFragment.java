package rrdl.be4care.Views.Fragments.MainUIFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Models.Document;
import rrdl.be4care.Models.Structure;
import rrdl.be4care.R;
import rrdl.be4care.Utils.ApiService;
import rrdl.be4care.Utils.ExpandableAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShortcutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShortcutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShortcutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String>top250,comingSoon,nowShowing;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ExpandableAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private OnFragmentInteractionListener mListener;

    public ShortcutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortcutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShortcutFragment newInstance(String param1, String param2) {
        ShortcutFragment fragment = new ShortcutFragment();
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
        prepareListData();
        View view= inflater.inflate(R.layout.fragment_shortcut, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.expandable);
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Inflate the layout for this fragment
        return view;
    }

    private void prepareListData() {
        SharedPreferences prefs = getContext().getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Documents");
        listDataHeader.add("Medecins");
        listDataHeader.add("Structure de santé");

        // Adding child data
      /*  Call<List<Document>>loaddocs=apiservice.load_documents(prefs.getString("AUTH",""));
        loaddocs.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                List<String> top250 = new ArrayList<String>();
                for(Document row:response.body())
                {
                    if(row.getStar()){
                        top250.add(row.getDr().toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {

            }
        });

       Call<List<Doctor>>getdocs=apiservice.getalldoctors(prefs.getString("AUTH",""));
       getdocs.enqueue(new Callback<List<Doctor>>() {
           @Override
           public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
               List<String> nowShowing = new ArrayList<String>();
               Toast.makeText(getContext(), response.body().size() + "Second", Toast.LENGTH_SHORT).show();

               for(Doctor row:response.body())
               {
                   if(row.getStar()){
                       nowShowing.add(row.getFullName().toString());
                   }
               }


           }

           @Override
           public void onFailure(Call<List<Doctor>> call, Throwable t) {

           }
       });

      Call<List<Structure>>getstruck=apiservice.getalldstruck(prefs.getString("AUTH",""));
      getstruck.enqueue(new Callback<List<Structure>>() {
          @Override
          public void onResponse(Call<List<Structure>> call, Response<List<Structure>> response) {
              List<String> comingSoon = new ArrayList<String>();
              for(Structure row:response.body())
              {
                  if(row.getStar()){
                      comingSoon.add(row.getFullName().toString());
                  }
              }

          }

          @Override
          public void onFailure(Call<List<Structure>> call, Throwable t) {

          }
      });*/
        List<String> comingSoon = new ArrayList<String>();
        List<String> nowShowing = new ArrayList<String>();
        List<String> top250 = new ArrayList<String>();

        top250.add("Ordenance");
        top250.add("Visite medicale");
        nowShowing.add("Dr quesnot");
        nowShowing.add("Dr Mahmoudi");
        nowShowing.add("Dr Saadaoui");
        nowShowing.add("Dr Zaidoune");
        nowShowing.add("Dr Baccouche");
        comingSoon.add("Hopital Militaire tunis");
        comingSoon.add("Hopital Sahloul");
        comingSoon.add("Hopital Bouficha");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
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
