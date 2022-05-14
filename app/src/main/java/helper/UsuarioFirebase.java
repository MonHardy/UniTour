package helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.monhardy.unitourapp.activity.telaInicial;
import com.monhardy.unitourapp.activity.telaProfessor;
import com.monhardy.unitourapp.config.ConfiguracaoFirebase;
import com.monhardy.unitourapp.model.Usuario;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual() {

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public static boolean atualizarNomeUsuario( String nome ) {

        try {

            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete( @NonNull Task<Void> task ) {
                    if (!task.isSuccessful()) {
                        Log.d("Perfil", "erro ao utilizar nome de perfil.");
                    }
                }
            });

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

                 //  ----- Redireciona o Motorosita ou o passageiro! -----  //

    public static void redirecionaUsuarioLogado(Activity activity){

        FirebaseUser user = getUsuarioAtual();
        if(user != null){

            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                    .child("usuarios")
                    .child(getIdentificadorUsuario() );
            usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    Usuario usuario = dataSnapshot.getValue( Usuario.class );

                    String tipoUsuario = usuario.getTipo();
                    if(tipoUsuario.equals("P")){
                        Intent i = new Intent(activity, telaProfessor.class);
                        activity.startActivity(i); // ---- Vai para MapsActivity ---- //
                    }else{
                        Intent i = new Intent(activity, telaInicial.class);
                        activity.startActivity(i);
                    }
                }

                @Override
                public void onCancelled( @NonNull DatabaseError error ) {

                }
            });
        }
    }

    public static String getIdentificadorUsuario(){
        return getUsuarioAtual().getUid();
    }
}
