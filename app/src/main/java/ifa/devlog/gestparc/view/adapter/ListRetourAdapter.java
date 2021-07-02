package ifa.devlog.gestparc.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ifa.devlog.gestparc.R;
import ifa.devlog.gestparc.model.Materiel;
import ifa.devlog.gestparc.model.Retour;

public class ListRetourAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private EcouteurClicRetour ecouteur;

    public interface EcouteurClicRetour {
        void onClicRetour(Retour item);
    }

    private List<Retour> listeRetour;

    public ListRetourAdapter(List<Retour> listeRetour, EcouteurClicRetour ecouteur) {
        this.ecouteur = ecouteur;
        this.listeRetour = listeRetour;
    }

    static class RetourViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView textViewNom;
        TextView textViewLocataire;
        TextView textViewReference;
        TextView textViewEtatEntrant;
        TextView textViewEtatSortant;
        TextView textViewLocationDates;
        CardView cardView;
        ImageView imageViewbottomList;


        public RetourViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_retour);
            textViewNom = itemView.findViewById(R.id.textView_itemRetourMaterielNom);
            textViewLocataire = itemView.findViewById(R.id.textView_itemRetourLocationLocataire);
            textViewReference = itemView.findViewById(R.id.textView_itemRetourMaterielReference);
            textViewEtatEntrant = itemView.findViewById(R.id.textView_itemRetourEtatEntrant);
            textViewEtatSortant = itemView.findViewById(R.id.textView_itemRetourEtatSortant);
            textViewLocationDates = itemView.findViewById(R.id.textView_itemRetourLocationDates);
            imageViewbottomList = itemView.findViewById(R.id.imageView_bottomListeRetour);
            cardView = itemView.findViewById(R.id.cardView_retour);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_retour,parent,false);
        return new RetourViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Retour retour = listeRetour.get(position);
        RetourViewHolder retourViewHolder = (RetourViewHolder) holder;
        retourViewHolder.textViewLocataire.setText("Location par " + retour.getLocation().getUtilisateur().getLogin());
        retourViewHolder.textViewNom.setText("Matériel : " +retour.getLocation().getMateriel().getNom());
        retourViewHolder.textViewReference.setText(retour.getLocation().getMateriel().getReference());
        retourViewHolder.textViewEtatEntrant.setText("Etat entrant : " +retour.getEtatEntrant());
        retourViewHolder.textViewEtatSortant.setText("Etat sortant : " +retour.getEtatSortant());
        retourViewHolder.textViewLocationDates.setText(
                " du " +retour.getLocation().getDate_debut() +
                " au " +retour.getLocation().getDate_retour()
        );
        retourViewHolder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicRetour(retour)
        );
        if (position != listeRetour.size() - 1) {
            retourViewHolder.imageViewbottomList.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listeRetour.size();
    }
}
