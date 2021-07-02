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

public class ListMaterielAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private EcouteurClicMateriel ecouteur;

    public interface EcouteurClicMateriel {
        void onClicMateriel(Materiel item);
    }

    private List<Materiel> listeMateriel;

    public ListMaterielAdapter(List<Materiel> listeMateriel, EcouteurClicMateriel ecouteur) {
        this.ecouteur = ecouteur;
        this.listeMateriel = listeMateriel;
    }

    static class MaterielViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView textViewNom;
        TextView textViewReference;
        TextView textViewEtat;
        CardView cardView;
        ImageView imageViewbottomList;


        public MaterielViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_materiel);
            textViewNom = itemView.findViewById(R.id.textView_itemMaterielNom);
            textViewReference = itemView.findViewById(R.id.textView_itemMaterielReference);
            textViewEtat = itemView.findViewById(R.id.textView_itemMaterielEtat);
            imageViewbottomList = itemView.findViewById(R.id.imageView_bottomListeMateriel);
            cardView = itemView.findViewById(R.id.cardView_materiel);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_materiel,parent,false);
        return new MaterielViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Materiel materiel = listeMateriel.get(position);
        MaterielViewHolder materielViewHolder = (MaterielViewHolder) holder;
        materielViewHolder.textViewEtat.setText(materiel.getEtat());
        materielViewHolder.textViewNom.setText(materiel.getNom());
        materielViewHolder.textViewReference.setText(materiel.getReference());
        materielViewHolder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicMateriel(materiel)
        );

        ViewGroup.MarginLayoutParams  layoutParams;
        if (position != listeMateriel.size() - 1) {
            materielViewHolder.imageViewbottomList.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listeMateriel.size();
    }
}
