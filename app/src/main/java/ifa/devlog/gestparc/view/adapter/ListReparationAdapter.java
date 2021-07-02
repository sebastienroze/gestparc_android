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
import ifa.devlog.gestparc.model.Reparation;

public class ListReparationAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private EcouteurClicReparation ecouteur;

    public interface EcouteurClicReparation {
        void onClicReparation (Reparation item);
    }

    private List<Reparation> listeReparation;

    public ListReparationAdapter(List<Reparation> listeReparation, EcouteurClicReparation ecouteur) {
        this.ecouteur = ecouteur;
        this.listeReparation = listeReparation;
    }

    static class ReparationViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView textViewNom;
        TextView textViewReference;
        TextView textViewEtatCasse;
        TextView textViewEtatRepare;
        TextView textViewEtatDates;
        CardView cardView;
        ImageView imageViewbottomList;


        public ReparationViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_reparation);
            textViewNom = itemView.findViewById(R.id.textView_itemReparationMaterielNom);
            textViewReference = itemView.findViewById(R.id.textView_itemReparationMaterielReference);
            textViewEtatCasse = itemView.findViewById(R.id.textView_itemReparationEtatCasse);
            textViewEtatRepare = itemView.findViewById(R.id.textView_itemReparationEtatRepare);
            textViewEtatDates = itemView.findViewById(R.id.textView_itemReparationDates);
            imageViewbottomList = itemView.findViewById(R.id.imageView_bottomListeReparation);
            cardView = itemView.findViewById(R.id.cardView_materiel);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_reparation,parent,false);
        return new ReparationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reparation reparation = listeReparation.get(position);
        ReparationViewHolder reparationViewHolder = (ReparationViewHolder) holder;
        reparationViewHolder.textViewEtatCasse.setText("Etat entrant : " + reparation.getEtatCasse());
        reparationViewHolder.textViewEtatRepare.setText("Etat réparé : "+reparation.getEtatRepare());
        reparationViewHolder.textViewNom.setText(reparation.getMateriel().getNom());
        reparationViewHolder.textViewReference.setText(reparation.getMateriel().getReference());
        reparationViewHolder.textViewEtatDates.setText(
                ( reparation.getEtat() ==0 ? "Réparation en cours du " : "Réparé du ")
                + reparation.getDate_envoi() + " au " + reparation.getDate_retour() + " ");
        reparationViewHolder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicReparation(reparation)
        );

        if (position != listeReparation.size() - 1) {
            reparationViewHolder.imageViewbottomList.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listeReparation.size();
    }
}
