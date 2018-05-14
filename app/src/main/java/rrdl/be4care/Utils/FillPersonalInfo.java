package rrdl.be4care.Utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import rrdl.be4care.Models.User;

public class FillPersonalInfo {
    private Context mContext;
    private User user;
    private EditText id, name, lastname, numtel, birth, sex;
    String[] data = {"Identifiant", "Nom", "Prénom", "Numero Telephone", "Date de naissance", "Sexe"};

    public FillPersonalInfo(Context context, User user,
                            EditText identifiant, EditText nom, EditText prenom,
                            EditText numerotel, EditText datenaissance, EditText sexe


    ) {

        id = identifiant;
        name = nom;
        lastname = prenom;
        numtel = numerotel;
        birth = datenaissance;
        sex = sexe;
        mContext = context;
        this.user = user;
        RoundedImageView profilepic;


        try {

            data[0] = user.getEmail();
            data[1] = user.getName();
            data[2] = user.getLastName();
            data[3] = user.getPhNumber();
            data[4] = user.getBDate().substring(0,Math.min(user.getBDate().length(),10));

        } catch (Exception e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(context, user.getName(), Toast.LENGTH_SHORT).show();
    }

    public void fillinfo() {
        id.setText(data[0]);
        name.setText(data[1]);
        lastname.setText(data[2]);
        numtel.setText(data[3]);
        birth.setText(data[4]);
        if (user.getSex()){sex.setText("Homme");}else{sex.setText("Femme");}
        id.setEnabled(false);
        name.setEnabled(false);
        lastname.setEnabled(false);
        numtel.setEnabled(false);
        birth.setEnabled(false);
        sex.setEnabled(false);

    }
}
